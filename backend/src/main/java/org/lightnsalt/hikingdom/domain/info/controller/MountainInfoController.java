package org.lightnsalt.hikingdom.domain.info.controller;

import java.util.List;

import javax.validation.Valid;

import org.lightnsalt.hikingdom.common.dto.BaseResponseBody;
import org.lightnsalt.hikingdom.common.dto.ErrorResponseBody;
import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.domain.info.dto.request.MountainAddReq;
import org.lightnsalt.hikingdom.domain.info.dto.response.MountainAddRes;
import org.lightnsalt.hikingdom.domain.info.dto.response.MountainDetailRes;
import org.lightnsalt.hikingdom.domain.info.dto.response.MountainListRes;
import org.lightnsalt.hikingdom.domain.info.service.InfoService;
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
@RequestMapping("/api/v1/info")
@RequiredArgsConstructor
public class MountainInfoController {
	private final InfoService infoService;

	@PostMapping("/mountains")
	public ResponseEntity<?> mountainInfoAdd(@RequestBody @Valid final MountainAddReq mountainCreateReq,
		BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			log.error("has error in request body format in add mountain info api");
			return new ResponseEntity<>(ErrorResponseBody.of(ErrorCode.INVALID_INPUT_VALUE,
				bindingResult.getAllErrors().get(0).getDefaultMessage()), HttpStatus.BAD_REQUEST);
		}

		log.debug("request is : {}", mountainCreateReq);

		final MountainAddRes response = infoService.addMountainInfo(mountainCreateReq);
		return new ResponseEntity<>(BaseResponseBody.of("산 정보 생성에 성공했습니다", response), HttpStatus.CREATED);
	}

	@GetMapping("/mountains/{mountainId}")
	public ResponseEntity<?> mountainInfoDetails(@PathVariable Long mountainId) {

		MountainDetailRes response = infoService.findMountainInfo(mountainId);
		return new ResponseEntity<>(BaseResponseBody.of("산 상세 정보 조회에 성공했습니다", response), HttpStatus.OK);
	}

	@GetMapping("/mountains")
	public ResponseEntity<?> mountainInfoList(@RequestParam(defaultValue = "") String query,
		@RequestParam(defaultValue = "") String word, @RequestParam(defaultValue = "0") double lat,
		@RequestParam(defaultValue = "0") double lng, @RequestParam(defaultValue = "") Long id) {

		List<MountainListRes> results = infoService.findAllMountainInfo(query, word, lat, lng, id);
		return new ResponseEntity<>(BaseResponseBody.of("산 검색에 성공했습니다", results), HttpStatus.OK);
	}
}
