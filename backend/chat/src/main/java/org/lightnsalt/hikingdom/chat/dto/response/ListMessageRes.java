package org.lightnsalt.hikingdom.chat.dto.response;

import java.util.List;

import org.lightnsalt.hikingdom.chat.dto.CustomPage;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ListMessageRes {
	private String type;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<MemberInfoRes> members;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private CustomPage<ChatRes> chats;

	public static ListMessageRes of(final String type, final List<MemberInfoRes> members) {
		return new ListMessageRes(type, members, null);
	}

	public static ListMessageRes of(final String type, final CustomPage<ChatRes> chats) {
		return new ListMessageRes(type, null, chats);
	}

	public static ListMessageRes of(final String type, final List<MemberInfoRes> members,
		final CustomPage<ChatRes> chats) {
		return new ListMessageRes(type, members, chats);
	}
}
