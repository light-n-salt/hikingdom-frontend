package org.lightnsalt.hikingdom.domain.club.service;

import static org.lightnsalt.hikingdom.common.enumType.JoinRequestStatusType.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.common.error.GlobalException;
import org.lightnsalt.hikingdom.common.enumType.JoinRequestStatusType;
import org.lightnsalt.hikingdom.domain.club.dto.response.MemberListRes;
import org.lightnsalt.hikingdom.domain.club.entity.Club;
import org.lightnsalt.hikingdom.domain.club.entity.ClubJoinRequest;
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
public class ClubMemberServiceImpl implements ClubMemberService {
	private final ClubRepository clubRepository;
	private final ClubMemberRepository clubMemberRepository;
	private final ClubJoinRequestRepository clubJoinRequestRepository;
	private final MemberRepository memberRepository;

	@Transactional
	@Override
	public void sendClubJoinRequest(String email, Long clubId) {
		final Member member = memberRepository.findByEmailAndIsWithdraw(email, false)
			.orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_UNAUTHORIZED));
		final Club club = clubRepository.findById(clubId)
			.orElseThrow(() -> new GlobalException(ErrorCode.CLUB_NOT_FOUND));

		// 현재 소모임에 가입되어 있는지 확인
		if (clubMemberRepository.findByMemberIdAndIsWithdraw(member.getId(), false).isPresent())
			throw new GlobalException(ErrorCode.CLUB_ALREADY_JOINED);

		// 현재 소모임에 가입 요청했는지 확인 (동시에 여러 소모임 가입 신청 가능)
		if (clubJoinRequestRepository.findByMemberIdAndClubIdAndStatus(member.getId(), clubId,
			PENDING).isPresent())
			throw new GlobalException(ErrorCode.CLUB_JOIN_REQUEST_PENDING);

		ClubJoinRequest clubJoinRequest = ClubJoinRequest.builder()
			.club(club)
			.member(member)
			.status(PENDING)
			.build();

		clubJoinRequestRepository.save(clubJoinRequest);
	}

	@Transactional
	@Override
	public void retractClubJoinRequest(String email, Long clubId) {
		final Member member = memberRepository.findByEmailAndIsWithdraw(email, false)
			.orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_UNAUTHORIZED));
		final Club club = clubRepository.findByIdAndIsDeleted(clubId, false)
			.orElseThrow(() -> new GlobalException(ErrorCode.CLUB_NOT_FOUND));

		final ClubJoinRequest clubJoinRequest = clubJoinRequestRepository.findByMemberIdAndClubIdAndStatus(
				member.getId(), club.getId(), PENDING)
			.orElseThrow(() -> new GlobalException(ErrorCode.CLUB_JOIN_REQUEST_NOT_FOUND));

		if (!retractPendingJoinRequest(clubJoinRequest.getMember(), clubJoinRequest.getClub()))
			throw new GlobalException(ErrorCode.INTERNAL_SERVER_ERROR);
	}

	@Transactional
	boolean retractPendingJoinRequest(Member member, Club club) {
		return clubJoinRequestRepository.updatePendingJoinRequestByMemberAndClub(member, club,
			JoinRequestStatusType.RETRACTED, LocalDateTime.now()) > 0;
	}

	@Override
	@Transactional
	public Map<String, List<MemberListRes>> findClubMember(String email, Long clubId) {
		// 회원 정보 id 가져오기 -> 소모임장인지 확인하기 위한 용도
		final Long memberId = memberRepository.findByEmailAndIsWithdraw(email, false)
			.orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_UNAUTHORIZED)).getId();

		// 소모임장 id 가져오기
		final Long hostId = clubRepository.findById(clubId)
			.orElseThrow(() -> new GlobalException(ErrorCode.CLUB_NOT_FOUND))
			.getHost().getId();

		Map<String, List<MemberListRes>> results = new HashMap<>();

		// 소모임장일 경우
		if (memberId.equals(hostId)) {
			// 신청한 회원 정보 가져오기
			List<ClubJoinRequest> list = clubJoinRequestRepository.findByClubIdAndMemberIsWithdrawAndStatus(clubId, false,
				PENDING);
			List<MemberListRes> result = list.stream()
				.map((clubJoinRequest) -> new MemberListRes(clubJoinRequest.getMember())).collect(Collectors.toList());
			results.put("request", result);
		}

		// 소모임에 가입되어있는 회원 정보 가져오기
		List<ClubMember> list = clubMemberRepository.findByClubIdAndIsWithdraw(clubId, false);
		List<MemberListRes> result = list.stream().map((clubMember) -> new MemberListRes(clubMember.getMember()))
			.collect(Collectors.toList());

		results.put("member", result);

		return results;
	}
}
