package org.lightnsalt.hikingdom.service.hiking.service;

import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.common.error.GlobalException;
import org.lightnsalt.hikingdom.domain.entity.hiking.MemberHiking;
import org.lightnsalt.hikingdom.domain.repository.hiking.MemberHikingRepository;
import org.lightnsalt.hikingdom.domain.repository.member.MemberRepository;
import org.lightnsalt.hikingdom.service.hiking.dto.response.HikingRecordDetailRes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberHikingServiceImpl implements MemberHikingService {
	private final MemberHikingRepository memberHikingRepository;
	private final MemberRepository memberRepository;

	@Override
	@Transactional
	public HikingRecordDetailRes findHikingRecord(String email, Long hikingRecordId) {
		// 회원 찾기
		final Long memberId = memberRepository.findByEmailAndIsWithdraw(email, false)
			.orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_UNAUTHORIZED))
			.getId();

		// 등산 기록 찾기
		final MemberHiking memberHiking = memberHikingRepository.findById(hikingRecordId)
			.orElseThrow(() -> new GlobalException(ErrorCode.HIKING_RECORD_NOT_FOUND));

		// 회원 기록 확인
		if (!memberHiking.getMember().getId().equals(memberId)) {
			throw new GlobalException(ErrorCode.MEMBER_UNAUTHORIZED);
		}

		return new HikingRecordDetailRes(memberHiking);
	}
}
