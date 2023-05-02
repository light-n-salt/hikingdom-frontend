package org.lightnsalt.hikingdom.domain.info.controller;

import org.lightnsalt.hikingdom.common.dto.BaseResponseBody;
import org.lightnsalt.hikingdom.domain.info.service.LocationInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1/info/location")
@RequiredArgsConstructor
public class LocationInfoController {
	private final LocationInfoService locationInfoService;

	@GetMapping
	public ResponseEntity<?> sidoList(@RequestParam(required = false, defaultValue = "sido") String query,
		@RequestParam(required = false, defaultValue = "0000000000") String word) {
		return new ResponseEntity<>(
			BaseResponseBody.of("지역 정보 검색에 성공했습니다", locationInfoService.findBaseAddressInfoList(query, word)),
			HttpStatus.OK);
	}
}
