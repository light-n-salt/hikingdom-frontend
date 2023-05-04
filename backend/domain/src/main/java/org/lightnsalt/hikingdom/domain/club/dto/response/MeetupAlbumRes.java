package org.lightnsalt.hikingdom.domain.club.dto.response;

import java.time.LocalDateTime;

import org.lightnsalt.hikingdom.entity.club.meetup.MeetupAlbum;

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

	public MeetupAlbumRes(MeetupAlbum meetupAlbum) {
		this.photoId = meetupAlbum.getId();
		this.memberId = meetupAlbum.getMember().getId();
		this.imgUrl = meetupAlbum.getImgUrl();
		this.createdAt = meetupAlbum.getCreatedAt();
	}
}
