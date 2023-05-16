package org.lightnsalt.hikingdom.common.security.jwt;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lightnsalt.hikingdom.common.dto.ErrorResponseBody;
import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
		AccessDeniedException accessDeniedException) throws IOException {
		log.error("Authentication failed: {}", accessDeniedException.getMessage());
		SecurityContextHolder.clearContext();
		sendErrorResponse(response);
	}

	private void sendErrorResponse(HttpServletResponse response) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();

		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.setStatus(ErrorCode.MEMBER_UNAUTHORIZED.getStatus());
		response.getWriter().write(objectMapper.writeValueAsString(ErrorResponseBody.of(ErrorCode.MEMBER_UNAUTHORIZED)));
	}
}
