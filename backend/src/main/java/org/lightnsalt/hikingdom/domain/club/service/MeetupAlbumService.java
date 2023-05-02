package org.lightnsalt.hikingdom.domain.club.service;

import java.util.List;

import org.lightnsalt.hikingdom.domain.club.dto.response.MeetupAlbumRes;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.multipart.MultipartFile;

public interface MeetupAlbumService {
	List<String> saveMeetupAlbum(String email, Long clubId, Long meetupId, List<MultipartFile> photos);

	Slice<MeetupAlbumRes> findMeetupAlbumList(Long clubId, Long meetupId, Long photoId, Pageable pageable);
}
