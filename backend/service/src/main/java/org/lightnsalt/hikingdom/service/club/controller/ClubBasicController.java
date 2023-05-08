package org.lightnsalt.hikingdom.service.club.controller;

import java.util.Map;

import javax.validation.Valid;

import org.lightnsalt.hikingdom.common.dto.BaseResponseBody;
import org.lightnsalt.hikingdom.common.dto.CustomResponseBody;
import org.lightnsalt.hikingdom.common.dto.CustomSlice;
import org.lightnsalt.hikingdom.common.dto.ErrorResponseBody;
import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.service.club.dto.request.ClubInfoReq;
import org.lightnsalt.hikingdom.service.club.dto.request.ClubNameReq;
import org.lightnsalt.hikingdom.service.club.dto.response.ClubDetailRes;
import org.lightnsalt.hikingdom.service.club.dto.response.ClubSearchRes;
import org.lightnsalt.hikingdom.service.club.dto.response.ClubSimpleDetailRes;
import org.lightnsalt.hikingdom.service.club.service.ClubBasicService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/clubs")
@RequiredArgsConstructor
public class ClubBasicController {
	private final ClubBasicService clubBasicService;

	@GetMapping
	public ResponseEntity<CustomResponseBody> clubList(@RequestParam(defaultValue = "") String query,
		@RequestParam(defaultValue = "") String word, @RequestParam(defaultValue = "") Long clubId, Pageable pageable) {
		CustomSlice<ClubSearchRes> clubSearchRes = clubBasicService.findClubList(query, word, clubId, pageable);
		return new ResponseEntity<>(BaseResponseBody.of("소모임 검색에 성공했습니다", clubSearchRes), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<CustomResponseBody> clubAdd(Authentication authentication,
		@RequestBody @Valid ClubInfoReq clubInfoReq,
		BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(ErrorResponseBody.of(ErrorCode.INVALID_INPUT_VALUE,
				bindingResult.getAllErrors().get(0).getDefaultMessage()),
				HttpStatus.BAD_REQUEST);
		}

		Long clubId = clubBasicService.addClub(authentication.getName(), clubInfoReq);
		return new ResponseEntity<>(BaseResponseBody.of("소모임 생성에 성공했습니다", Map.of("clubId", clubId)),
			HttpStatus.CREATED);
	}

	@PutMapping("/{clubId}")
	public ResponseEntity<CustomResponseBody> clubModify(Authentication authentication, @PathVariable Long clubId,
		@RequestBody @Valid ClubInfoReq clubInfoReq, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(ErrorResponseBody.of(ErrorCode.INVALID_INPUT_VALUE,
				bindingResult.getAllErrors().get(0).getDefaultMessage()),
				HttpStatus.BAD_REQUEST);
		}

		clubBasicService.modifyClub(authentication.getName(), clubId, clubInfoReq);

		return new ResponseEntity<>(BaseResponseBody.of("소모임 정보 수정에 성공했습니다"), HttpStatus.OK);
	}

	@GetMapping("/{clubId}")
	public ResponseEntity<CustomResponseBody> clubSimpleDetails(@PathVariable Long clubId) {
		ClubSimpleDetailRes clubSimpleDetailRes = clubBasicService.findClubSimpleDetail(clubId);
		return new ResponseEntity<>(BaseResponseBody.of("소모임 정보 조회에 성공했습니다", clubSimpleDetailRes), HttpStatus.OK);
	}

	@GetMapping("/{clubId}/detail")
	public ResponseEntity<CustomResponseBody> clubDetails(Authentication authentication, @PathVariable Long clubId) {
		ClubDetailRes clubSimpleDetailRes = clubBasicService.findClubDetail(authentication.getName(), clubId);
		return new ResponseEntity<>(BaseResponseBody.of("소모임 상세 정보 조회에 성공했습니다", clubSimpleDetailRes), HttpStatus.OK);
	}

	@GetMapping("/check-duplicate")
	public ResponseEntity<CustomResponseBody> clubNameCheck(@RequestBody @Valid ClubNameReq clubNameReq,
		BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(ErrorResponseBody.of(ErrorCode.INVALID_INPUT_VALUE,
				bindingResult.getAllErrors().get(0).getDefaultMessage()),
				HttpStatus.BAD_REQUEST);
		}

		clubBasicService.checkDuplicateClubName(clubNameReq.getName());
		return new ResponseEntity<>(BaseResponseBody.of("사용할 수 있는 소모임 이름입니다"), HttpStatus.OK);
	}
}
