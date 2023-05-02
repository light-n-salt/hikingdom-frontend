package org.lightnsalt.hikingdom.domain.club.controller;

import java.util.Map;

import javax.validation.Valid;

import org.lightnsalt.hikingdom.common.dto.BaseResponseBody;
import org.lightnsalt.hikingdom.common.dto.ErrorResponseBody;
import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.domain.club.dto.request.ClubAddReq;
import org.lightnsalt.hikingdom.domain.club.dto.request.ClubNameReq;
import org.lightnsalt.hikingdom.domain.club.service.ClubBasicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/clubs")
@RequiredArgsConstructor
public class ClubBasicController {
	private final ClubBasicService clubBasicService;

	@PostMapping
	public ResponseEntity<?> clubAdd(Authentication authentication, @RequestBody @Valid ClubAddReq clubAddReq,
		BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(ErrorResponseBody.of(ErrorCode.INVALID_INPUT_VALUE,
				bindingResult.getAllErrors().get(0).getDefaultMessage()),
				HttpStatus.BAD_REQUEST);
		}

		Long clubId = clubBasicService.addClub(authentication.getName(), clubAddReq);
		return new ResponseEntity<>(BaseResponseBody.of("소모임 생성에 성공했습니다", Map.of("clubId", clubId)),
			HttpStatus.CREATED);
	}

	@GetMapping("/check-duplicate")
	public ResponseEntity<?> clubNameCheck(@RequestBody @Valid ClubNameReq clubNameReq,
		BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(ErrorResponseBody.of(ErrorCode.INVALID_INPUT_VALUE,
				bindingResult.getAllErrors().get(0).getDefaultMessage()),
				HttpStatus.BAD_REQUEST);
		}

		clubBasicService.checkDuplicateClubName(clubNameReq);
		return new ResponseEntity<>(BaseResponseBody.of("사용할 수 있는 소모임 이름입니다"), HttpStatus.OK);
	}
}
