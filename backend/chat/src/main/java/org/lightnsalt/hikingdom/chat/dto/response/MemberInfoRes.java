package org.lightnsalt.hikingdom.chat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 채팅방 회원 정보를 나타내는 클래스
 * 회원 ID, 닉네임, 프로필 URL 및 등급과 같은 세부 정보를 포함한다.
 */
@Data
@AllArgsConstructor
public class MemberInfoRes {
	/**
	 * 회원 ID
	 */
	private Long memberId;

	/**
	 * 회원 닉네임
	 */
	private String nickname;

	/**
	 * 회원 프로필 URL
	 */
	private String profileUrl;

	/**
	 * 회원 등급(레벨)
	 */
	private Integer level;
}
