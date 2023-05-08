package org.lightnsalt.hikingdom.service.club.dto.response;

import java.time.format.DateTimeFormatter;

import org.lightnsalt.hikingdom.domain.entity.club.meetup.MeetupAlbum;

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
	private String createdAt;

	public MeetupAlbumRes(MeetupAlbum meetupAlbum) {
		this.photoId = meetupAlbum.getId();
		this.memberId = meetupAlbum.getMember().getId();
		this.imgUrl = meetupAlbum.getImgUrl();
		this.createdAt = meetupAlbum.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
}
