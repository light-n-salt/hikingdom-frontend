package org.lightnsalt.hikingdom.chat.controller;

import org.lightnsalt.hikingdom.chat.dto.response.ListMessageRes;
import org.lightnsalt.hikingdom.chat.service.ChatService;
import org.lightnsalt.hikingdom.common.dto.BaseResponseBody;
import org.lightnsalt.hikingdom.common.dto.CustomResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {
	private final ChatService chatService;

	@GetMapping("/clubs/{clubId}/enter")
	public ResponseEntity<CustomResponseBody> enterChat(@PathVariable Long clubId) {
		log.info("clubId {} ", clubId);
		ListMessageRes message = chatService.findInitialChatInfo(clubId);
		log.info("enter chat : {} ", message);
		return new ResponseEntity<>(BaseResponseBody.of("소모임 채팅방 입장에 성공했습니다", message), HttpStatus.OK);
	}
}
