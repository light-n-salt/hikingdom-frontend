package org.lightnsalt.hikingdom.service.hiking.controller;

import java.util.List;

import org.lightnsalt.hikingdom.common.dto.BaseResponseBody;
import org.lightnsalt.hikingdom.common.dto.CustomResponseBody;
import org.lightnsalt.hikingdom.service.hiking.dto.request.HikingRecordReq;
import org.lightnsalt.hikingdom.service.hiking.dto.response.TodayMeetupRes;
import org.lightnsalt.hikingdom.service.hiking.service.HikingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/hiking")
@RequiredArgsConstructor
public class HikingController {
	private final HikingService hikingService;

	@GetMapping("/meetups")
	public ResponseEntity<CustomResponseBody> todayMeetupList(Authentication authentication) {

		List<TodayMeetupRes> result = hikingService.findTodayMeetup(authentication.getName());
		return new ResponseEntity<>(BaseResponseBody.of("오늘 일정 조회에 성공했습니다", result), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<CustomResponseBody> hikingRecordAdd(Authentication authentication, @RequestBody HikingRecordReq hikingRecordReq) {
		Long hikingRecordId = hikingService.saveHikingRecord(authentication.getName(), hikingRecordReq);
		return new ResponseEntity<>(BaseResponseBody.of("등산 기록 생성에 성공했습니다", hikingRecordId), HttpStatus.CREATED);
	}
}
