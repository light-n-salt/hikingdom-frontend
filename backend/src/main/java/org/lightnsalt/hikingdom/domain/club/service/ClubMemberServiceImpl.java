package org.lightnsalt.hikingdom.domain.club.service;

import java.time.LocalDateTime;

import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.common.error.GlobalException;
import org.lightnsalt.hikingdom.domain.club.common.enumtype.JoinRequestStatusType;
import org.lightnsalt.hikingdom.domain.club.entity.Club;
import org.lightnsalt.hikingdom.domain.club.entity.ClubJoinRequest;
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
public class ClubMemberServiceImpl implements ClubMemberService {
	private final ClubRepository clubRepository;
	private final ClubMemberRepository clubMemberRepository;
	private final ClubJoinRequestRepository clubJoinRequestRepository;
	private final MemberRepository memberRepository;

	@Transactional
	@Override
	public void sendClubJoinRequest(String email, Long clubId) {
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new GlobalException(ErrorCode.INVALID_LOGIN));
		Club club = clubRepository.findById(clubId).orElseThrow(() -> new GlobalException(ErrorCode.CLUB_NOT_FOUND));

		// 현재 소모임에 가입되어 있는지 확인
		if (clubMemberRepository.findCurrentClubByMember(member).isPresent())
			throw new GlobalException(ErrorCode.CLUB_ALREADY_JOINED);

		// 현재 소모임에 가입 요청했는지 확인 (동시에 여러 소모임 가입 신청 가능)
		if (clubJoinRequestRepository.findPendingRequestByMemberAndClub(member, club).isPresent())
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
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new GlobalException(ErrorCode.INVALID_LOGIN));
		Club club = clubRepository.findById(clubId).orElseThrow(() -> new GlobalException(ErrorCode.CLUB_NOT_FOUND));

		ClubJoinRequest clubJoinRequest = clubJoinRequestRepository.findPendingRequestByMemberAndClub(member, club)
			.orElseThrow(() -> new GlobalException(ErrorCode.CLUB_JOIN_REQUEST_NOT_FOUND));

		clubJoinRequestRepository.retractPendingJoinRequestByMemberAndClub(clubJoinRequest.getMember(),
			clubJoinRequest.getClub(), LocalDateTime.now());
	}
}
