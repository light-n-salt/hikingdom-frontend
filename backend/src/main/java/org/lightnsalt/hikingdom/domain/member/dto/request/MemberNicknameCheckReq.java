package org.lightnsalt.hikingdom.domain.member.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class MemberNicknameCheckReq {
	@NotEmpty(message = "닉네임은 필수 입력값입니다.")
	@Pattern(regexp = "[ㄱ-ㅎ가-힣a-zA-Z0-9]{2,16}", message = "닉네임 형식에 맞지 않습니다.")
	private String nickname;
}
