package org.lightnsalt.hikingdom.domain.member.controller;

import javax.validation.Valid;

import org.lightnsalt.hikingdom.common.dto.BaseResponseBody;
import org.lightnsalt.hikingdom.common.dto.ErrorResponseBody;
import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.domain.member.dto.request.MemberNicknameReq;
import org.lightnsalt.hikingdom.domain.member.dto.request.MemberSignUpReq;
import org.lightnsalt.hikingdom.domain.member.service.MemberSignUpService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * 회원 가입을 위해 필요한 API
 * JWT 토큰 인증이 없어도 접근 가능
 */
@RestController
@RequestMapping("/api/v1/members/auth")
@RequiredArgsConstructor
public class MemberSignUpController {
	private final MemberSignUpService memberSignUpService;

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody @Valid MemberSignUpReq memberSignUpReq, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(ErrorResponseBody.of(ErrorCode.INVALID_INPUT_VALUE,
				bindingResult.getAllErrors().get(0).getDefaultMessage()),
				HttpStatus.BAD_REQUEST);
		}

		memberSignUpService.signUp(memberSignUpReq);

		return new ResponseEntity<>(BaseResponseBody.of("회원 가입에 성공했습니다"), HttpStatus.CREATED);
	}

	@GetMapping("/nickname-check")
	public ResponseEntity<?> nicknameCheck(@RequestBody @Valid MemberNicknameReq memberNicknameReq,
		BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(ErrorResponseBody.of(ErrorCode.INVALID_INPUT_VALUE,
				bindingResult.getAllErrors().get(0).getDefaultMessage()),
				HttpStatus.BAD_REQUEST);
		}

		memberSignUpService.checkDuplicateNickname(memberNicknameReq.getNickname());

		return new ResponseEntity<>(BaseResponseBody.of("사용할 수 있는 닉네임입니다"), HttpStatus.OK);
	}
}
