package org.lightnsalt.hikingdom.chat.dto.response;

import java.time.format.DateTimeFormatter;

import org.lightnsalt.hikingdom.chat.entity.Chat;

import lombok.Data;

/**
 * 채팅 메시지 정보를 나타내는 클래스
 */
@Data
public class ChatInfoRes {
	/**
	 * 채팅 메시지 ID
	 */
	private String chatId;

	/**
	 * 채팅 메시지를 보낸 회원 ID
	 */
	private Long memberId;

	/**
	 * 채팅 메시지 내용
	 */
	private String content;

	/**
	 * 채팅 전송 시간
	 */
	private String sendAt;

	/**
	 * Chat 엔티티로 ChatInfoRes DTO를 생성한다.
	 *
	 * @param chat ChatInfoRes DTO로 변환하고자 하는 Chat 객체
	 */
	public ChatInfoRes(Chat chat) {
		this.chatId = chat.getId();
		this.memberId = chat.getMemberId();
		this.content = chat.getContent();
		this.sendAt = chat.getSendAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	}
}
