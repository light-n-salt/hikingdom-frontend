package org.lightnsalt.hikingdom.chat.controller;

import org.lightnsalt.hikingdom.chat.dto.request.ChatReq;
import org.lightnsalt.hikingdom.chat.dto.response.ChatMessageRes;
import org.lightnsalt.hikingdom.chat.dto.response.ChatRes;
import org.lightnsalt.hikingdom.chat.dto.response.ListMessageRes;
import org.lightnsalt.hikingdom.chat.service.ChatService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ChatController {
	private final SimpMessagingTemplate template;
	private final ChatService chatService;

	@MessageMapping("/clubs/{clubId}/enter")
	public void enterChat(@DestinationVariable Long clubId) {
		ListMessageRes message = chatService.findInitialChatInfo(clubId);
		log.info("enter chat : {} ", message);
		template.convertAndSend("/sub/clubs/" + clubId, message);
	}

	@MessageMapping("/clubs/{clubId}/chats")
	public void messageSave(@DestinationVariable Long clubId, ChatReq chatReq) {
		ChatRes chatRes = chatService.saveMessage(chatReq);
		log.info("enter chat : {} ", chatRes);
		template.convertAndSend("/sub/clubs/" + clubId, new ChatMessageRes(chatRes));
	}
}
