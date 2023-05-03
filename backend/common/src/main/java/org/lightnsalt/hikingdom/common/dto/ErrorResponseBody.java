package org.lightnsalt.hikingdom.common.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.lightnsalt.hikingdom.common.error.ErrorCode;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * Common error response body format
 */
@Getter
@Builder
@AllArgsConstructor
@JsonPropertyOrder({"timestamp", "code", "message"})
public class ErrorResponseBody {
	private final String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	private String code;
	private String message;

	private ErrorResponseBody(final ErrorCode errorCode) {
		this.code = errorCode.getCode();
		this.message = errorCode.getMessage();
	}

	public static ErrorResponseBody of(final ErrorCode errorCode) {
		return new ErrorResponseBody(errorCode);
	}

	public static ErrorResponseBody of(final ErrorCode errorCode, String message) {
		return ErrorResponseBody.builder()
			.code(errorCode.getCode())
			.message(message)
			.build();
	}
}
