package org.lightnsalt.hikingdom.common.util;

import java.security.Key;
import java.util.Date;
import java.util.List;

import org.lightnsalt.hikingdom.common.enumType.MemberRoleType;
import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.common.error.GlobalException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;

@Component
public class JwtTokenUtil {
	public final Long refreshExpiration;
	private final Key secretKey;
	private final Long expiration;

	public JwtTokenUtil(@Value("${security.jwt.token.secret-key}") String secretKeyString,
		@Value("${security.jwt.token.expire-length}") long expiration,
		@Value("${security.jwt.token.refresh-expire-length}") long refreshExpiration) {
		this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKeyString));
		this.expiration = expiration;
		this.refreshExpiration = refreshExpiration;
	}

	private String createToken(String email, MemberRoleType role, String tokenType, long expiration) {
		Date now = new Date();

		Claims claims = Jwts.claims().setSubject(email);
		claims.put("roles", List.of(role));
		claims.put("type", tokenType);

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + expiration))
			.signWith(secretKey, SignatureAlgorithm.HS256)
			.compact();
	}

	private Claims getClaimsFromToken(String token) throws
		ExpiredJwtException, SecurityException, MalformedJwtException, IllegalArgumentException {
		return Jwts.parserBuilder()
			.setSigningKey(secretKey)
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

	public String createAccessToken(String email, MemberRoleType role) {
		return "Bearer " + createToken(email, role, "access", expiration);
	}

	public String createRefreshToken(String email, MemberRoleType role) {
		return "Bearer " + createToken(email, role, "refresh", refreshExpiration);
	}

	public String resolveToken(String bearerToken) {
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		throw new GlobalException(ErrorCode.INVALID_TOKEN);
	}

	public String getEmailFromToken(String token) {
		return getClaimsFromToken(token).getSubject();
	}

	public Long getExpirationFromToken(String token) {
		Date expiration = getClaimsFromToken(token).getExpiration();
		long now = new Date().getTime();
		return (expiration.getTime() - now);
	}
}
