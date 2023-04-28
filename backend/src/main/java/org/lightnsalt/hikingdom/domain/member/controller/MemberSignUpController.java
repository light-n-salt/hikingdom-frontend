package org.lightnsalt.hikingdom.domain.member.controller;

import javax.validation.Valid;

import org.lightnsalt.hikingdom.common.dto.BaseResponseBody;
import org.lightnsalt.hikingdom.common.dto.ErrorResponseBody;
import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.domain.member.dto.request.MemberEmailAuthenticationReq;
import org.lightnsalt.hikingdom.domain.member.dto.request.MemberEmailReq;
import org.lightnsalt.hikingdom.domain.member.dto.request.MemberNicknameReq;
import org.lightnsalt.hikingdom.domain.member.dto.request.MemberSignUpReq;
import org.lightnsalt.hikingdom.domain.member.service.MemberEmailService;
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
import lombok.extern.slf4j.Slf4j;

/**
 * 회원 가입을 위해 필요한 API
 * JWT 토큰 인증이 없어도 접근 가능
 */
@RestController
@Slf4j
@RequestMapping("/api/v1/members/auth")
@RequiredArgsConstructor
public class MemberSignUpController {
	private final MemberSignUpService memberSignUpService;
	private final MemberEmailService memberEmailService;

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

		memberSignUpService.checkDuplicateNickname(memberNicknameReq);

		return new ResponseEntity<>(BaseResponseBody.of("사용할 수 있는 닉네임입니다"), HttpStatus.OK);
	}

	@GetMapping("/email-valid")
	public ResponseEntity<?> emailValid(@RequestBody @Valid MemberEmailReq memberEmailReq,
		BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(ErrorResponseBody.of(ErrorCode.INVALID_INPUT_VALUE,
				bindingResult.getAllErrors().get(0).getDefaultMessage()),
				HttpStatus.BAD_REQUEST);
		}

		memberEmailService.sendAuthenticationEmail(memberEmailReq);

		return new ResponseEntity<>(BaseResponseBody.of("인증 이메일을 전송했습니다. 5분 내에 확인해주세요."), HttpStatus.OK);
	}

	@GetMapping("/email-confirm")
	public ResponseEntity<?> emailConfirm(@RequestBody @Valid MemberEmailAuthenticationReq memberEmailAuthenticationReq,
		BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(ErrorResponseBody.of(ErrorCode.INVALID_INPUT_VALUE,
				bindingResult.getAllErrors().get(0).getDefaultMessage()),
				HttpStatus.BAD_REQUEST);
		}

		memberEmailService.confirmAuthenticationEmail(memberEmailAuthenticationReq);

		return new ResponseEntity<>(BaseResponseBody.of("이메일 인증에 성공했습니다"), HttpStatus.OK);
	}
}
