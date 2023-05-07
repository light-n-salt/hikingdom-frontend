package org.lightnsalt.hikingdom.service.member.controller;

import org.lightnsalt.hikingdom.common.dto.BaseResponseBody;
import org.lightnsalt.hikingdom.common.dto.CustomResponseBody;
import org.lightnsalt.hikingdom.service.member.dto.response.HikingRecordDetailRes;
import org.lightnsalt.hikingdom.service.member.service.MemberHikingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/members/hiking")
@RequiredArgsConstructor
public class MemberHikingController {
	private final MemberHikingService memberHikingService;

	@GetMapping("/{hikingRecordId}")
	public ResponseEntity<CustomResponseBody> hikingRecordDetail(Authentication authentication,
		@PathVariable Long hikingRecordId) {

		HikingRecordDetailRes result = memberHikingService.findHikingRecord(authentication.getName(), hikingRecordId);
		return new ResponseEntity<>(BaseResponseBody.of("등산기록 상세조회에 성공해습니다", result), HttpStatus.OK);
	}
}
