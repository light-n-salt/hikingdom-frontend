package org.lightnsalt.hikingdom.domain.club.dto.response;

import org.lightnsalt.hikingdom.entity.club.meetup.MeetupAlbum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhotoInfoRes {
	private Long photoId;
	private String imgUrl;

	public PhotoInfoRes(MeetupAlbum meetupAlbum) {
		this.photoId = meetupAlbum.getId();
		this.imgUrl = meetupAlbum.getImgUrl();
	}
}
