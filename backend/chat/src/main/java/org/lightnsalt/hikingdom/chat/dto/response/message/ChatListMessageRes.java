package org.lightnsalt.hikingdom.chat.dto.response.message;

import org.lightnsalt.hikingdom.chat.dto.CustomPage;
import org.lightnsalt.hikingdom.chat.dto.MessageType;
import org.lightnsalt.hikingdom.chat.dto.response.ChatRes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatListMessageRes extends MessageRes {
	private CustomPage<ChatRes> chats;

	public ChatListMessageRes(CustomPage<ChatRes> chats) {
		super(MessageType.MESSAGES);
		this.chats = chats;
	}
}
