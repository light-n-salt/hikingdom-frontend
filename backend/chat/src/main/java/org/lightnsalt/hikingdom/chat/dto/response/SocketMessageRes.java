package org.lightnsalt.hikingdom.chat.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
public class SocketMessageRes {
	private String status;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<MemberInfoRes> members;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<ChatRes> chats;
}
