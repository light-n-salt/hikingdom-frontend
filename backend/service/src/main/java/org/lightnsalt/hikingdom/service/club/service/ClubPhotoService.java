package org.lightnsalt.hikingdom.service.club.service;

import org.lightnsalt.hikingdom.common.dto.CustomSlice;
import org.lightnsalt.hikingdom.service.club.dto.response.MeetupAlbumRes;
import org.springframework.data.domain.Pageable;

public interface ClubPhotoService {
	CustomSlice<MeetupAlbumRes> findClubPhotoList(String email, Long clubId, Long photoId, Pageable pageable);

	void removeClubPhoto(String email, Long clubId, Long photoId);
}
