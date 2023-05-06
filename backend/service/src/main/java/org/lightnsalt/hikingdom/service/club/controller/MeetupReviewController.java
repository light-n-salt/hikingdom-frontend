package org.lightnsalt.hikingdom.service.club.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.lightnsalt.hikingdom.common.dto.BaseResponseBody;
import org.lightnsalt.hikingdom.common.dto.ErrorResponseBody;
import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.service.club.dto.request.MeetupReviewReq;
import org.lightnsalt.hikingdom.service.club.dto.response.MeetupReviewRes;
import org.lightnsalt.hikingdom.service.club.service.MeetupReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1/clubs/{clubId}/meetups/{meetupId}/reviews")
@RequiredArgsConstructor
public class MeetupReviewController {
	private final MeetupReviewService meetupReviewService;

	@GetMapping
	public ResponseEntity<?> meetupReviewList(Authentication authentication, @PathVariable Long clubId,
		@PathVariable Long meetupId) {
		List<MeetupReviewRes> result = meetupReviewService.findMeetupReviewList(authentication.getName(), clubId,
			meetupId);

		return new ResponseEntity<>(BaseResponseBody.of("일정 후기 조회에 성공했습니다", result), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> meetupReviewAdd(Authentication authentication, @PathVariable Long clubId,
		@PathVariable Long meetupId, @RequestBody @Valid MeetupReviewReq meetupReviewReq, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(ErrorResponseBody.of(ErrorCode.INVALID_INPUT_VALUE,
				bindingResult.getAllErrors().get(0).getDefaultMessage()), HttpStatus.BAD_REQUEST);
		}

		Long reviewId = meetupReviewService.saveMeetupReview(authentication.getName(), clubId, meetupId,
			meetupReviewReq);

		return new ResponseEntity<>(BaseResponseBody.of("일정 후기 생성에 성공했습니다", Map.of("reviewId", reviewId)),
			HttpStatus.CREATED);
	}

	@DeleteMapping("/{reviewId}")
	public ResponseEntity<?> meetupReviewDelete(Authentication authentication, @PathVariable Long clubId,
		@PathVariable Long meetupId, @PathVariable Long reviewId) {
		meetupReviewService.removeMeetupReview(authentication.getName(), clubId, meetupId, reviewId);

		return new ResponseEntity<>(BaseResponseBody.of("일정 후기 삭제에 성공했습니다"), HttpStatus.OK);
	}
}
