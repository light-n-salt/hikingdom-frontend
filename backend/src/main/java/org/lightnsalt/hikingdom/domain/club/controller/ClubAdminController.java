package org.lightnsalt.hikingdom.domain.club.controller;

import org.lightnsalt.hikingdom.common.dto.BaseResponseBody;
import org.lightnsalt.hikingdom.domain.club.service.ClubAdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 소모임 관리자 관련 API
 * - 소모임 가입 신청 관리 등
 */
@RestController
@Slf4j
@RequestMapping("/api/v1/clubs/{clubId}/admin")
@RequiredArgsConstructor
public class ClubAdminController {
	private final ClubAdminService clubAdminService;

	@PostMapping("/requests/{memberId}")
	public ResponseEntity<?> clubJoinRequestAccept(Authentication authentication, @PathVariable Long clubId,
		@PathVariable Long memberId) {
		clubAdminService.acceptClubJoinRequest(authentication.getName(), clubId, memberId);

		return new ResponseEntity<>(BaseResponseBody.of("소모임 가입 신청 수락에 성공했습니다"), HttpStatus.CREATED);
	}

	@DeleteMapping("/requests/{memberId}")
	public ResponseEntity<?> clubJoinRequestReject(Authentication authentication, @PathVariable Long clubId,
		@PathVariable Long memberId) {
		clubAdminService.rejectClubJoinRequest(authentication.getName(), clubId, memberId);

		return new ResponseEntity<>(BaseResponseBody.of("소모임 가입 신청 거절에 성공했습니다"), HttpStatus.OK);
	}
}
