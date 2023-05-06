package org.lightnsalt.hikingdom.service.info.controller;

import javax.validation.Valid;

import org.lightnsalt.hikingdom.common.dto.CustomResponseBody;
import org.springframework.data.domain.Pageable;
import org.lightnsalt.hikingdom.common.dto.BaseResponseBody;
import org.lightnsalt.hikingdom.common.dto.CustomSlice;
import org.lightnsalt.hikingdom.common.dto.ErrorResponseBody;
import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.service.info.dto.request.MountainAddReq;
import org.lightnsalt.hikingdom.service.info.dto.response.MountainAddRes;
import org.lightnsalt.hikingdom.service.info.dto.response.MountainDetailRes;
import org.lightnsalt.hikingdom.service.info.dto.response.MountainListRes;
import org.lightnsalt.hikingdom.service.info.service.MountainInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1/info/mountains")
@RequiredArgsConstructor
public class MountainInfoController {

	/*
	- 컨트롤러 클래스 안에서 메서드 명을 작성 할 때는 아래와 같은 접미사를 붙인다.

		orderList() – 목록 조회 유형의 서비스

		orderDetails() – 단 건 상세 조회 유형의 controller 메서드

		orderSave() – 등록/수정/삭제 가 동시에 일어나는 유형의 controller 메서드

		orderAdd() – 등록만 하는 유형의 controller 메서드

		orderModify() – 수정만 하는 유형의 controller 메서드

		orderRemove() – 삭제만 하는 유형의 controller 메서드
	* */

	private final MountainInfoService mountainInfoService;

	@PostMapping("")
	public ResponseEntity<CustomResponseBody> mountainInfoAdd(@RequestBody @Valid final MountainAddReq mountainCreateReq,
		BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			log.error("has error in request body format in add mountain info api");
			return new ResponseEntity<>(ErrorResponseBody.of(ErrorCode.INVALID_INPUT_VALUE,
				bindingResult.getAllErrors().get(0).getDefaultMessage()), HttpStatus.BAD_REQUEST);
		}

		log.debug("request is : {}", mountainCreateReq);

		final MountainAddRes response = mountainInfoService.addMountainInfo(mountainCreateReq);
		return new ResponseEntity<>(BaseResponseBody.of("산 정보 생성에 성공했습니다", response), HttpStatus.CREATED);
	}

	@GetMapping("/{mountainId}")
	public ResponseEntity<CustomResponseBody> mountainInfoDetails(@PathVariable Long mountainId) {

		MountainDetailRes response = mountainInfoService.findMountainInfo(mountainId);
		return new ResponseEntity<>(BaseResponseBody.of("산 상세 정보 조회에 성공했습니다", response), HttpStatus.OK);
	}

	@GetMapping("")
	public ResponseEntity<CustomResponseBody> mountainInfoList(@RequestParam(defaultValue = "") String query,
		@RequestParam(defaultValue = "") String word, @RequestParam(defaultValue = "0") double lat,
		@RequestParam(defaultValue = "0") double lng, @RequestParam(defaultValue = "") Long id,
		Pageable pageable) {

		CustomSlice<MountainListRes> results = mountainInfoService.findAllMountainInfo(query, word, lat, lng, id,
			pageable);
		return new ResponseEntity<>(BaseResponseBody.of("산 검색에 성공했습니다", results), HttpStatus.OK);
	}

	@PostMapping("/today/{mountainId}")
	public ResponseEntity<CustomResponseBody> mountainInfoTodayAdd(@PathVariable Long mountainId) {

		mountainInfoService.addMountainInfoDaily(mountainId);
		return new ResponseEntity<>(BaseResponseBody.of("오늘의 산 등록에 성공했습니다"), HttpStatus.CREATED);
	}

}
