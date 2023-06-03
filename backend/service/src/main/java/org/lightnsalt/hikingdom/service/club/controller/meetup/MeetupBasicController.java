package org.lightnsalt.hikingdom.service.club.controller.meetup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.lightnsalt.hikingdom.common.dto.BaseResponseBody;
import org.lightnsalt.hikingdom.common.dto.CustomResponseBody;
import org.lightnsalt.hikingdom.common.dto.ErrorResponseBody;
import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.service.club.dto.request.MeetupAddReq;
import org.lightnsalt.hikingdom.service.club.dto.response.meetup.MeetupDailyRes;
import org.lightnsalt.hikingdom.service.club.dto.response.meetup.MeetupDetailRes;
import org.lightnsalt.hikingdom.service.club.dto.response.meetup.MeetupMonthlyRes;
import org.lightnsalt.hikingdom.service.club.service.meetup.MeetupBasicService;
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
@RequestMapping("/api/v1/clubs/{clubId}/meetups")
@RequiredArgsConstructor
public class MeetupBasicController {

	private final MeetupBasicService meetupBasicService;

	@PostMapping
	public ResponseEntity<CustomResponseBody> meetupAdd(@PathVariable Long clubId, @RequestBody @Valid MeetupAddReq req,
		BindingResult bindingResult, Authentication authentication) {

		if (bindingResult.hasErrors()) {
			log.error("has error in request body format in add mountain info api");
			return new ResponseEntity<>(ErrorResponseBody.of(ErrorCode.INVALID_INPUT_VALUE,
				bindingResult.getAllErrors().get(0).getDefaultMessage()), HttpStatus.BAD_REQUEST);
		}

		Long id = meetupBasicService.addMeetup(authentication.getName(), clubId, req);
		Map<String, Long> result = new HashMap<>();
		result.put("id", id);

		return new ResponseEntity<>(BaseResponseBody.of("일정이 생성되었습니다", result), HttpStatus.CREATED);
	}

	@DeleteMapping("/{meetupId}")
	public ResponseEntity<CustomResponseBody> meetupRemove(Authentication authentication, @PathVariable Long clubId, @PathVariable Long meetupId) {
		meetupBasicService.removeMeetup(authentication.getName(), clubId, meetupId);

		return new ResponseEntity<>(BaseResponseBody.of("일정이 삭제되었습니다"), HttpStatus.OK);
	}

	@GetMapping("/month/{month}")
	public ResponseEntity<CustomResponseBody> meetupMonthlyList(@PathVariable Long clubId, @PathVariable String month) {

		MeetupMonthlyRes result = meetupBasicService.findMeetupMonthly(clubId, month);
		return new ResponseEntity<>(BaseResponseBody.of("모임 일정 조회에 성공했습니다", result), HttpStatus.OK);
	}

	@GetMapping("/date/{date}")
	public ResponseEntity<CustomResponseBody> meetupDailyList(@PathVariable Long clubId, @PathVariable String date) {

		List<MeetupDailyRes> result = meetupBasicService.findMeetupDaily(clubId, date);
		return new ResponseEntity<>(BaseResponseBody.of("일정 조회에 성공했습니다", result), HttpStatus.OK);

	}

	@GetMapping("{meetupId}/detail")
	public ResponseEntity<CustomResponseBody> meetupDetails(Authentication authentication, @PathVariable Long clubId,
		@PathVariable Long meetupId) {

		MeetupDetailRes result = meetupBasicService.findMeetup(authentication.getName(), clubId, meetupId);
		return new ResponseEntity<>(BaseResponseBody.of("일정 상세 조회에 성공했습니다", result), HttpStatus.OK);
	}
}
