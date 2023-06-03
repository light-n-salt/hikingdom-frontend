package org.lightnsalt.hikingdom.chat.dto.response.message;

import org.lightnsalt.hikingdom.chat.dto.enumType.MessageType;

import lombok.Data;

/**
 * 메시지 응답을 나타내는 클래스
 * 다른 MessageRes 클래스의 상위 클래스로 사용된다.
 */
@Data
public class MessageRes {
	/**
	 * 메시지 유형
	 */
	private MessageType type;

	/**
	 * 주어진 메시지 유형의 MessageRes 객체를 생성한다.
	 *
	 * @param type 메시지 유형
	 */
	public MessageRes(MessageType type) {
		this.type = type;
	}
}
