package org.lightnsalt.hikingdom.service.club.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lightnsalt.hikingdom.common.dto.BaseResponseBody;
import org.lightnsalt.hikingdom.common.dto.CustomSlice;
import org.lightnsalt.hikingdom.service.club.dto.response.MeetupAlbumRes;
import org.lightnsalt.hikingdom.service.club.service.MeetupAlbumService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1/clubs/{clubId}/meetups/{meetupId}/photos")
@RequiredArgsConstructor
public class MeetupAlbumController {

	/*
	- 컨트롤러 클래스 안에서 메서드 명을 작성 할 때는 아래와 같은 접미사를 붙인다.

		orderList() – 목록 조회 유형의 서비스

		orderDetails() – 단 건 상세 조회 유형의 controller 메서드

		orderSave() – 등록/수정/삭제 가 동시에 일어나는 유형의 controller 메서드

		orderAdd() – 등록만 하는 유형의 controller 메서드

		orderModify() – 수정만 하는 유형의 controller 메서드

		orderRemove() – 삭제만 하는 유형의 controller 메서드
	* */

	private final MeetupAlbumService meetupAlbumService;

	@PostMapping("")
	public ResponseEntity<?> meetupAlbumAdd(@PathVariable Long clubId, @PathVariable Long meetupId,
		@RequestBody List<MultipartFile> photos, Authentication authentication) {

		List<String> list = meetupAlbumService.saveMeetupAlbum(authentication.getName(), clubId, meetupId, photos);

		Map<String, List<String>> result = new HashMap<>();
		result.put("imgUrl", list);
		return new ResponseEntity<>(BaseResponseBody.of("일정 사진이 등록되었습니다", result), HttpStatus.CREATED);
	}

	@GetMapping("")
	public ResponseEntity<?> meetupAlbumList(@PathVariable Long clubId, @PathVariable Long meetupId,
		@RequestParam(defaultValue = "") Long photoId, Pageable pageable) {

		CustomSlice<MeetupAlbumRes> result = meetupAlbumService.findMeetupAlbumList(clubId, meetupId, photoId,
			pageable);
		return new ResponseEntity<>(BaseResponseBody.of("일정 사진 조회에 성공했습니다", result), HttpStatus.OK);
	}

	@DeleteMapping("/{photoId}")
	public ResponseEntity<?> meetupAlbumRemove(@PathVariable Long clubId, @PathVariable Long meetupId,
		@PathVariable Long photoId, Authentication authentication) {

		meetupAlbumService.removeMeetupAlbum(authentication.getName(), clubId, meetupId, photoId);
		return new ResponseEntity<>(BaseResponseBody.of("일정 사진이 삭제되었습니다"), HttpStatus.OK);
	}

}
