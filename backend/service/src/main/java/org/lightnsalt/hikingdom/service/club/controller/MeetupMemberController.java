package org.lightnsalt.hikingdom.service.club.controller;

import java.util.List;

import org.lightnsalt.hikingdom.common.dto.BaseResponseBody;
import org.lightnsalt.hikingdom.service.club.dto.response.MemberListRes;
import org.lightnsalt.hikingdom.service.club.service.MeetupMemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1/clubs/{clubId}/meetups/{meetupId}")
@RequiredArgsConstructor
public class MeetupMemberController {

	private final MeetupMemberService meetupMemberService;

	@PostMapping("/join")
	public ResponseEntity<?> meetupJoinSave(Authentication authentication, @PathVariable Long clubId,
		@PathVariable Long meetupId) {
		meetupMemberService.addJoinMeetup(authentication.getName(), clubId, meetupId);
		return new ResponseEntity<>(BaseResponseBody.of("일정 참여에 성공했습니다"), HttpStatus.CREATED);
	}

	@DeleteMapping("/join")
	public ResponseEntity<?> meetupJoinRemove(Authentication authentication, @PathVariable Long clubId,
		@PathVariable Long meetupId) {
		meetupMemberService.removeJoinMeetup(authentication.getName(), clubId, meetupId);
		return new ResponseEntity<>(BaseResponseBody.of("일정 참여 취소에 성공했습니다"), HttpStatus.OK);
	}

	@GetMapping("/members")
	public ResponseEntity<?> meetupMemberList(@PathVariable Long clubId, @PathVariable Long meetupId) {

		List<MemberListRes> result = meetupMemberService.findMeetupMember(clubId, meetupId);
		return new ResponseEntity<>(BaseResponseBody.of("일정 멤버 조회에 성공했습니다", result), HttpStatus.OK);
	}
}
