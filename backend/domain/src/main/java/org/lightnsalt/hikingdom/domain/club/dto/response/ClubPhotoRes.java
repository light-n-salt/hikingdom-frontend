package org.lightnsalt.hikingdom.domain.club.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public class ClubPhotoRes {
	private Long photoId;
	private Long memberId;
	private String imgUrl;
	private LocalDateTime createdAt;
}
