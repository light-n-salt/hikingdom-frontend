package org.lightnsalt.hikingdom.chat.dto.response;

import java.time.format.DateTimeFormatter;

import org.lightnsalt.hikingdom.chat.entity.Chat;

import lombok.Data;

@Data
public class ChatRes {
	private String chatId;
	private Long memberId;
	private String content;
	private String sendAt;

	public ChatRes(Chat chat) {
		this.chatId = chat.getId();
		this.memberId = chat.getMemberId();
		this.content = chat.getContent();
		this.sendAt = chat.getSendAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
}
