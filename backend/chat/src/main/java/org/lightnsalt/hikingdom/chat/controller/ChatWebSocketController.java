package org.lightnsalt.hikingdom.chat.controller;

import org.lightnsalt.hikingdom.chat.dto.request.ChatReq;
import org.lightnsalt.hikingdom.chat.dto.response.message.MessageRes;
import org.lightnsalt.hikingdom.chat.service.ChatService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 웹소켓을 사용한 채팅 관련 요청을 처리하는 Controller 클래스
 */
@Controller
@Slf4j
@RequiredArgsConstructor
public class ChatWebSocketController {
	private final SimpMessagingTemplate template;
	private final ChatService chatService;

	/**
	 * 채팅 메시지를 저장하고, 해당 소모임 채팅방 회원에게 채팅 메시지를 전달한다.
	 *
	 * @param clubId 소모임 ID
	 * @param chatReq 저장할 채팅 메시지
	 */
	@MessageMapping("/clubs/{clubId}")
	public void chatSave(@DestinationVariable Long clubId, ChatReq chatReq) {
		log.info("clubId {} ", clubId);
		log.info("chatReq {} ", chatReq);
		MessageRes message = chatService.addChat(chatReq);
		log.info("send chat : {} ", message);
		template.convertAndSend("/sub/clubs/" + clubId, message);
	}
}
