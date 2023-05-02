package org.lightnsalt.hikingdom.domain.club.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface MeetupAlbumService {
	List<String> saveMeetupAlbum(String email, Long clubId, Long meetupId, List<MultipartFile> photos);
}
