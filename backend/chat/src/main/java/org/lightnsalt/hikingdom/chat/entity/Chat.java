package org.lightnsalt.hikingdom.chat.entity;

import java.time.LocalDateTime;

import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "chat")
public class Chat {
	@Id
	private String id;

	@Field(name = "club_id")
	private Long clubId;

	@Field(name = "member_id")
	private Long memberId;

	private String content;

	@CreatedDate
	@Field(name = "send_at")
	private LocalDateTime sendAt;

	@Builder
	public Chat(Long clubId, Long memberId, String content, LocalDateTime sendAt) {
		this.clubId = clubId;
		this.memberId = memberId;
		this.content = content;
		this.sendAt = sendAt;
	}
}
