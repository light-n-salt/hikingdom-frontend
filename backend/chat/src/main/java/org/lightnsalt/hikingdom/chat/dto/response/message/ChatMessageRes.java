package org.lightnsalt.hikingdom.chat.dto.response.message;

import org.lightnsalt.hikingdom.chat.dto.MessageType;
import org.lightnsalt.hikingdom.chat.dto.response.ChatInfoRes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageRes extends MessageRes {
	private ChatInfoRes chat;

	public ChatMessageRes(ChatInfoRes chatInfoRes) {
		super(MessageType.MESSAGE);
		this.chat = chatInfoRes;
	}
}
