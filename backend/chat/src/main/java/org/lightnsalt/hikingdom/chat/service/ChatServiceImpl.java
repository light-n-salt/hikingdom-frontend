package org.lightnsalt.hikingdom.chat.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.lightnsalt.hikingdom.chat.dto.CustomPage;
import org.lightnsalt.hikingdom.chat.dto.request.ChatReq;
import org.lightnsalt.hikingdom.chat.dto.response.ChatRes;
import org.lightnsalt.hikingdom.chat.dto.response.MemberInfoRes;
import org.lightnsalt.hikingdom.chat.dto.response.ListMessageRes;
import org.lightnsalt.hikingdom.chat.entity.Chat;
import org.lightnsalt.hikingdom.chat.repository.mongo.ChatRepository;
import org.lightnsalt.hikingdom.chat.repository.mysql.ClubMemberRepository;
import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.common.error.GlobalException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
	private final ChatRepository chatRepository;
	private final ClubMemberRepository clubMemberRepository;

	@Override
	public ChatRes saveMessage(ChatReq chatReq) {
		Chat chat = Chat.builder()
			.memberId(chatReq.getMemberId())
			.clubId(chatReq.getClubId())
			.content(chatReq.getContent())
			.sendAt(LocalDateTime.now())
			.build();

		Chat savedChat = chatRepository.save(chat);

		return new ChatRes(savedChat);
	}

	@Override
	public ListMessageRes findInitialChatInfo(Long clubId) {
		// 소모임 채팅방 내 회원 목록
		List<MemberInfoRes> members = clubMemberRepository.findByClubId(clubId);

		// 최근 N개 채팅 반환
		Pageable pageable = PageRequest.of(0, 20, Sort.by("sendAt").descending());
		Page<Chat> chatPage = chatRepository.findByClubIdOrderBySendAtDesc(clubId, pageable);
		System.out.println(chatPage);
		CustomPage<ChatRes> chats = new CustomPage<>(
			chatPage.getContent().stream().map(ChatRes::new).collect(Collectors.toList()),
			chatPage.getNumber(), chatPage.getSize(), chatPage.getTotalElements(), chatPage.hasNext());

		return ListMessageRes.of("ENTER", members, chats);
	}

	@Override
	public ListMessageRes findPrevChatInfo(Long clubId, String chatId) {
		Chat chat = chatRepository.findById(chatId).orElse(null);

		if (chat == null)
			throw new GlobalException(ErrorCode.CHAT_NOT_FOUND);

		Pageable pageable = PageRequest.of(0, 20, Sort.by("sendAt").descending());
		Page<Chat> chatPage = chatRepository.findByClubIdAndSendAtBeforeOrderBySendAtDesc(clubId, chat.getSendAt(),
			pageable);
		CustomPage<ChatRes> chats = new CustomPage<>(
			chatPage.getContent().stream().map(ChatRes::new).collect(Collectors.toList()),
			chatPage.getNumber(), chatPage.getSize(), chatPage.getTotalElements(), !chatPage.hasNext());

		return ListMessageRes.of("MESSAGE_LIST", chats);
	}

	@Override
	public ListMessageRes findClubMemberInfo(Long clubId) {
		List<MemberInfoRes> members = clubMemberRepository.findByClubId(clubId);
		return ListMessageRes.of("MEMBERS", members);
	}
}
