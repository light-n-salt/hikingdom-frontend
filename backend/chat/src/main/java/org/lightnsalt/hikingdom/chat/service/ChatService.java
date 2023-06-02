package org.lightnsalt.hikingdom.chat.service;

import java.util.List;

import org.lightnsalt.hikingdom.chat.dto.request.ChatReq;
import org.lightnsalt.hikingdom.chat.dto.response.MemberInfoRes;
import org.lightnsalt.hikingdom.chat.dto.response.message.MessageRes;

public interface ChatService {
	MessageRes addChat(ChatReq chatReq);

	MessageRes findPrevChat(Long clubId, String chatId, Integer size);

	MessageRes findMember(Long clubId);

	MessageRes convertMemberResToMessageRes(List<MemberInfoRes> memberInfoResList);
}
