package org.lightnsalt.hikingdom.domain.member.service;

import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.common.error.GlobalException;
import org.lightnsalt.hikingdom.common.util.JwtTokenUtil;
import org.lightnsalt.hikingdom.common.util.RedisUtil;
import org.lightnsalt.hikingdom.domain.member.dto.request.MemberChangePasswordReq;
import org.lightnsalt.hikingdom.domain.member.dto.request.MemberNicknameReq;
import org.lightnsalt.hikingdom.domain.member.entity.Member;
import org.lightnsalt.hikingdom.domain.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberManagementServiceImpl implements MemberManagementService {
	private final PasswordEncoder passwordEncoder;

	private final RedisUtil redisUtil;
	private final JwtTokenUtil jwtTokenUtil;

	private final MemberRepository memberRepository;

	@Override
	public void logout(String bearerToken) {
		String accessToken = jwtTokenUtil.resolveToken(bearerToken);
		String email = jwtTokenUtil.getEmailFromToken(accessToken);

		if (redisUtil.getValue("RT" + email) != null) {
			redisUtil.deleteValue("RT" + email);
		}

		Long expiration = jwtTokenUtil.getExpirationFromToken(accessToken);
		redisUtil.setValueWithExpiration(accessToken, "logout", expiration);
	}

	@Override
	public void changePassword(String email, MemberChangePasswordReq memberChangePasswordReq) {
		if (!memberChangePasswordReq.getCheckPassword().equals(memberChangePasswordReq.getNewPassword())) {
			throw new GlobalException(ErrorCode.INVALID_INPUT_VALUE);
		}

		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new GlobalException(ErrorCode.INVALID_LOGIN));

		boolean isValidPassword = passwordEncoder.matches(member.getPassword(), memberChangePasswordReq.getPassword());

		if (!isValidPassword) {
			throw new GlobalException(ErrorCode.INVALID_LOGIN);
		}

		memberRepository.setPasswordById(memberChangePasswordReq.getNewPassword(), member.getId());
	}

	@Override
	public void changeNickname(String email, MemberNicknameReq memberNicknameReq) {
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new GlobalException(ErrorCode.INVALID_LOGIN));

		String newNickname = memberNicknameReq.getNickname();

		if (member.getNickname().equals(newNickname)) {
			return;
		}

		if (!memberRepository.existsByNickname(newNickname)) {
			throw new GlobalException(ErrorCode.DUPLICATE_NICKNAME);
		}

		memberRepository.setNicknameById(newNickname, member.getId());
	}
}
