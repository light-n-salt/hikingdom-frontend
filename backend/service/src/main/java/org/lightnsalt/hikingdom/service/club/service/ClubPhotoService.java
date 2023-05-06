package org.lightnsalt.hikingdom.service.club.service;

import org.lightnsalt.hikingdom.common.dto.CustomSlice;
import org.lightnsalt.hikingdom.service.club.dto.response.MeetupAlbumRes;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface ClubPhotoService {

	@Transactional
	CustomSlice<MeetupAlbumRes> findClubPhotoList(String email, Long clubId, Long photoId, Pageable pageable);
}
