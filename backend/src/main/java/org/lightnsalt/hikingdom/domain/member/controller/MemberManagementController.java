package org.lightnsalt.hikingdom.domain.member.controller;

import javax.validation.Valid;

import org.lightnsalt.hikingdom.common.dto.BaseResponseBody;
import org.lightnsalt.hikingdom.common.dto.ErrorResponseBody;
import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.domain.member.dto.request.MemberChangePasswordReq;
import org.lightnsalt.hikingdom.domain.member.service.MemberManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * 회원 관리와 관련된 API
 * JWT 토큰 인증 필요
 */
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberManagementController {
	private final MemberManagementService memberManagementService;

	@PostMapping("/logout")
	public ResponseEntity<?> logout(@RequestHeader("Authorization") String bearerToken) {
		memberManagementService.logout(bearerToken);

		return new ResponseEntity<>(BaseResponseBody.of("로그아웃에 성공했습니다"), HttpStatus.OK);
	}

	@PutMapping("/password-change")
	public ResponseEntity<?> passwordChange(Authentication authentication,
		@RequestBody @Valid MemberChangePasswordReq memberChangePasswordReq, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(ErrorResponseBody.of(ErrorCode.INVALID_INPUT_VALUE,
				bindingResult.getAllErrors().get(0).getDefaultMessage()),
				HttpStatus.BAD_REQUEST);
		}

		memberManagementService.changePassword(authentication.getName(), memberChangePasswordReq);

		return new ResponseEntity<>(BaseResponseBody.of("비밀번호 변경에 성공했습니다"), HttpStatus.OK);
	}
}
