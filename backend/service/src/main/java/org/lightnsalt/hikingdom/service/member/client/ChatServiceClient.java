package org.lightnsalt.hikingdom.service.member.client;

import java.util.List;

import org.lightnsalt.hikingdom.service.member.dto.response.MemberInfoRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 채팅 서버와 통신하기 위한 Feign Client Interface
 */
@FeignClient(name = "chatService", url = "${api.chatService.url}")
public interface ChatServiceClient {

	/**
	 * 회원 정보 변경 발생 시, 채팅 서버에 변경 사항을 전송한다.
	 * 
	 * @param clubId 소모임 ID
	 * @param members 소모임 회원 정보 목록
	 */
	@PostMapping("/chat/clubs/{clubId}/member-update")
	void sendMemberUpdate(@PathVariable("clubId") Long clubId, List<MemberInfoRes> members);
}