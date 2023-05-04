package org.lightnsalt.hikingdom.service.member.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.lightnsalt.hikingdom.common.dto.BaseResponseBody;
import org.lightnsalt.hikingdom.common.dto.ErrorResponseBody;
import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.service.member.dto.request.MemberEmailAuthenticationReq;
import org.lightnsalt.hikingdom.service.member.dto.request.MemberEmailReq;
import org.lightnsalt.hikingdom.service.member.dto.request.MemberSignUpReq;
import org.lightnsalt.hikingdom.service.member.service.MemberEmailService;
import org.lightnsalt.hikingdom.service.member.service.MemberSignUpService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@Validated
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

	@GetMapping("/nickname-check/{nickname}")
	public ResponseEntity<?> nicknameCheck(@Valid @NotEmpty(message = "닉네임은 필수 입력값입니다.")
	@Pattern(regexp = "[ㄱ-ㅎ가-힣a-zA-Z0-9]{2,16}", message = "닉네임 형식에 맞지 않습니다.") @PathVariable String nickname) {
		memberSignUpService.checkDuplicateNickname(nickname);

		return new ResponseEntity<>(BaseResponseBody.of("사용할 수 있는 닉네임입니다"), HttpStatus.OK);
	}

	@PostMapping("/email-valid")
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

	@DeleteMapping("/email-valid")
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
