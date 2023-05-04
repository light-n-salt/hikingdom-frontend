package org.lightnsalt.hikingdom.service.report.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberReportReq {
	@NotEmpty(message = "신고 타입은 필수 입력값입니다.")
	@Pattern(regexp = "(ALBUM|REVIEW|CHAT)", message = "신고 타입은 (ALBUM, REVIEW, CHAT)입니다.")
	private String type;
	@NotNull(message = "컨텐츠 아이디는 필수 입력값입니다.")
	private Long contentId;
}
