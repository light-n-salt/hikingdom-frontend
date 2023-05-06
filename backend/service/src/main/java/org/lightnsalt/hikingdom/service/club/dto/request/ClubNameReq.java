package org.lightnsalt.hikingdom.service.club.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class ClubNameReq {
	@NotEmpty(message = "소모임 이름은 필수 입력값입니다.")
	@Pattern(regexp = ".{1,20}", message = "소모임 이름은 1~20자 이내입니다.")
	private String name;
}
