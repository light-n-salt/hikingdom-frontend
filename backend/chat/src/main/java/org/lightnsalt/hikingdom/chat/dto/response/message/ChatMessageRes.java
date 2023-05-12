package org.lightnsalt.hikingdom.chat.dto.response.message;

import org.lightnsalt.hikingdom.chat.dto.MessageType;
import org.lightnsalt.hikingdom.chat.dto.response.ChatRes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageRes extends MessageRes {
	private ChatRes chatRes;

	public ChatMessageRes(ChatRes chatRes) {
		super(MessageType.MESSAGE);
		this.chatRes = chatRes;
	}
}
