package org.lightnsalt.hikingdom.service.member.service;

import org.lightnsalt.hikingdom.domain.common.enumType.MemberRoleType;
import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.common.error.GlobalException;
import org.lightnsalt.hikingdom.service.member.dto.request.MemberSignUpReq;
import org.lightnsalt.hikingdom.domain.entity.member.Member;
import org.lightnsalt.hikingdom.domain.entity.member.MemberHikingStatistic;
import org.lightnsalt.hikingdom.domain.repository.member.MemberHikingStatisticRepository;
import org.lightnsalt.hikingdom.domain.repository.member.MemberLevelInfoRepository;
import org.lightnsalt.hikingdom.domain.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberSignUpServiceImpl implements MemberSignUpService {
	private final MemberHikingStatisticRepository memberHikingStatisticRepository;
	private final PasswordEncoder passwordEncoder;
	private final MemberRepository memberRepository;
	private final MemberLevelInfoRepository memberLevelInfoRepository;

	@Value("${values.profile.default}")
	private String memberDefaultProfileUrl;

	@Override
	@Transactional
	public void signUp(MemberSignUpReq memberSignUpReq) {
		String email = memberSignUpReq.getEmail();
		String nickname = memberSignUpReq.getNickname();

		if (!memberSignUpReq.getPassword().equals(memberSignUpReq.getCheckPassword()) ||
			memberRepository.existsByEmailAndIsWithdraw(email, false)
			|| memberRepository.existsByNicknameAndIsWithdraw(nickname, false)) {
			throw new GlobalException(ErrorCode.INVALID_INPUT_VALUE);
		}

		Member member = Member.builder()
			.email(email)
			.nickname(nickname)
			.password(passwordEncoder.encode(memberSignUpReq.getPassword()))
			.role(MemberRoleType.ROLE_USER)
			.level(memberLevelInfoRepository.findById(1)
				.orElseThrow(() -> new GlobalException(ErrorCode.INTERNAL_SERVER_ERROR)))
			.profileUrl(memberDefaultProfileUrl)
			.build();

		final Member savedMember = memberRepository.save(member);

		MemberHikingStatistic memberHikingStatistic = MemberHikingStatistic.builder()
			.member(savedMember)
			.build();

		memberHikingStatisticRepository.save(memberHikingStatistic);
	}

	@Override
	public void checkDuplicateNickname(String nickname) {
		if (memberRepository.existsByNicknameAndIsWithdraw(nickname, false)) {
			throw new GlobalException(ErrorCode.DUPLICATE_NICKNAME);
		}
	}
}
