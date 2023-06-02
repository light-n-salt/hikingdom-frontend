package org.lightnsalt.hikingdom.chat.dto.response.message;

import java.util.Map;

import org.lightnsalt.hikingdom.chat.dto.MessageType;
import org.lightnsalt.hikingdom.chat.dto.response.MemberInfoRes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberListMessageRes extends MessageRes {
	private Map<Long, MemberInfoRes> members;

	public MemberListMessageRes(Map<Long, MemberInfoRes> members) {
		super(MessageType.MEMBERS);
		this.members = members;
	}
}
