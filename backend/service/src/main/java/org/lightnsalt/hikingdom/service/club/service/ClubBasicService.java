package org.lightnsalt.hikingdom.service.club.service;


import org.lightnsalt.hikingdom.common.dto.CustomSlice;
import org.lightnsalt.hikingdom.service.club.dto.request.ClubInfoReq;
import org.lightnsalt.hikingdom.service.club.dto.response.ClubDetailRes;
import org.lightnsalt.hikingdom.service.club.dto.response.ClubSearchRes;
import org.lightnsalt.hikingdom.service.club.dto.response.ClubSimpleDetailRes;
import org.springframework.data.domain.Pageable;

public interface ClubBasicService {
	CustomSlice<ClubSearchRes> findClubList(String query, String word, Long id, Pageable pageable);

	Long addClub(String email, ClubInfoReq clubInfoReq);

	void modifyClub(String email, Long clubId, ClubInfoReq clubInfoReq);

	ClubSimpleDetailRes findClubSimpleDetail(Long clubId);

	void checkDuplicateClubName(String clubName);

	ClubDetailRes findClubDetail(String email, Long clubId);
}
