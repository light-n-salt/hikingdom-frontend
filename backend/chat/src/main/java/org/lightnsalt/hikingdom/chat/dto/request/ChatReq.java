package org.lightnsalt.hikingdom.chat.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ChatReq {
	@NotNull
	private String type;

	@NotNull
	private Long clubId;

	@NotNull
	private Long memberId;

	@NotEmpty
	private String content;
}
