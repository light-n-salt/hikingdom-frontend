package org.lightnsalt.hikingdom.common.security.jwt;

import java.security.Key;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

	private final Key secretKey;

	public JwtAuthenticationProvider(@Value("${security.jwt.token.secret-key}") String secretKeyString) {
		this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKeyString));
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String jwtAuthToken = authentication.getCredentials().toString();
		Claims claims = Jwts.parserBuilder()
			.setSigningKey(secretKey)
			.build()
			.parseClaimsJws(jwtAuthToken)
			.getBody();

		return new JwtAuthToken(claims.getSubject(), jwtAuthToken, createGrantedAuthorities(claims));
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return JwtAuthToken.class.isAssignableFrom(authentication);
	}

	private Collection<? extends GrantedAuthority> createGrantedAuthorities(Claims claims) {
		@SuppressWarnings("unchecked") // JWT token 생성 시, roles를 String(Enum) 배열로 정의하기 때문에 형변환 에러 발생하지 않음
		List<String> roles = (List<String>)claims.get("roles");
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

		for (String role : roles) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role));
		}

		return grantedAuthorities;
	}
}

