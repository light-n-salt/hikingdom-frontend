package org.lightnsalt.hikingdom.domain.club.service;

import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.common.error.GlobalException;
import org.lightnsalt.hikingdom.domain.club.dto.request.ClubInfoReq;
import org.lightnsalt.hikingdom.domain.club.dto.response.ClubSimpleDetailRes;
import org.lightnsalt.hikingdom.domain.club.entity.Club;
import org.lightnsalt.hikingdom.domain.club.entity.ClubMember;
import org.lightnsalt.hikingdom.domain.club.repository.ClubMemberRepository;
import org.lightnsalt.hikingdom.domain.club.repository.ClubRepository;
import org.lightnsalt.hikingdom.domain.info.entity.BaseAddressInfo;
import org.lightnsalt.hikingdom.domain.info.repository.BaseAddressInfoRepository;
import org.lightnsalt.hikingdom.domain.member.entity.Member;
import org.lightnsalt.hikingdom.domain.member.repository.MemberRepository;
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
	private final BaseAddressInfoRepository baseAddressInfoRepository;
	private final MemberRepository memberRepository;

	@Transactional
	@Override
	public Long addClub(String email, ClubInfoReq clubInfoReq) {
		Member host = memberRepository.findByEmail(email)
			.orElseThrow(() -> new GlobalException(ErrorCode.INVALID_LOGIN));

		checkDuplicateClubName(clubInfoReq.getName());

		if (clubMemberRepository.existsByMemberId(host.getId())) {
			throw new GlobalException(ErrorCode.ALREADY_JOINED_CLUB);
		}

		BaseAddressInfo baseAddressInfo = getBaseAddressInfo(clubInfoReq);

		Club club = Club.builder()
			.host(host)
			.name(clubInfoReq.getName())
			.description(clubInfoReq.getDescription())
			.baseAddress(baseAddressInfo)
			.build();

		clubRepository.save(club);
		clubMemberRepository.save(new ClubMember(host, club));

		return club.getId();
	}

	@Transactional
	@Override
	public void modifyClub(String email, Long clubId, ClubInfoReq clubInfoReq) {
		Member host = memberRepository.findByEmail(email)
			.orElseThrow(() -> new GlobalException(ErrorCode.INVALID_LOGIN));

		Club club = clubRepository.findById(clubId).orElseThrow(() -> new GlobalException(ErrorCode.CLUB_NOT_FOUND));

		if (!club.getHost().getId().equals(host.getId())) {
			log.error("ClubBasicService:modifyClub: hostId is not equal, {} {}", host.getId(), club.getHost().getId());
			throw new GlobalException(ErrorCode.INVALID_LOGIN);
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

		clubRepository.updateClub(clubInfoReq.getName(), clubInfoReq.getDescription(), baseAddressInfo, clubId);
	}

	@Override
	public ClubSimpleDetailRes findClubSimpleDetail(Long clubId) {
		Club club = clubRepository.findById(clubId).orElseThrow(() -> new GlobalException(ErrorCode.CLUB_NOT_FOUND));

		return ClubSimpleDetailRes.builder()
			.hostId(club.getHost().getId())
			.groupId(clubId)
			.groupName(club.getName())
			.build();
	}

	@Override
	public void checkDuplicateClubName(String clubName) {
		if (clubRepository.findByNameAndIsNotDeleted(clubName).isPresent()) {
			throw new GlobalException(ErrorCode.DUPLICATE_CLUB_NAME);
		}
	}

	private BaseAddressInfo getBaseAddressInfo(ClubInfoReq clubInfoReq) {
		if (clubInfoReq.getDongCode() != null) {
			return baseAddressInfoRepository.findById(clubInfoReq.getDongCode())
				.orElseThrow(() -> new GlobalException(ErrorCode.BASE_ADDRESS_NOT_FOUND));
		}
		return baseAddressInfoRepository.findById("0000000000") // 전국
			.orElseThrow(() -> new GlobalException(ErrorCode.BASE_ADDRESS_NOT_FOUND));
	}
}
