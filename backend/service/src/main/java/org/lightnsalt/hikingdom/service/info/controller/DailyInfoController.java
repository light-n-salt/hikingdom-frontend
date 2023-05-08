package org.lightnsalt.hikingdom.service.info.controller;

import org.lightnsalt.hikingdom.common.dto.BaseResponseBody;
import org.lightnsalt.hikingdom.common.dto.CustomResponseBody;
import org.lightnsalt.hikingdom.service.info.dto.response.ClubDailyRes;
import org.lightnsalt.hikingdom.service.info.dto.response.MountainDailyRes;
import org.lightnsalt.hikingdom.service.info.service.DailyInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1/info/today")
@RequiredArgsConstructor
public class DailyInfoController {

	/*
	- 컨트롤러 클래스 안에서 메서드 명을 작성 할 때는 아래와 같은 접미사를 붙인다.

		orderList() – 목록 조회 유형의 서비스

		orderDetails() – 단 건 상세 조회 유형의 controller 메서드

		orderSave() – 등록/수정/삭제 가 동시에 일어나는 유형의 controller 메서드

		orderAdd() – 등록만 하는 유형의 controller 메서드

		orderModify() – 수정만 하는 유형의 controller 메서드

		orderRemove() – 삭제만 하는 유형의 controller 메서드
	* */

	private final DailyInfoService dailyInfoService;

	@GetMapping("/mountain")
	public ResponseEntity<CustomResponseBody> dailyMountainDetails() {

		MountainDailyRes result = dailyInfoService.findDailyMountain();
		return new ResponseEntity<>(BaseResponseBody.of("오늘의 산 조회에 성공했습니다", result), HttpStatus.OK);
	}

	@GetMapping("/club")
	public ResponseEntity<CustomResponseBody> dailyClubDetails() {

		ClubDailyRes result = dailyInfoService.findDailyClub();
		return new ResponseEntity<>(BaseResponseBody.of("오늘의 소모임 조회에 성공했습니다", result), HttpStatus.OK);
	}

}
