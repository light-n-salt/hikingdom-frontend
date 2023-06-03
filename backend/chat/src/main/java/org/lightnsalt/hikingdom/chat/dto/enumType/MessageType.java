package org.lightnsalt.hikingdom.chat.dto.enumType;

/**
 * 메시지 유형을 나타내기 위한 Enum 타입
 */
public enum MessageType {
	/**
	 * 단일 채팅 메시지
	 * 실시간으로 채팅을 주고받을 때 사용된다.
	 */
	MESSAGE,

	/**
	 * 채팅 메시지 목록
	 * 과거 채팅 목록을 조회할 때 사용된다.
	 */
	MESSAGES,

	/**
	 * 채팅방 회원 목록
	 * 채팅방 회원 목록을 조회하거나 변경 사항을 전달하기 위해 사용된다.
	 */
	MEMBERS
}
