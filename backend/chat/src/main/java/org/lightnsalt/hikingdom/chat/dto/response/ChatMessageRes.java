package org.lightnsalt.hikingdom.chat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatMessageRes {
	private static final String TYPE = "MESSAGE";
	private ChatRes chat;
}
