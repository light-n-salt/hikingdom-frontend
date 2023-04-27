package org.lightnsalt.hikingdom.common.security.jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

	private final byte[] secretKeyBytes;

	public JwtAuthenticationProvider(@Value("${security.jwt.token.secret-key}") String secretKey) {
		this.secretKeyBytes = secretKey.getBytes();
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Claims claims = Jwts.parserBuilder()
			.setSigningKey(secretKeyBytes)
			.build()
			.parseClaimsJws(((JwtAuthToken)authentication).getJwtAuthToken())
			.getBody();

		return new JwtAuthToken(claims.getSubject(), "", createGrantedAuthorities(claims));
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return JwtAuthToken.class.isAssignableFrom(authentication);
	}

	private Collection<? extends GrantedAuthority> createGrantedAuthorities(Claims claims) {
		@SuppressWarnings("unchecked")
		List<String> roles = (List<String>)claims.get("roles");
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

		for (String role : roles) {
			grantedAuthorities.add(() -> role);
		}

		return grantedAuthorities;
	}
}

