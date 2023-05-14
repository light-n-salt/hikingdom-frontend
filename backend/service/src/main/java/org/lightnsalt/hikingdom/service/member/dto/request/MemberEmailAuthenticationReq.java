package org.lightnsalt.hikingdom.service.member.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class MemberEmailAuthenticationReq {
	@NotEmpty(message = "이메일은 필수 입력값입니다.")
	@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
	private String email;

	@NotBlank(message = "인증코드를 입력해주세요.")
	@Pattern(regexp = "^([0-9]{6})?$", message = "올바른 인증코드를 입력해주세요.")
	private String authCode;
}
