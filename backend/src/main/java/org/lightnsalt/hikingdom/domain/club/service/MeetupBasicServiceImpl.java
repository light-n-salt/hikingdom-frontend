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
import org.lightnsalt.hikingdom.domain.club.dto.response.MeetupMonthlyRes;
import org.lightnsalt.hikingdom.domain.club.entity.ClubMember;
import org.lightnsalt.hikingdom.domain.club.entity.meetup.Meetup;
import org.lightnsalt.hikingdom.domain.club.entity.meetup.MeetupMember;
import org.lightnsalt.hikingdom.domain.club.repository.ClubRepository;
import org.lightnsalt.hikingdom.domain.club.repository.MeetupMemberRepository;
import org.lightnsalt.hikingdom.domain.club.repository.MeetupRepository;
import org.lightnsalt.hikingdom.domain.club.repository.ClubMemberRepository;
import org.lightnsalt.hikingdom.domain.info.entity.MountainInfo;
import org.lightnsalt.hikingdom.domain.info.repository.MountainInfoRepository;
import org.lightnsalt.hikingdom.domain.member.entity.Member;
import org.lightnsalt.hikingdom.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MeetupBasicServiceImpl implements MeetupBasicService {
	private final MeetupMemberRepository meetupMemberRepository;
	private final ClubRepository clubRepository;
	private final MeetupRepository meetupRepository;
	private final MountainInfoRepository mountainInfoRepository;
	private final MemberRepository memberRepository;
	private final ClubMemberRepository clubMemberRepository;

	/*
	- 서비스 클래스 안에서 메서드 명을 작성 할 때는 아래와 같은 접두사를 붙인다.

		findOrder() - 조회 유형의 service 메서드

		addOrder() - 등록 유형의 service 메서드

		modifyOrder() - 변경 유형의 service 메서드

		removeOrder() - 삭제 유형의 service 메서드

		saveOrder() – 등록/수정/삭제 가 동시에 일어나는 유형의 service 메서드
	* */

	@Override
	@Transactional
	public Long saveMeetup(String email, Long clubId, MeetupAddReq req) {
		// club에 속해있는 멤버인지 확인
		final Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));

		final ClubMember clubMember = clubMemberRepository.findByClubIdAndMemberId(clubId, member.getId())
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
}
