package org.lightnsalt.hikingdom.service.club.controller;

import java.util.List;
import java.util.Map;

import org.lightnsalt.hikingdom.common.dto.BaseResponseBody;
import org.lightnsalt.hikingdom.common.dto.CustomResponseBody;
import org.lightnsalt.hikingdom.service.club.dto.response.ClubAssetRes;
import org.lightnsalt.hikingdom.service.club.service.ClubAssetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1/clubs/{clubId}")
@RequiredArgsConstructor
public class ClubAssetController {
	private final ClubAssetService clubAssetService;

	@GetMapping("/mountains")
	public ResponseEntity<CustomResponseBody> clubMountainList(@PathVariable Long clubId) {
		List<ClubAssetRes> assets = clubAssetService.findClubMountainList(clubId);

		return new ResponseEntity<>(BaseResponseBody.of("모임 산 조회에 성공했습니다", Map.of("assets", assets)),
			HttpStatus.OK);
	}
}
