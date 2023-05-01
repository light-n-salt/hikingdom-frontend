package org.lightnsalt.hikingdom.domain.info.controller;

import javax.validation.Valid;

import org.lightnsalt.hikingdom.common.dto.BaseResponseBody;
import org.lightnsalt.hikingdom.common.dto.ErrorResponseBody;
import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.domain.info.dto.request.MountainAddReq;
import org.lightnsalt.hikingdom.domain.info.dto.response.MountainAddRes;
import org.lightnsalt.hikingdom.domain.info.dto.response.MountainGetRes;
import org.lightnsalt.hikingdom.domain.info.service.InfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1/info")
@RequiredArgsConstructor
public class InfoController {
	private final InfoService infoService;

	@PostMapping("/mountains")
	public ResponseEntity<?> addMountainInfo(@RequestBody @Valid final MountainAddReq mountainCreateReq,
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
	public ResponseEntity<?> getMountainInfo(@PathVariable Long mountainId) {

		MountainGetRes response = infoService.findMountainInfo(mountainId);

		return new ResponseEntity<>(BaseResponseBody.of("산 상세 정보 조회에 성공했습니다", response), HttpStatus.OK);
	}
}
