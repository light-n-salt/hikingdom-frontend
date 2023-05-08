package org.lightnsalt.hikingdom.service.club.dto.response;

import java.time.format.DateTimeFormatter;

import org.lightnsalt.hikingdom.domain.entity.club.meetup.MeetupAlbum;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetupAlbumRes {
	private Long photoId;
	@JsonProperty("isOwner")
	private boolean isOwner;
	private String imgUrl;
	private String createdAt;

	public MeetupAlbumRes(MeetupAlbum meetupAlbum, boolean isOwner) {
		this.photoId = meetupAlbum.getId();
		this.isOwner = isOwner;
		this.imgUrl = meetupAlbum.getImgUrl();
		this.createdAt = meetupAlbum.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
}
