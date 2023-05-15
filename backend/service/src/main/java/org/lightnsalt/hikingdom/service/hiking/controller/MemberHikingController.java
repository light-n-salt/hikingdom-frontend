package org.lightnsalt.hikingdom.service.hiking.controller;

import org.lightnsalt.hikingdom.common.dto.BaseResponseBody;
import org.lightnsalt.hikingdom.common.dto.CustomResponseBody;
import org.lightnsalt.hikingdom.common.dto.CustomSlice;
import org.lightnsalt.hikingdom.service.hiking.dto.response.HikingRecordDetailRes;
import org.lightnsalt.hikingdom.service.hiking.dto.response.HikingRecordListRes;
import org.lightnsalt.hikingdom.service.hiking.service.MemberHikingService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/members/{nickname}/hiking")
@RequiredArgsConstructor
public class MemberHikingController {
	private final MemberHikingService memberHikingService;

	@GetMapping("/{hikingRecordId}")
	public ResponseEntity<CustomResponseBody> hikingRecordDetail(@PathVariable String nickname,
		@PathVariable Long hikingRecordId) {
		HikingRecordDetailRes result = memberHikingService.findHikingRecord(nickname, hikingRecordId);
		return new ResponseEntity<>(BaseResponseBody.of("등산기록 상세조회에 성공했습니다", result), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<CustomResponseBody> hikingRecordList(@PathVariable String nickname,
		@RequestParam(defaultValue = "") Long hikingRecordId, Pageable pageable) {

		CustomSlice<HikingRecordListRes> result = memberHikingService.findHikingRecordList(nickname, hikingRecordId,
			pageable);
		return new ResponseEntity<>(BaseResponseBody.of("전체 등산기록 조회에 성공했습니다", result), HttpStatus.OK);
	}
}
