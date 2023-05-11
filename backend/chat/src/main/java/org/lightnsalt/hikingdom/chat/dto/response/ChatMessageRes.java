package org.lightnsalt.hikingdom.chat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatMessageRes {
	private String type;
	private ChatRes chat;
}
