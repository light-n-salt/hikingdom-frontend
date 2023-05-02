package org.lightnsalt.hikingdom.common.error;

import lombok.Getter;

/**
 * List of possible error codes
 */
@Getter
public enum ErrorCode {

	// common error codes
	INVALID_INPUT_VALUE(400, "C001", "입력값이 유효하지 않습니다"),
	METHOD_NOT_ALLOWED(405, "C002", "지원하지 않는 HTTP 요청 메서드입니다"),
	INTERNAL_SERVER_ERROR(500, "C003", "서버에서 예상치 못한 오류가 발생했습니다"),
	INVALID_LOGIN(401, "C004", "인증 자격 증명이 유효하지 않습니다"),
	INVALID_TOKEN(401, "C005", "토큰이 유효하지 않습니다"),
	EXPIRED_TOKEN(401, "C006", "토큰이 만료되었습니다"),
	MISSING_REQUEST_BODY(400, "C007", "응답 요청 내용이 없습니다"),

	// business error codes

	// member related error codes
	DUPLICATE_EMAIL(400, "M001", "사용할 수 없는 이메일입니다"),
	DUPLICATE_NICKNAME(400, "M002", "사용할 수 없는 닉네임입니다"),

	// info related error codes
	DUPLICATED_MOUNTAIN_REGISTER(403, "M001", "이미 등록된 산 이름입니다"),
	MOUNTAIN_NOT_FOUND(404, "M002", "존재하지 않는 데이터입니다"),
	BASE_ADDRESS_NOT_FOUND(404, "L001", "존재하지 않는 지역 정보입니다"),

	// club related error codes
	ALREADY_JOINED_CLUB(400, "K001", "이미 소모임에 가입되어 있습니다"),
	DUPLICATE_CLUB_NAME(400, "K002", "이미 존재하는 소모임 이름입니다"),
	CLUB_NOT_FOUND(404, "K003", "존재하지 않는 소모임입니다")
	;

	private final int status;
	private final String code;
	private final String message;

	ErrorCode(final int status, final String code, final String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
}
