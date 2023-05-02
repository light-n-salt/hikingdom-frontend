package org.lightnsalt.hikingdom.domain.club.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetupAlbumRes {
	private Long photoId;
	private Long memberId;
	private String imgUrl;
	private LocalDateTime createdAt;
}
