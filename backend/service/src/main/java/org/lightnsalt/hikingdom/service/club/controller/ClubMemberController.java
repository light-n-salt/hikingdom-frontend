package org.lightnsalt.hikingdom.service.club.controller;

import java.util.List;
import java.util.Map;

import org.lightnsalt.hikingdom.common.dto.BaseResponseBody;
import org.lightnsalt.hikingdom.service.club.dto.response.MemberListRes;
import org.lightnsalt.hikingdom.service.club.service.ClubMemberService;
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

/**
 * 소모임 회원 관련 API
 * - 소모임 가입, 탈퇴, 신고 등
 */
@RestController
@Slf4j
@RequestMapping("/api/v1/clubs/{clubId}")
@RequiredArgsConstructor
public class ClubMemberController {
	private final ClubMemberService clubMemberService;

	@PostMapping("/join-request")
	public ResponseEntity<?> clubJoinRequestAdd(Authentication authentication, @PathVariable Long clubId) {
		clubMemberService.sendClubJoinRequest(authentication.getName(), clubId);

		return new ResponseEntity<>(BaseResponseBody.of("소모임 가입 신청에 성공했습니다"), HttpStatus.CREATED);
	}

	@DeleteMapping("/join-request")
	public ResponseEntity<?> clubJoinRequestRetract(Authentication authentication, @PathVariable Long clubId) {
		clubMemberService.retractClubJoinRequest(authentication.getName(), clubId);

		return new ResponseEntity<>(BaseResponseBody.of("소모임 가입 신청 취소에 성공했습니다"), HttpStatus.OK);
	}

	@GetMapping("/members")
	public ResponseEntity<?> clubMemberList(Authentication authentication, @PathVariable Long clubId) {
		Map<String, List<MemberListRes>> result = clubMemberService.findClubMember(authentication.getName(), clubId);
		return new ResponseEntity<>(BaseResponseBody.of("소모임 멤버 조회에 성공했습니다", result), HttpStatus.OK);
	}

	@DeleteMapping("/members")
	public ResponseEntity<?> clubMemberWithdraw(Authentication authentication, @PathVariable Long clubId) {
		clubMemberService.withdrawClubMember(authentication.getName(), clubId);
		return new ResponseEntity<>(BaseResponseBody.of("소모임 탈퇴에 성공했습니다"), HttpStatus.OK);
	}
}
