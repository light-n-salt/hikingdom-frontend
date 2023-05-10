package org.lightnsalt.hikingdom.chat.service;

import org.lightnsalt.hikingdom.chat.dto.request.ChatReq;
import org.lightnsalt.hikingdom.chat.dto.response.ChatRes;
import org.lightnsalt.hikingdom.chat.dto.response.ListMessageRes;

public interface ChatService {
	ChatRes saveMessage(ChatReq chatReq);

	ListMessageRes findInitialChatInfo(Long clubId);

	ListMessageRes findPrevChatInfo(Long clubId, String chatId);

	ListMessageRes findClubMemberInfo(Long clubId);
}
