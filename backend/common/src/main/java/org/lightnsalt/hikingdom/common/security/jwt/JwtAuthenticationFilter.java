package org.lightnsalt.hikingdom.common.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SecurityException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import org.lightnsalt.hikingdom.common.dto.ErrorResponseBody;
import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.common.util.RedisUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Slf4j
@Lazy
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final AuthenticationManager authenticationManager;
	private final RedisUtil redisUtil;

	JwtAuthenticationFilter(@Lazy AuthenticationManager authenticationManager, @Lazy RedisUtil redisUtil) {
		this.authenticationManager = authenticationManager;
		this.redisUtil = redisUtil;
	}

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
		@NonNull FilterChain filterChain)
		throws ServletException, IOException {
		try {
			String accessToken = resolveToken(request);

			if (StringUtils.hasText(accessToken)) {
				// check if access token is blacklisted (token is logged out)
				String isLogout = redisUtil.getValue(accessToken);
				if (StringUtils.hasText(isLogout)) {
					throw new ExpiredJwtException(null, null, "Token is logged out");
				}

				Authentication jwtAuthToken = new JwtAuthToken(accessToken);
				Authentication authentication = authenticationManager.authenticate(jwtAuthToken);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (SecurityException | MalformedJwtException | UnsupportedJwtException e) {
			log.error("Invalid JWT token : {}", e.getMessage());
			SecurityContextHolder.clearContext();
			sendErrorResponse(response, ErrorCode.INVALID_TOKEN);
			return;
		} catch (ExpiredJwtException e) {
			log.error("JWT token is expired: {}", e.getMessage());
			SecurityContextHolder.clearContext();
			sendErrorResponse(response, ErrorCode.EXPIRED_TOKEN);
			return;
		} catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty: {}", e.getMessage());
			SecurityContextHolder.clearContext();
			sendErrorResponse(response, ErrorCode.INVALID_TOKEN);
			return;
		}

		filterChain.doFilter(request, response);
	}

	private String resolveToken(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");

		if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7);
		}

		return null;
	}

	private void sendErrorResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();

		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.setStatus(errorCode.getStatus());
		response.getWriter().write(objectMapper.writeValueAsString(ErrorResponseBody.of(errorCode)));
	}
}
