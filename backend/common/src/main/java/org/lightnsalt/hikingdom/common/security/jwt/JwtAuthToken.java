package org.lightnsalt.hikingdom.common.security.jwt;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthToken extends AbstractAuthenticationToken {

	private String jwtAuthToken;
	private Object principal; // currently logged-in user
	private Object credentials;

	public JwtAuthToken(String jwtAuthToken) {
		super(null);
		this.jwtAuthToken = jwtAuthToken;
		this.setAuthenticated(false);
	}

	public JwtAuthToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		this.credentials = credentials;
		super.setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		return this.credentials;
	}

	@Override
	public Object getPrincipal() {
		return this.principal;
	}

	public String getJwtAuthToken() {
		return this.jwtAuthToken;
	}
}
