package org.lightnsalt.hikingdom.service.club.controller;

import org.lightnsalt.hikingdom.common.dto.BaseResponseBody;
import org.lightnsalt.hikingdom.common.dto.CustomResponseBody;
import org.lightnsalt.hikingdom.common.dto.CustomSlice;
import org.lightnsalt.hikingdom.service.club.dto.response.ClubSearchRes;
import org.lightnsalt.hikingdom.service.club.service.ClubRankingService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/clubs/ranking")
@RequiredArgsConstructor
public class ClubRankingController {
	private final ClubRankingService clubRankingService;

	@GetMapping
	public ResponseEntity<CustomResponseBody> rankingList(@RequestParam(defaultValue = "") String sort,
		 @RequestParam(defaultValue = "") Long clubId, Pageable pageable) {
		CustomSlice<ClubSearchRes> clubSearchRes = clubRankingService.findRankingList(sort, clubId, pageable);
		return new ResponseEntity<>(BaseResponseBody.of("소모임 랭킹 검색에 성공했습니다", clubSearchRes), HttpStatus.OK);
	}

}
