package org.lightnsalt.hikingdom.chat.dto.response.message;

import java.util.Map;

import org.lightnsalt.hikingdom.chat.dto.enumType.MessageType;
import org.lightnsalt.hikingdom.chat.dto.response.MemberInfoRes;

import lombok.Getter;
import lombok.Setter;

/**
 * 채팅방 회원 목록에 대한 메시지 정보를 나타내는 클래스
 * MessageRes 클래스의 하위 클래스이다.
 */
@Getter
@Setter
public class MemberListMessageRes extends MessageRes {
	/**
	 * 회원 ID를 key로 하는 채팅방 회원 목록
	 */
	private Map<Long, MemberInfoRes> members;

	/**
	 * 주어진 회원 목록으로 MemberListMessageRes 객체를 생성한다.
	 *
	 * @param members 채팅방 회원 목록
	 */
	public MemberListMessageRes(Map<Long, MemberInfoRes> members) {
		super(MessageType.MEMBERS);
		this.members = members;
	}
}
