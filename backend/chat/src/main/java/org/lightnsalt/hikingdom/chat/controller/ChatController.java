package org.lightnsalt.hikingdom.chat.controller;

import org.lightnsalt.hikingdom.chat.dto.response.MemberRes;
import org.lightnsalt.hikingdom.chat.dto.response.message.MessageRes;
import org.lightnsalt.hikingdom.chat.service.ChatService;
import org.lightnsalt.hikingdom.common.dto.BaseResponseBody;
import org.lightnsalt.hikingdom.common.dto.CustomResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {
	private final SimpMessagingTemplate template;
	private final ChatService chatService;

	@GetMapping("/clubs/{clubId}/members")
	public ResponseEntity<CustomResponseBody> memberList(@PathVariable Long clubId) {
		log.info("clubId {} ", clubId);
		MessageRes message = chatService.findClubMemberInfo(clubId);
		log.info("get members : {} ", message);
		return new ResponseEntity<>(BaseResponseBody.of("소모임 채팅방 회원 조회에 성공했습니다", message), HttpStatus.OK);
	}

	@GetMapping("/clubs/{clubId}/chats")
	public ResponseEntity<CustomResponseBody> prevChats(@PathVariable Long clubId,
		@RequestParam(required = false) String chatId,
		@RequestParam(required = false, defaultValue = "20") Integer size) {
		log.info("clubId {} ", clubId);
		MessageRes message = chatService.findPrevChatInfo(clubId, chatId, size);
		log.info("prev chats  : {} ", message);
		return new ResponseEntity<>(BaseResponseBody.of("소모임 채팅방 이전 대화 조회에 성공했습니다", message), HttpStatus.OK);
	}

	@PostMapping("/clubs/{clubId}/member-update")
	public ResponseEntity<CustomResponseBody> memberUpdate(@PathVariable Long clubId,
		@RequestBody List<MemberRes> members) {
		log.info("clubId {} ", clubId);
		log.info("members {} ", members);

		MessageRes message = chatService.convertMemberResToMessageRes(members);
		log.info("update member: {} ", message);
		template.convertAndSend("/sub/clubs/" + clubId, message);
		return new ResponseEntity<>(BaseResponseBody.of("소모임 멤버 업데이트에 성공했습니다"), HttpStatus.OK);
	}
}
