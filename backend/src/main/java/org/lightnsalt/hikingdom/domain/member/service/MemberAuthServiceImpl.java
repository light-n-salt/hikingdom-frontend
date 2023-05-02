package org.lightnsalt.hikingdom.domain.member.service;

import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.common.error.GlobalException;
import org.lightnsalt.hikingdom.common.util.JwtTokenUtil;
import org.lightnsalt.hikingdom.common.util.RedisUtil;
import org.lightnsalt.hikingdom.domain.member.dto.request.MemberLoginReq;
import org.lightnsalt.hikingdom.domain.member.dto.request.MemberRefreshTokenReq;
import org.lightnsalt.hikingdom.domain.member.dto.response.MemberTokenRes;
import org.lightnsalt.hikingdom.domain.member.entity.Member;
import org.lightnsalt.hikingdom.domain.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberAuthServiceImpl implements MemberAuthService {
	private final PasswordEncoder passwordEncoder;

	private final RedisUtil redisUtil;
	private final JwtTokenUtil jwtTokenUtil;

	private final MemberRepository memberRepository;

	@Override
	public MemberTokenRes login(MemberLoginReq memberLoginReq) {
		String email = memberLoginReq.getEmail();

		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_UNAUTHORIZED));

		if (!passwordEncoder.matches(memberLoginReq.getPassword(), member.getPassword())) {
			throw new GlobalException(ErrorCode.MEMBER_UNAUTHORIZED);
		}

		String accessToken = jwtTokenUtil.createAccessToken(email, member.getRole());
		String refreshToken = jwtTokenUtil.createRefreshToken(email, member.getRole());

		redisUtil.deleteValue("RT" + email);
		redisUtil.setValueWithExpiration("RT" + email, refreshToken.substring(7), jwtTokenUtil.refreshExpiration);

		log.info("Successful Login: " + email);

		return MemberTokenRes.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}

	@Override
	public MemberTokenRes refreshToken(MemberRefreshTokenReq memberRefreshTokenReq) {
		String oldRefreshToken = jwtTokenUtil.resolveToken(memberRefreshTokenReq.getRefreshToken());
		String email = jwtTokenUtil.getEmailFromToken(oldRefreshToken);

		if (!oldRefreshToken.equals(redisUtil.getValue("RT" + email))) {
			throw new GlobalException(ErrorCode.INVALID_TOKEN);
		}

		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_UNAUTHORIZED));

		String accessToken = jwtTokenUtil.createAccessToken(email, member.getRole());
		String refreshToken = jwtTokenUtil.createRefreshToken(email, member.getRole());

		redisUtil.deleteValue("RT" + email);
		redisUtil.setValueWithExpiration("RT" + email, refreshToken.substring(7), jwtTokenUtil.refreshExpiration);

		return MemberTokenRes.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}
}
