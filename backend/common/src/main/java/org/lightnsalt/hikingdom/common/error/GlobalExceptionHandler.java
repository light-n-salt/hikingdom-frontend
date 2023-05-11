package org.lightnsalt.hikingdom.common.error;

import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.lightnsalt.hikingdom.common.dto.ErrorResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

/**
 * Global exception handler
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	/**
	 * javax.validation.Valid, @Validated 등으로 검증 실패 시 발생하는 에러 처리
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalArgumentException.class)
	protected ResponseEntity<ErrorResponseBody> handleIllegalArgumentException(IllegalArgumentException e) {
		log.error("handleIllegalArgumentException", e);
		return new ResponseEntity<>(ErrorResponseBody.of(ErrorCode.INVALID_INPUT_VALUE, e.getMessage()),
			HttpStatus.BAD_REQUEST);
	}

	/**
	 * 지원하지 않은 HTTP method 호출 시 발생하는 에러 처리
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	protected ResponseEntity<ErrorResponseBody> handleHttpRequestMethodNotSupportedException(
		HttpRequestMethodNotSupportedException e) {
		log.error("handleHttpRequestMethodNotSupportedException", e);
		return new ResponseEntity<>(ErrorResponseBody.of(ErrorCode.METHOD_NOT_ALLOWED), HttpStatus.METHOD_NOT_ALLOWED);
	}

	/**
	 * 필요한 Request Body가 없을 때 발생하는 에러 처리
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	protected ResponseEntity<ErrorResponseBody> handleHttpMessageNotReadableException(
		HttpMessageNotReadableException e) {
		log.error("handleHttpMessageNotReadableException", e);
		return new ResponseEntity<>(ErrorResponseBody.of(ErrorCode.MISSING_REQUEST_BODY), HttpStatus.BAD_REQUEST);
	}

	/**
	 * 필요한 Request Parameter가 없을 때 발생하는 에러 처리
	 */
	@ExceptionHandler(MissingServletRequestParameterException.class)
	protected ResponseEntity<ErrorResponseBody> handleMissingServletRequestParameterException(
		MissingServletRequestParameterException e) {
		log.error("handleMissingServletRequestParameterException", e);
		return new ResponseEntity<>(ErrorResponseBody.of(ErrorCode.MISSING_REQUEST_PARAMETER),
			HttpStatus.BAD_REQUEST);
	}

	/**
	 * PathVariable 형식 에러 처리
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<ErrorResponseBody> handleConstraintValidationException(
		final ConstraintViolationException e) {
		log.error("handleConstraintValidationException", e);
		String errorMessage = e.getMessage();
		if (StringUtils.contains(errorMessage, ":")) {
			errorMessage = StringUtils.substringAfter(errorMessage, ":").trim();
		}

		return new ResponseEntity<>(ErrorResponseBody.of(ErrorCode.INVALID_INPUT_VALUE, errorMessage),
			HttpStatus.BAD_REQUEST);
	}

	/**
	 * 프로젝트 내 설정한 에러 처리
	 */
	@ExceptionHandler(GlobalException.class)
	protected ResponseEntity<ErrorResponseBody> handleGlobalException(final GlobalException e) {
		log.error("handleGlobalException", e);
		final ErrorCode errorCode = e.getErrorCode();
		final ErrorResponseBody response = ErrorResponseBody.of(errorCode);
		return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
	}

	/**
	 * 그 외 에러 처리
	 */
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorResponseBody> handleException(Exception e) {
		log.error("handleException", e);
		return new ResponseEntity<>(ErrorResponseBody.of(ErrorCode.INTERNAL_SERVER_ERROR),
			HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
