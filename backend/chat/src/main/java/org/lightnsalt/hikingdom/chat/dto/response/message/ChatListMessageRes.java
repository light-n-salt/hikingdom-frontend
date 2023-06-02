package org.lightnsalt.hikingdom.chat.dto.response.message;

import org.lightnsalt.hikingdom.chat.dto.CustomPage;
import org.lightnsalt.hikingdom.chat.dto.MessageType;
import org.lightnsalt.hikingdom.chat.dto.response.ChatInfoRes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatListMessageRes extends MessageRes {
	private CustomPage<ChatInfoRes> chats;

	public ChatListMessageRes(CustomPage<ChatInfoRes> chats) {
		super(MessageType.MESSAGES);
		this.chats = chats;
	}
}
