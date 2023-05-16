package org.lightnsalt.hikingdom.service.club.dto.response;

import org.lightnsalt.hikingdom.domain.entity.club.meetup.MeetupAlbum;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhotoInfoRes {
	private Long photoId;
	private String imgUrl;
	@JsonProperty("isOwner")
	private boolean isOwner;

	public PhotoInfoRes(MeetupAlbum meetupAlbum, boolean isOwner) {
		this.photoId = meetupAlbum.getId();
		this.imgUrl = meetupAlbum.getImgUrl();
		this.isOwner = isOwner;
	}
}
