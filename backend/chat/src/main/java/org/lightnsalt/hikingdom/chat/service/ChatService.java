package org.lightnsalt.hikingdom.chat.service;

import java.util.List;

import org.lightnsalt.hikingdom.chat.dto.request.ChatReq;
import org.lightnsalt.hikingdom.chat.dto.response.MemberRes;
import org.lightnsalt.hikingdom.chat.dto.response.message.MessageRes;

public interface ChatService {
	MessageRes saveMessage(ChatReq chatReq);

	MessageRes findPrevChatInfo(Long clubId, String chatId, Integer size);

	MessageRes findClubMemberInfo(Long clubId);

	MessageRes convertMemberResToMessageRes(List<MemberRes> memberResList);
}
