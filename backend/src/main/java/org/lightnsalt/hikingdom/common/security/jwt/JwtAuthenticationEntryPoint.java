package org.lightnsalt.hikingdom.common.security.jwt;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lightnsalt.hikingdom.common.dto.ErrorResponseBody;
import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authenticationException)
		throws IOException {
		log.error("Authentication failed: {}", authenticationException.getMessage());
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
