package org.lightnsalt.hikingdom.domain.member.service;

import static org.lightnsalt.hikingdom.common.error.ErrorCode.*;

import java.security.SecureRandom;

import org.lightnsalt.hikingdom.common.error.GlobalException;
import org.lightnsalt.hikingdom.domain.member.common.enumtype.MemberRoleType;
import org.lightnsalt.hikingdom.domain.member.dto.request.MemberSignUpReq;
import org.lightnsalt.hikingdom.domain.member.entity.Member;
import org.lightnsalt.hikingdom.domain.member.repository.MemberLevelInfoRepository;
import org.lightnsalt.hikingdom.domain.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberAuthServiceImpl implements MemberAuthService {

	private static final SecureRandom secureRandom = new SecureRandom();
	private final PasswordEncoder passwordEncoder;

	private final MemberRepository memberRepository;
	private final MemberLevelInfoRepository memberLevelInfoRepository;

	@Transactional
	@Override
	public Long signUp(MemberSignUpReq memberSignUpReq) {
		if (!memberSignUpReq.getPassword().equals(memberSignUpReq.getCheckPassword()) ||
			memberRepository.existsByEmail(memberSignUpReq.getEmail())
			|| memberRepository.existsByNickname(memberSignUpReq.getNickname())) {
			throw new GlobalException(INVALID_INPUT_VALUE);
		}

		Member member = Member.builder()
			.email(memberSignUpReq.getEmail())
			.nickname(memberSignUpReq.getNickname())
			.password(passwordEncoder.encode(memberSignUpReq.getPassword()))
			.role(MemberRoleType.ROLE_USER)
			.level(memberLevelInfoRepository.findById(1L).orElseThrow(() -> new GlobalException(INTERNAL_SERVER_ERROR)))
			.build();

		memberRepository.save(member);

		return member.getId();
	}
}
