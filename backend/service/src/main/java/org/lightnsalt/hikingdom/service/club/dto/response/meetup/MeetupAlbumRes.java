package org.lightnsalt.hikingdom.service.club.dto.response.meetup;

import java.time.format.DateTimeFormatter;

import org.lightnsalt.hikingdom.domain.entity.club.meetup.MeetupAlbum;
import org.lightnsalt.hikingdom.domain.entity.member.Member;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetupAlbumRes {
	private Long photoId;
	private String imgUrl;
	private String createdAt;
	private String profileUrl;
	private String nickname;
	@JsonProperty("isOwner")
	private boolean isOwner;

	public MeetupAlbumRes(MeetupAlbum meetupAlbum, Member member) {
		this.photoId = meetupAlbum.getId();
		this.isOwner = meetupAlbum.getMember().getId().equals(member.getId());
		this.imgUrl = meetupAlbum.getImgUrl();
		this.profileUrl = member.getProfileUrl();
		this.nickname = member.getNickname();
		this.createdAt = meetupAlbum.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
}
