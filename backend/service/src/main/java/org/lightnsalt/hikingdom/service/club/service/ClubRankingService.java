package org.lightnsalt.hikingdom.service.club.service;

import org.lightnsalt.hikingdom.common.dto.CustomSlice;
import org.lightnsalt.hikingdom.service.club.dto.response.ClubSearchRes;
import org.springframework.data.domain.Pageable;

public interface ClubRankingService {
	CustomSlice<ClubSearchRes> findRankingList(String sort, Long clubId, Pageable pageable);
}
