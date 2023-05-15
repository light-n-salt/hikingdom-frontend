package org.lightnsalt.hikingdom.service.club.dto.request;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class MeetupReviewReq {
	@NotEmpty(message = "후기 내용은 필수 입력값입니다.")
	String content;
}
