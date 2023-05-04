package org.lightnsalt.hikingdom.domain.club.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.common.error.GlobalException;
import org.lightnsalt.hikingdom.domain.club.dto.request.MeetupAddReq;
import org.lightnsalt.hikingdom.domain.club.dto.response.MeetupDailyRes;
import org.lightnsalt.hikingdom.domain.club.dto.response.MeetupDetailRes;
import org.lightnsalt.hikingdom.domain.club.dto.response.MeetupMonthlyRes;
import org.lightnsalt.hikingdom.domain.club.dto.response.MemberInfoRes;
import org.lightnsalt.hikingdom.domain.club.dto.response.PhotoInfoRes;
import org.lightnsalt.hikingdom.domain.club.dto.response.ReviewInfoRes;
import org.lightnsalt.hikingdom.entity.club.ClubMember;
import org.lightnsalt.hikingdom.entity.club.meetup.Meetup;
import org.lightnsalt.hikingdom.entity.club.meetup.MeetupAlbum;
import org.lightnsalt.hikingdom.entity.club.meetup.MeetupMember;
import org.lightnsalt.hikingdom.entity.club.meetup.MeetupReview;
import org.lightnsalt.hikingdom.domain.club.repository.ClubRepository;
import org.lightnsalt.hikingdom.domain.club.repository.MeetupAlbumRepository;
import org.lightnsalt.hikingdom.domain.club.repository.MeetupMemberRepository;
import org.lightnsalt.hikingdom.domain.club.repository.MeetupRepository;
import org.lightnsalt.hikingdom.domain.club.repository.ClubMemberRepository;
import org.lightnsalt.hikingdom.domain.club.repository.MeetupReviewRepository;
import org.lightnsalt.hikingdom.entity.info.MountainInfo;
import org.lightnsalt.hikingdom.domain.info.repository.MountainInfoRepository;
import org.lightnsalt.hikingdom.entity.member.Member;
import org.lightnsalt.hikingdom.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MeetupBasicServiceImpl implements MeetupBasicService {
	private final MeetupReviewRepository meetupReviewRepository;
	private final MeetupAlbumRepository meetupAlbumRepository;
	private final MeetupMemberRepository meetupMemberRepository;
	private final ClubRepository clubRepository;
	private final MeetupRepository meetupRepository;
	private final MountainInfoRepository mountainInfoRepository;
	private final MemberRepository memberRepository;
	private final ClubMemberRepository clubMemberRepository;

	@Override
	@Transactional
	public Long saveMeetup(String email, Long clubId, MeetupAddReq req) {
		// club에 속해있는 멤버인지 확인
		final Member member = memberRepository.findByEmailAndIsWithdraw(email, false)
			.orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));

		final ClubMember clubMember = clubMemberRepository.findByClubIdAndMemberIdAndIsWithdraw(clubId, member.getId(),
				false)
			.orElseThrow(() -> new GlobalException(ErrorCode.CLUB_MEMBER_UNAUTHORIZED));

		// 산 데이터 가져오기
		final MountainInfo mountainInfo = mountainInfoRepository.findById(req.getMountainId())
			.orElseThrow(() -> new GlobalException(ErrorCode.MOUNTAIN_NOT_FOUND));

		// 일정 생성
		final Meetup meetup = Meetup.builder()
			.club(clubMember.getClub())
			.mountain(mountainInfo)
			.host(member)
			.name(req.getName())
			.description(req.getDescription())
			.startAt(LocalDateTime.parse(req.getStartAt(), DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")))
			.build();

		final Meetup savedMeetup = meetupRepository.save(meetup);

		// 일정장 일정 가입
		final MeetupMember meetupMember = MeetupMember.builder()
			.meetup(savedMeetup)
			.member(member)
			.build();

		meetupMemberRepository.save(meetupMember);

		return savedMeetup.getId();
	}

	@Override
	@Transactional
	public MeetupMonthlyRes findMeetupMonthly(Long clubId, String month) {
		// 소모임이 존재하는지 확인
		final boolean isExit = clubRepository.existsById(clubId);
		if (!isExit) {
			throw new GlobalException(ErrorCode.CLUB_NOT_FOUND);
		}

		// 일정 가져오기
		String[] element = month.split("-");
		log.debug("query intput date is : {}", Arrays.toString(element));
		final List<Meetup> meetups = meetupRepository.findByClubIdAndStartMonth(clubId, Integer.parseInt(element[0]),
			Integer.parseInt(element[1]));

		// 형 변환
		List<Integer> list = meetups.stream()
			.map((meetup -> meetup.getStartAt().getDayOfMonth())).collect(Collectors.toList());

		MeetupMonthlyRes result = new MeetupMonthlyRes();
		result.setStartAt(list);

		return result;
	}

	@Override
	@Transactional
	public List<MeetupDailyRes> findMeetupDaily(Long clubId, String date) {
		// 소모임이 존재하는지 확인
		final boolean isExit = clubRepository.existsById(clubId);
		if (!isExit) {
			throw new GlobalException(ErrorCode.CLUB_NOT_FOUND);
		}

		// 입력 데이터 가공
		String[] element = date.split("-");

		// 일정 데이터 가져오기
		final List<Meetup> meetups = meetupRepository.findByClubIdAndStartDay(clubId, Integer.parseInt(element[0]),
			Integer.parseInt(element[1]),
			Integer.parseInt(element[2]));

		// 형 변환
		return meetups.stream().map(meetup -> {
			MeetupDailyRes dto = new MeetupDailyRes(meetup);
			// 일정 참여 멤버 가져오기
			final int totalMember = meetupMemberRepository.countByMeetupId(meetup.getId());
			dto.setTotalMember(totalMember);
			return dto;
		}).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public MeetupDetailRes findMeetup(String email, Long clubId, Long meetupId) {
		// 일정 정보 가져오기
		final Meetup meetup = meetupRepository.findById(meetupId)
			.orElseThrow(() -> new GlobalException(ErrorCode.MEETUP_NOT_FOUND));

		// 일정 참여여부 가져오기
		final Long memberId = memberRepository.findByEmailAndIsWithdraw(email, false)
			.orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND)).getId();

		final boolean isJoin = meetupMemberRepository.existsByMeetupIdAndMemberId(meetupId, memberId);

		// 일정 참여 멤버 수 조회하기
		final int totalMember = meetupMemberRepository.countByMeetupId(meetupId);

		// 일정 참여멤버 조회하기 6명
		List<MeetupMember> meetupMembers = meetupMemberRepository.findTop6ByMeetupId(meetupId);
		List<MemberInfoRes> memberInfos = meetupMembers.stream().map(MemberInfoRes::new).collect(Collectors.toList());

		// 일정 사진 조회하기 3개
		List<MeetupAlbum> meetupAlbums = meetupAlbumRepository.findTop3ByMeetupIdOrderByCreatedAtDesc(meetupId);
		List<PhotoInfoRes> photoInfos = meetupAlbums.stream().map(PhotoInfoRes::new).collect(Collectors.toList());

		// 일정 리뷰 조회하기 전체
		List<MeetupReview> meetupReviews = meetupReviewRepository.findByMeetupId(meetupId);
		List<ReviewInfoRes> reviewInfos = meetupReviews.stream().map(ReviewInfoRes::new).collect(Collectors.toList());

		return new MeetupDetailRes(meetup, totalMember, isJoin, memberInfos, photoInfos, reviewInfos);
	}
}
