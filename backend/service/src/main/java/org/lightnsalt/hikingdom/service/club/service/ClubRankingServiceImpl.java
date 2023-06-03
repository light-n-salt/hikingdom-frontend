package org.lightnsalt.hikingdom.service.club.service;

import java.time.LocalDate;

import org.lightnsalt.hikingdom.common.dto.CustomSlice;
import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.common.error.GlobalException;
import org.lightnsalt.hikingdom.service.club.dto.response.ClubSearchRes;
import org.lightnsalt.hikingdom.service.club.repository.record.ClubRankingRepositoryCustom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClubRankingServiceImpl implements ClubRankingService {
	private final ClubRankingRepositoryCustom clubRankingRepositoryCustom;

	@Transactional
	@Override
	public CustomSlice<ClubSearchRes> findRankingList(String sort, Long clubId, Pageable pageable) {
		if (!sort.matches("^(|mountain|asset|member)")) {
			log.error("ClubRankingService:findRankingList: Invalid Sort {}", sort);
			throw new GlobalException(ErrorCode.INVALID_INPUT_VALUE);
		}

		final Slice<ClubSearchRes> result;
		switch (sort) {
			case "mountain":
				result = clubRankingRepositoryCustom.sortClubRankingByMountainCount(LocalDate.now(), clubId,
					pageable);
				break;
			case "asset":
				result = clubRankingRepositoryCustom.sortClubRankingByAssetCount(LocalDate.now(), clubId, pageable);
				break;
			case "member":
				result = clubRankingRepositoryCustom.sortClubRankingByMemberCount(LocalDate.now(), clubId, pageable);
				break;
			default:
				result = clubRankingRepositoryCustom.sortClubRankingByRanking(LocalDate.now(), clubId, pageable);
				break;
		}

		return new CustomSlice<>(result);
	}
}
