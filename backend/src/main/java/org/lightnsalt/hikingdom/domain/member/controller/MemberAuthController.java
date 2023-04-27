package org.lightnsalt.hikingdom.domain.member.controller;

import javax.validation.Valid;

import org.lightnsalt.hikingdom.common.dto.BaseResponseBody;
import org.lightnsalt.hikingdom.common.dto.ErrorResponseBody;
import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.domain.member.dto.request.MemberLoginReq;
import org.lightnsalt.hikingdom.domain.member.dto.request.MemberRefreshTokenReq;
import org.lightnsalt.hikingdom.domain.member.dto.response.MemberTokenRes;
import org.lightnsalt.hikingdom.domain.member.service.MemberAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * 회원 인증을 받기 위해 필요한 API
 * JWT 토큰 인증이 없어도 접근 가능
 */
@RestController
@RequestMapping("/api/v1/members/auth")
@RequiredArgsConstructor
public class MemberAuthController {
	private final MemberAuthService memberAuthService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid MemberLoginReq memberLoginReq, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(ErrorResponseBody.of(ErrorCode.INVALID_INPUT_VALUE,
				bindingResult.getAllErrors().get(0).getDefaultMessage()),
				HttpStatus.BAD_REQUEST);
		}

		MemberTokenRes memberTokenRes = memberAuthService.login(memberLoginReq);

		return new ResponseEntity<>(BaseResponseBody.of("로그인에 성공했습니다", memberTokenRes), HttpStatus.OK);
	}

	@PostMapping("/refresh-token")
	public ResponseEntity<?> tokenRefresh(@RequestBody MemberRefreshTokenReq memberRefreshTokenReq) {
		MemberTokenRes memberTokenRes = memberAuthService.refreshToken(memberRefreshTokenReq.getRefreshToken());

		return new ResponseEntity<>(BaseResponseBody.of("토큰 재발급에 성공했습니다", memberTokenRes), HttpStatus.OK);
	}
}
