package org.lightnsalt.hikingdom.chat.dto.response.message;

import org.lightnsalt.hikingdom.chat.dto.MessageType;
import org.lightnsalt.hikingdom.chat.entity.Chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageRes extends MessageRes {
	private Chat chat;

	public ChatMessageRes(Chat chat) {
		super(MessageType.MESSAGE);
		this.chat = chat;
	}
}
