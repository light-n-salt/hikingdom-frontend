package org.lightnsalt.hikingdom.chat.controller;

import org.lightnsalt.hikingdom.chat.dto.response.MemberInfoRes;
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

/**
 * 채팅방의 정보 관련 요청을 처리하는 Controller 클래스
 */
@RestController
@Slf4j
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatInfoController {
	private final SimpMessagingTemplate template;
	private final ChatService chatService;

	/**
	 * 소모임 채팅방에 참여하고 있는 회원 목록을 조회한다.
	 *
	 * @param clubId 소모임 ID
	 * @return 채팅방 회원 목록 (소모임 회원 목록과 같음)
	 */
	@GetMapping("/clubs/{clubId}/members")
	public ResponseEntity<CustomResponseBody> memberList(@PathVariable Long clubId) {
		log.info("clubId {} ", clubId);
		MessageRes message = chatService.findMember(clubId);
		log.info("get members : {} ", message);
		return new ResponseEntity<>(BaseResponseBody.of("소모임 채팅방 회원 조회에 성공했습니다", message), HttpStatus.OK);
	}

	/**
	 * 소모임 채팅방의 과거 채팅 기록을 조회한다.
	 *
	 * @param clubId 소모임 ID
	 * @param chatId 조회 기준이 되는 채팅 메시지 ID (선택)
	 * @param size 조회할 채팅 메시지 개수 (기본값: 20)
	 * @return 과거 채팅 메시지 목록
	 */
	@GetMapping("/clubs/{clubId}/chats")
	public ResponseEntity<CustomResponseBody> prevChatList(@PathVariable Long clubId,
		@RequestParam(required = false) String chatId,
		@RequestParam(required = false, defaultValue = "20") Integer size) {
		log.info("clubId {} ", clubId);
		MessageRes message = chatService.findPrevChat(clubId, chatId, size);
		log.info("prev chats  : {} ", message);
		return new ResponseEntity<>(BaseResponseBody.of("소모임 채팅방 과거 메시지 조회에 성공했습니다", message), HttpStatus.OK);
	}

	/**
	 * 소모임 회원 정보 관련 업데이트 발생 시, 변경 사항을 채팅방에 전달한다.
	 *
	 * @param clubId 소모임 ID
	 * @param members 변경된 소모임 회원 정보
	 * @return 회원 정보 전달 성공 여부에 대한 응답
	 */
	@PostMapping("/clubs/{clubId}/member-update")
	public ResponseEntity<CustomResponseBody> memberUpdate(@PathVariable Long clubId,
		@RequestBody List<MemberInfoRes> members) {
		log.info("clubId {} ", clubId);
		log.info("members {} ", members);

		MessageRes message = chatService.convertMemberResToMessageRes(members);
		log.info("update member: {} ", message);
		template.convertAndSend("/sub/clubs/" + clubId, message);
		return new ResponseEntity<>(BaseResponseBody.of("소모임 멤버 업데이트에 성공했습니다"), HttpStatus.OK);
	}
}
