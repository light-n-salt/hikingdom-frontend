package org.lightnsalt.hikingdom.chat.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 회원이 전송한 채팅 메시지를 나타내는 클래스
 */
@Data
public class ChatReq {
	/**
	 * 메시지 유형
	 * 단일 채팅 메시지의 경우, MESSAGE 유형이다. (MessageType 참조)
	 */
	@NotNull
	private String type;

	/**
	 * 회원이 소속된 소모임 ID
	 */
	@NotNull
	private Long clubId;

	/**
	 * 채팅을 전송한 회원의 ID
	 */
	@NotNull
	private Long memberId;

	/**
	 * 채팅 메시지 내용
	 */
	@NotEmpty
	private String content;
}
