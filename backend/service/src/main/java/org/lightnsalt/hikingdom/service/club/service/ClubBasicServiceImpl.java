package org.lightnsalt.hikingdom.service.club.service;

import java.time.LocalDateTime;

import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.common.error.GlobalException;
import org.lightnsalt.hikingdom.domain.common.enumType.JoinRequestStatusType;
import org.lightnsalt.hikingdom.service.club.dto.request.ClubInfoReq;
import org.lightnsalt.hikingdom.service.club.dto.response.ClubSimpleDetailRes;
import org.lightnsalt.hikingdom.domain.entity.club.Club;
import org.lightnsalt.hikingdom.domain.entity.club.ClubMember;
import org.lightnsalt.hikingdom.domain.entity.club.record.ClubTotalHikingStatistic;
import org.lightnsalt.hikingdom.domain.repository.club.ClubJoinRequestRepository;
import org.lightnsalt.hikingdom.domain.repository.club.ClubMemberRepository;
import org.lightnsalt.hikingdom.domain.repository.club.ClubRepository;
import org.lightnsalt.hikingdom.domain.repository.club.ClubTotalHikingStatisticRepository;
import org.lightnsalt.hikingdom.domain.entity.info.BaseAddressInfo;
import org.lightnsalt.hikingdom.domain.repository.info.BaseAddressInfoRepository;
import org.lightnsalt.hikingdom.domain.entity.member.Member;
import org.lightnsalt.hikingdom.domain.repository.member.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 소모임 기본 CRUD 기능 관련
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ClubBasicServiceImpl implements ClubBasicService {
	private final ClubRepository clubRepository;
	private final ClubMemberRepository clubMemberRepository;
	private final ClubJoinRequestRepository clubJoinRequestRepository;
	private final ClubTotalHikingStatisticRepository clubTotalHikingStatisticRepository;
	private final BaseAddressInfoRepository baseAddressInfoRepository;
	private final MemberRepository memberRepository;

	@Transactional
	@Override
	public Long addClub(String email, ClubInfoReq clubInfoReq) {
		final Member host = memberRepository.findByEmailAndIsWithdraw(email, false)
			.orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_UNAUTHORIZED));

		checkDuplicateClubName(clubInfoReq.getName());

		// 소모임 가입 여부 확인. 이미 가입된 경우, 소모임 생성 X
		if (clubMemberRepository.findByMemberIdAndIsWithdraw(host.getId(), false).isPresent())
			throw new GlobalException(ErrorCode.CLUB_ALREADY_JOINED);

		// 소모임 신청 취소
		clubJoinRequestRepository.updatePendingJoinRequestByMember(host, JoinRequestStatusType.RETRACTED,
			LocalDateTime.now());

		final BaseAddressInfo baseAddressInfo = getBaseAddressInfo(clubInfoReq);

		Club club = Club.builder()
			.host(host)
			.name(clubInfoReq.getName())
			.description(clubInfoReq.getDescription())
			.baseAddress(baseAddressInfo)
			.build();

		final Club savedclub = clubRepository.save(club);
		clubTotalHikingStatisticRepository.save(ClubTotalHikingStatistic.builder()
			.club(savedclub)
			.build());

		clubMemberRepository.save(ClubMember.builder()
			.member(host)
			.club(club)
			.build());

		return club.getId();
	}

	@Transactional
	@Override
	public void modifyClub(String email, Long clubId, ClubInfoReq clubInfoReq) {
		final Member host = memberRepository.findByEmailAndIsWithdraw(email, false)
			.orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_UNAUTHORIZED));

		final Club club = clubRepository.findById(clubId)
			.orElseThrow(() -> new GlobalException(ErrorCode.CLUB_NOT_FOUND));

		if (!club.getHost().getId().equals(host.getId())) {
			log.error("ClubBasicService:modifyClub: hostId is not equal, {} {}", host.getId(), club.getHost().getId());
			throw new GlobalException(ErrorCode.MEMBER_UNAUTHORIZED);
		}

		// 소모임 이름 체크
		if (!(club.getName().equals(clubInfoReq.getName()))) {
			checkDuplicateClubName(clubInfoReq.getName());
		}

		// 지역코드 체크
		BaseAddressInfo baseAddressInfo;
		if (!club.getBaseAddress().getDongCode().equals(clubInfoReq.getDongCode())) {
			baseAddressInfo = getBaseAddressInfo(clubInfoReq);
		} else {
			baseAddressInfo = club.getBaseAddress();
		}

		if (!updateClub(clubId, clubInfoReq, baseAddressInfo))
			throw new GlobalException(ErrorCode.INTERNAL_SERVER_ERROR);
	}

	@Transactional
	@Override
	public ClubSimpleDetailRes findClubSimpleDetail(Long clubId) {
		final Club club = clubRepository.findById(clubId)
			.orElseThrow(() -> new GlobalException(ErrorCode.CLUB_NOT_FOUND));

		return ClubSimpleDetailRes.builder()
			.hostId(club.getHost().getId())
			.clubId(clubId)
			.groupName(club.getName())
			.build();
	}

	@Transactional
	@Override
	public void checkDuplicateClubName(String clubName) {
		if (clubRepository.findByNameAndIsDeleted(clubName, false).isPresent()) {
			throw new GlobalException(ErrorCode.DUPLICATE_CLUB_NAME);
		}
	}

	@Transactional
	public boolean updateClub(Long clubId, ClubInfoReq clubInfoReq, BaseAddressInfo baseAddressInfo) {
		return clubRepository.updateClubProfile(clubId, clubInfoReq.getName(), clubInfoReq.getDescription(),
			baseAddressInfo, LocalDateTime.now()) > 0;
	}

	@Transactional
	public BaseAddressInfo getBaseAddressInfo(ClubInfoReq clubInfoReq) {
		if (clubInfoReq.getDongCode() != null) {
			return baseAddressInfoRepository.findById(clubInfoReq.getDongCode())
				.orElseThrow(() -> new GlobalException(ErrorCode.BASE_ADDRESS_NOT_FOUND));
		}
		return baseAddressInfoRepository.findById("0000000000") // 전국
			.orElseThrow(() -> new GlobalException(ErrorCode.BASE_ADDRESS_NOT_FOUND));
	}
}
