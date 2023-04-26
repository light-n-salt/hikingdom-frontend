package org.lightnsalt.hikingdom.common.error;

import lombok.Getter;

/**
 * List of possible error codes
 */
@Getter
public enum ErrorCode {

	// common error codes
	INVALID_INPUT_VALUE(400, "C001", "유효하지 않은 입력값입니다"),
	METHOD_NOT_ALLOWED(405, "C002", " HTTP 요청 메서드가 서버에서 허용되지 않습니다"),
	INTERNAL_SERVER_ERROR(500, "C003", "서버에서 예상치 못한 오류가 발생했습니다"),
	INVALID_LOGIN(401, "C004", "유효하지 않은 로그인 정보입니다"),
	INVALID_TOKEN(401, "C005", "유효하지 않은 토큰입니다"),

	// business error codes
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
