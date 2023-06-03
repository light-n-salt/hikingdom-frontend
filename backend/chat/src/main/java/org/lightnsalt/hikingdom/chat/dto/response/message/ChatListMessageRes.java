package org.lightnsalt.hikingdom.chat.dto.response.message;

import org.lightnsalt.hikingdom.chat.dto.CustomPage;
import org.lightnsalt.hikingdom.chat.dto.enumType.MessageType;
import org.lightnsalt.hikingdom.chat.dto.response.ChatInfoRes;

import lombok.Getter;
import lombok.Setter;

/**
 * 채팅 메시지 목록에 대한 메시지 정보를 나타내는 클래스
 * MessageRes 클래스의 하위 클래스이다.
 */
@Getter
@Setter
public class ChatListMessageRes extends MessageRes {
	/**
	 * 채팅 메시지 목록
	 */
	private CustomPage<ChatInfoRes> chats;

	/**
	 * 주어진 채팅 메시지 목록으로 ChatListMessageRes 객체를 생성한다.
	 *
	 * @param chats 채팅 메시지 목록
	 */
	public ChatListMessageRes(CustomPage<ChatInfoRes> chats) {
		super(MessageType.MESSAGES);
		this.chats = chats;
	}
}
