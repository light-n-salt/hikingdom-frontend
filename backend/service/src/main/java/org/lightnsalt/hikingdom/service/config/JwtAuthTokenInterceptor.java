package org.lightnsalt.hikingdom.service.config;

import org.lightnsalt.hikingdom.common.security.jwt.JwtAuthToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class JwtAuthTokenInterceptor implements RequestInterceptor {
	@Override
	public void apply(RequestTemplate requestTemplate) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// get JWT token from Authentication
		if (authentication instanceof JwtAuthToken && authentication.getCredentials() != null) {
			String jwtAuthToken = authentication.getCredentials().toString();

			// add JWT token to request header
			requestTemplate.header("Authorization", "Bearer " + jwtAuthToken);
		}
	}
}
