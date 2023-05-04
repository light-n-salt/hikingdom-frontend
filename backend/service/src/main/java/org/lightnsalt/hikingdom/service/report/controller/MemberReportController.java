package org.lightnsalt.hikingdom.service.report.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.lightnsalt.hikingdom.common.dto.BaseResponseBody;
import org.lightnsalt.hikingdom.common.dto.ErrorResponseBody;
import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.service.report.dto.MemberReportReq;
import org.lightnsalt.hikingdom.service.report.service.MemberReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class MemberReportController {

	private final MemberReportService memberReportService;

	@PostMapping
	public ResponseEntity<?> memberReportAdd(Authentication authentication, @Valid @RequestBody MemberReportReq req,
		BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(ErrorResponseBody.of(ErrorCode.INVALID_INPUT_VALUE,
				bindingResult.getAllErrors().get(0).getDefaultMessage()), HttpStatus.BAD_REQUEST);
		}

		Long reportedId = memberReportService.saveMemberReport(authentication.getName(), req);
		Map<String, Long> result = new HashMap<>();
		result.put("id", reportedId);
		return new ResponseEntity<>(BaseResponseBody.of("컨텐츠가 신고되었습니다", result), HttpStatus.CREATED);
	}
}
