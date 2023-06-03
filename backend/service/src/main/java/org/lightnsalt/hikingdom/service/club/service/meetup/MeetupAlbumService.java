package org.lightnsalt.hikingdom.service.club.service.meetup;

import java.util.List;

import org.lightnsalt.hikingdom.common.dto.CustomSlice;
import org.lightnsalt.hikingdom.service.club.dto.response.meetup.MeetupAlbumRes;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface MeetupAlbumService {
	List<String> saveMeetupAlbum(String email, Long clubId, Long meetupId, List<MultipartFile> photos);

	CustomSlice<MeetupAlbumRes> findMeetupAlbumList(String email, Long clubId, Long meetupId, Long photoId,
		Pageable pageable);

	void removeMeetupAlbum(String email, Long clubId, Long meetupId, Long photoId);
}
