package org.lightnsalt.hikingdom.service.club.controller;

import org.lightnsalt.hikingdom.common.dto.BaseResponseBody;
import org.lightnsalt.hikingdom.common.dto.CustomResponseBody;
import org.lightnsalt.hikingdom.common.dto.CustomSlice;
import org.lightnsalt.hikingdom.service.club.dto.response.MeetupAlbumRes;
import org.lightnsalt.hikingdom.service.club.service.ClubPhotoService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/clubs/{clubId}/photos")
@RequiredArgsConstructor
public class ClubPhotoController {
	private final ClubPhotoService clubPhotoService;

	@GetMapping
	public ResponseEntity<CustomResponseBody> meetupAlbumList(Authentication authentication, @PathVariable Long clubId,
		@RequestParam(defaultValue = "") Long photoId, Pageable pageable) {
		CustomSlice<MeetupAlbumRes> result = clubPhotoService.findClubPhotoList(authentication.getName(), clubId,
			photoId, pageable);

		return new ResponseEntity<>(BaseResponseBody.of("소모임 사진 조회에 성공했습니다", result), HttpStatus.OK);
	}
}
