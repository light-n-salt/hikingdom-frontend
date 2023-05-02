package org.lightnsalt.hikingdom.domain.club.service;

import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.common.error.GlobalException;
import org.lightnsalt.hikingdom.domain.club.dto.request.ClubAddReq;
import org.lightnsalt.hikingdom.domain.club.dto.request.ClubNameReq;
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
	public Long addClub(String email, ClubAddReq clubAddReq) {
		Member host = memberRepository.findByEmail(email)
			.orElseThrow(() -> new GlobalException(ErrorCode.INVALID_LOGIN));

		if (clubRepository.findByNameAndIsNotDeleted(clubAddReq.getName()).isPresent()) {
			throw new GlobalException(ErrorCode.DUPLICATE_CLUB_NAME);
		}

		if (clubMemberRepository.existsByMemberId(host.getId())) {
			throw new GlobalException(ErrorCode.ALREADY_JOINED_CLUB);
		}

		BaseAddressInfo baseAddressInfo;

		if (clubAddReq.getDongCode() != null) {
			baseAddressInfo = baseAddressInfoRepository.findById(clubAddReq.getDongCode())
				.orElseThrow(() -> new GlobalException(ErrorCode.BASE_ADDRESS_NOT_FOUND));
		} else {
			baseAddressInfo = baseAddressInfoRepository.findById("0000000000") // 전국
				.orElseThrow(() -> new GlobalException(ErrorCode.BASE_ADDRESS_NOT_FOUND));
		}

		Club club = Club.builder()
			.host(host)
			.name(clubAddReq.getName())
			.description(clubAddReq.getDescription())
			.baseAddress(baseAddressInfo)
			.build();

		clubRepository.save(club);
		clubMemberRepository.save(new ClubMember(host, club));

		return club.getId();
	}
}
