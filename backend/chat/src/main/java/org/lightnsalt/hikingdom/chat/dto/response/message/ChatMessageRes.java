package org.lightnsalt.hikingdom.chat.dto.response.message;

import org.lightnsalt.hikingdom.chat.dto.enumType.MessageType;
import org.lightnsalt.hikingdom.chat.dto.response.ChatInfoRes;

import lombok.Getter;
import lombok.Setter;

/**
 * 단일 채팅 메시지에 대한 메시지 정보를 나타내는 클래스
 * MessageRes 클래스의 하위 클래스이다.
 */
@Getter
@Setter
public class ChatMessageRes extends MessageRes {
	/**
	 * 채팅 메시지
	 */
	private ChatInfoRes chat;

	/**
	 * 주어진 채팅 메시지로 ChatMessageRes 객체를 생성한다.
	 *
	 * @param chatInfoRes 단일 채팅 메시지
	 */
	public ChatMessageRes(ChatInfoRes chatInfoRes) {
		super(MessageType.MESSAGE);
		this.chat = chatInfoRes;
	}
}
