package org.lightnsalt.hikingdom.domain.club.service;

import java.time.LocalDateTime;

import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.common.error.GlobalException;
import org.lightnsalt.hikingdom.domain.club.entity.Club;
import org.lightnsalt.hikingdom.domain.club.entity.ClubMember;
import org.lightnsalt.hikingdom.domain.club.repository.ClubJoinRequestRepository;
import org.lightnsalt.hikingdom.domain.club.repository.ClubMemberRepository;
import org.lightnsalt.hikingdom.domain.club.repository.ClubRepository;
import org.lightnsalt.hikingdom.domain.member.entity.Member;
import org.lightnsalt.hikingdom.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClubAdminServiceImpl implements ClubAdminService {
	private final ClubRepository clubRepository;
	private final ClubMemberRepository clubMemberRepository;
	private final ClubJoinRequestRepository clubJoinRequestRepository;
	private final MemberRepository memberRepository;

	@Transactional
	@Override
	public void acceptClubJoinRequest(String email, Long clubId, Long memberId) {
		final Member host = memberRepository.findByEmail(email)
			.orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_UNAUTHORIZED));
		final Member candidate = memberRepository.findById(memberId)
			.orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));
		final Club club = clubRepository.findById(clubId)
			.orElseThrow(() -> new GlobalException(ErrorCode.CLUB_NOT_FOUND));

		// 소모임 관리자인지 확인
		if (!club.getHost().equals(host))
			throw new GlobalException(ErrorCode.MEMBER_UNAUTHORIZED);

		// 가입 신청자가 현재 소모임에 가입 요청했는지 확인 (동시에 여러 소모임 가입 신청 가능)
		if (clubJoinRequestRepository.findPendingRequestByMemberAndClub(candidate, club).isEmpty())
			throw new GlobalException(ErrorCode.CLUB_JOIN_REQUEST_NOT_FOUND);

		// 가입 신청자가 다른 소모임에 가입되어 있는지 확인
		if (clubMemberRepository.findCurrentClubByMember(candidate).isPresent())
			throw new GlobalException(ErrorCode.CLUB_ALREADY_JOINED);

		// 가입 신청 처리
		if (!acceptPendingJoinRequest(candidate, club))
			throw new GlobalException(ErrorCode.INTERNAL_SERVER_ERROR);
		clubMemberRepository.save(ClubMember.builder()
			.club(club)
			.member(candidate)
			.build());

		// 가입 신청자의 다른 가입 요청 취소
		clubJoinRequestRepository.retractPendingJoinRequestByMember(candidate, LocalDateTime.now());
	}

	@Transactional
	@Override
	public void rejectClubJoinRequest(String email, Long clubId, Long memberId) {
		final Member host = memberRepository.findByEmail(email)
			.orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_UNAUTHORIZED));
		final Member candidate = memberRepository.findById(memberId)
			.orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));
		final Club club = clubRepository.findById(clubId)
			.orElseThrow(() -> new GlobalException(ErrorCode.CLUB_NOT_FOUND));

		// 소모임 관리자인지 확인
		if (!club.getHost().equals(host))
			throw new GlobalException(ErrorCode.MEMBER_UNAUTHORIZED);

		if (!rejectPendingJoinRequest(candidate, club))
			throw new GlobalException(ErrorCode.INTERNAL_SERVER_ERROR);
	}

	@Transactional
	boolean acceptPendingJoinRequest(Member candidate, Club club) {
		return clubJoinRequestRepository.acceptPendingJoinRequestByMemberAndClub(candidate, club, LocalDateTime.now())
			> 0;
	}

	@Transactional
	boolean rejectPendingJoinRequest(Member candidate, Club club) {
		return clubJoinRequestRepository.rejectPendingJoinRequestByMemberAndClub(candidate, club, LocalDateTime.now())
			> 0;
	}
}
