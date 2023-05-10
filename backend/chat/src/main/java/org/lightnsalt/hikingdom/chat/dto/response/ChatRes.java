package org.lightnsalt.hikingdom.chat.dto.response;

import lombok.Data;

@Data
public class ChatRes {
	private String chatId;
	private Long memberId;
	private String content;
	private String sendAt;
}
