package org.lightnsalt.hikingdom.domain.club.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class ClubAddReq {
	@NotEmpty(message = "소모임 이름은 필수 입력값입니다.")
	@Pattern(regexp = ".{1,20}", message = "소모임 이름은 1~20자 이내입니다.")
	private String name;
	@NotEmpty(message = "소모임 설명은 필수 입력값입니다.")
	@Pattern(regexp = ".{1,512}", message = "소모임 설명은 1~512자 이내입니다.")
	private String description;

	private String dongCode;
}
