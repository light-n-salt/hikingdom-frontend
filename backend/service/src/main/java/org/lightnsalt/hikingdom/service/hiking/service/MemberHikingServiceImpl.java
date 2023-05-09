package org.lightnsalt.hikingdom.service.hiking.service;

import org.lightnsalt.hikingdom.common.dto.CustomSlice;
import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.common.error.GlobalException;
import org.lightnsalt.hikingdom.domain.entity.hiking.MemberHiking;
import org.lightnsalt.hikingdom.service.hiking.repository.MemberHikingRepository;
import org.lightnsalt.hikingdom.service.hiking.repository.MemberHikingRepositoryCustom;
import org.lightnsalt.hikingdom.service.member.repository.MemberRepository;
import org.lightnsalt.hikingdom.service.hiking.dto.response.HikingRecordDetailRes;
import org.lightnsalt.hikingdom.service.hiking.dto.response.HikingRecordListRes;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
	private final MemberHikingRepositoryCustom memberHikingRepositoryCustom;

	@Override
	@Transactional
	public HikingRecordDetailRes findHikingRecord(String nickname, Long hikingRecordId) {
		// 회원 찾기
		final Long memberId = memberRepository.findByNicknameAndIsWithdraw(nickname, false)
			.orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND))
			.getId();

		// 등산 기록 찾기
		final MemberHiking memberHiking = memberHikingRepository.findById(hikingRecordId)
			.orElseThrow(() -> new GlobalException(ErrorCode.HIKING_RECORD_NOT_FOUND));

		// 회원 기록 확인
		if (!memberHiking.getMember().getId().equals(memberId)) {
			log.error("MemberHikingService:findHikingRecord: nickname {}, memberId {}, hikingRecordId {}", nickname,
				memberId, hikingRecordId);
			throw new GlobalException(ErrorCode.HIKING_RECORD_NOT_FOUND);
		}

		return new HikingRecordDetailRes(memberHiking);
	}

	@Override
	@Transactional
	public CustomSlice<HikingRecordListRes> findHikingRecordList(String nickname, Long hikingRecordId,
		Pageable pageable) {
		// 회원 확인
		final Long memberId = memberRepository.findByNicknameAndIsWithdraw(nickname, false)
			.orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND))
			.getId();

		// 기록 가져오기
		final Slice<MemberHiking> memberHiking = memberHikingRepositoryCustom.findByMemberHikingList(memberId,
			hikingRecordId, pageable);

		return new CustomSlice<>(memberHiking.map(HikingRecordListRes::new));
	}
}
