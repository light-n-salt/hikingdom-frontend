package org.lightnsalt.hikingdom.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import lombok.Getter;

/**
 * Common response body format
 */
@Getter
@Builder
@JsonPropertyOrder({"message", "result"})
public class BaseResponseBody implements CustomResponseBody {
	private String message;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Object result;

	public static BaseResponseBody of(final String msg) {
		return BaseResponseBody.builder()
			.message(msg)
			.build();
	}

	public static BaseResponseBody of(final String message, final Object result) {
		return BaseResponseBody.builder()
			.message(message)
			.result(result)
			.build();
	}
}
