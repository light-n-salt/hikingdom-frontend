package org.lightnsalt.hikingdom.service.club.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.common.error.GlobalException;
import org.lightnsalt.hikingdom.domain.common.enumType.JoinRequestStatusType;
import org.lightnsalt.hikingdom.domain.entity.club.meetup.MeetupMember;
import org.lightnsalt.hikingdom.service.club.repository.meetup.MeetupMemberRepository;
import org.lightnsalt.hikingdom.service.club.repository.meetup.MeetupRepository;
import org.lightnsalt.hikingdom.service.club.dto.response.meetup.MeetupMemberDetailListRes;
import org.lightnsalt.hikingdom.domain.entity.club.Club;
import org.lightnsalt.hikingdom.domain.entity.club.ClubJoinRequest;
import org.lightnsalt.hikingdom.domain.entity.club.ClubMember;
import org.lightnsalt.hikingdom.service.club.repository.ClubJoinRequestRepository;
import org.lightnsalt.hikingdom.service.club.repository.ClubMemberRepository;
import org.lightnsalt.hikingdom.service.club.repository.ClubRepository;
import org.lightnsalt.hikingdom.domain.entity.member.Member;
import org.lightnsalt.hikingdom.service.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClubMemberServiceImpl implements ClubMemberService {
	private final MeetupMemberRepository meetupMemberRepository;
	private final MeetupRepository meetupRepository;
	private final ClubRepository clubRepository;
	private final ClubMemberRepository clubMemberRepository;
	private final ClubJoinRequestRepository clubJoinRequestRepository;
	private final MemberRepository memberRepository;

	@Transactional
	@Override
	public void sendClubJoinRequest(String email, Long clubId) {
		final Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_UNAUTHORIZED));
		final Club club = clubRepository.findById(clubId)
			.orElseThrow(() -> new GlobalException(ErrorCode.CLUB_NOT_FOUND));

		// 현재 소모임에 가입되어 있는지 확인
		if (clubMemberRepository.findByMemberId(member.getId()).isPresent())
			throw new GlobalException(ErrorCode.CLUB_ALREADY_JOINED);

		// 현재 소모임에 가입 요청했는지 확인 (동시에 여러 소모임 가입 신청 가능)
		if (clubJoinRequestRepository.findByMemberIdAndClubIdAndStatus(member.getId(), clubId,
			JoinRequestStatusType.PENDING).isPresent())
			throw new GlobalException(ErrorCode.CLUB_JOIN_REQUEST_PENDING);

		ClubJoinRequest clubJoinRequest = ClubJoinRequest.builder()
			.club(club)
			.member(member)
			.status(JoinRequestStatusType.PENDING)
			.build();

		clubJoinRequestRepository.save(clubJoinRequest);
	}

	@Transactional
	@Override
	public void retractClubJoinRequest(String email, Long clubId) {
		final Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_UNAUTHORIZED));
		final Club club = clubRepository.findById(clubId)
			.orElseThrow(() -> new GlobalException(ErrorCode.CLUB_NOT_FOUND));

		final ClubJoinRequest clubJoinRequest = clubJoinRequestRepository.findByMemberIdAndClubIdAndStatus(
				member.getId(), club.getId(), JoinRequestStatusType.PENDING)
			.orElseThrow(() -> new GlobalException(ErrorCode.CLUB_JOIN_REQUEST_NOT_FOUND));

		if (!retractPendingJoinRequest(clubJoinRequest.getMember(), clubJoinRequest.getClub()))
			throw new GlobalException(ErrorCode.INTERNAL_SERVER_ERROR);
	}

	@Transactional
	public boolean retractPendingJoinRequest(Member member, Club club) {
		return clubJoinRequestRepository.updatePendingJoinRequestByMemberAndClub(member, club,
			JoinRequestStatusType.RETRACTED, LocalDateTime.now()) > 0;
	}

	@Override
	@Transactional
	public Map<String, List<MeetupMemberDetailListRes>> findClubMember(String email, Long clubId) {
		// 회원 정보 id 가져오기 -> 소모임장인지 확인하기 위한 용도
		final Long memberId = memberRepository.findByEmail(email)
			.orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_UNAUTHORIZED)).getId();

		// 소모임장 id 가져오기
		final Long hostId = clubRepository.findById(clubId)
			.orElseThrow(() -> new GlobalException(ErrorCode.CLUB_NOT_FOUND))
			.getHost().getId();

		Map<String, List<MeetupMemberDetailListRes>> results = new HashMap<>();

		// 소모임장일 경우
		if (memberId.equals(hostId)) {
			// 신청한 회원 정보 가져오기
			List<ClubJoinRequest> list = clubJoinRequestRepository.findByClubIdAndStatus(clubId,
				JoinRequestStatusType.PENDING);
			List<MeetupMemberDetailListRes> result = list.stream()
				.map(clubJoinRequest -> new MeetupMemberDetailListRes(clubJoinRequest.getMember()))
				.collect(Collectors.toList());
			results.put("request", result);
		}

		// 소모임에 가입되어있는 회원 정보 가져오기
		List<ClubMember> list = clubMemberRepository.findByClubId(clubId);
		List<MeetupMemberDetailListRes> result = list.stream()
			.map(clubMember -> new MeetupMemberDetailListRes(clubMember.getMember()))
			.collect(Collectors.toList());

		results.put("member", result);

		return results;
	}

	@Transactional
	@Override
	public void withdrawClubMember(String email, Long clubId) {
		final Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_UNAUTHORIZED));
		final ClubMember clubMember = clubMemberRepository.findByClubIdAndMemberId(clubId, member.getId())
			.orElseThrow(() -> new GlobalException(ErrorCode.CLUB_MEMBER_UNAUTHORIZED));

		// 모임장일 시, 모임 탈퇴 불가능
		if (clubMember.getClub().getHost().getId().equals(member.getId())) {
			throw new GlobalException(ErrorCode.CLUB_MEMBER_IS_HOST);
		}

		// 내가 만든 일정이 있을 시, 끝나야지 탈퇴 가능
		if (!meetupRepository.findByClubIdAndHostIdAndStartAtAfter(clubId, member.getId(), LocalDateTime.now())
			.isEmpty()) {
			throw new GlobalException(ErrorCode.CLUB_MEMBER_HOST_MEETUP_EXISTS);
		}

		// 참여하고 있는 일정이 있을 시, 자동 참여 취소
		LocalDateTime now = LocalDateTime.now();
		List<MeetupMember> participatingEvents = meetupMemberRepository.findByMemberIdAndStartAtAfter(
			member.getId(), now);
		meetupMemberRepository.deleteAll(participatingEvents);

		// 소모임 탈퇴 처리
		clubMemberRepository.deleteById(clubMember.getId());
	}
}
