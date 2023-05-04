package org.lightnsalt.hikingdom.domain.member.service;

import java.io.IOException;

import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.common.error.GlobalException;
import org.lightnsalt.hikingdom.common.util.JwtTokenUtil;
import org.lightnsalt.hikingdom.common.util.RedisUtil;
import org.lightnsalt.hikingdom.common.util.S3FileUtil;
import org.lightnsalt.hikingdom.entity.club.ClubMember;
import org.lightnsalt.hikingdom.domain.club.repository.ClubMemberRepository;
import org.lightnsalt.hikingdom.domain.member.dto.request.MemberChangePasswordReq;
import org.lightnsalt.hikingdom.domain.member.dto.request.MemberNicknameReq;
import org.lightnsalt.hikingdom.domain.member.dto.response.MemberInfoRes;
import org.lightnsalt.hikingdom.entity.member.Member;
import org.lightnsalt.hikingdom.domain.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberManagementServiceImpl implements MemberManagementService {
	private final PasswordEncoder passwordEncoder;

	private final S3FileUtil s3FileUtil;
	private final RedisUtil redisUtil;
	private final JwtTokenUtil jwtTokenUtil;

	private final ClubMemberRepository clubMemberRepository;
	private final MemberRepository memberRepository;

	@Override
	public MemberInfoRes findMemberInfo(String email) {
		final Member member = memberRepository.findByEmailAndIsWithdraw(email, false)
			.orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_UNAUTHORIZED));
		final ClubMember clubMember = clubMemberRepository.findByMemberIdAndIsWithdraw(member.getId(), false)
			.orElse(null);

		return MemberInfoRes.builder()
			.email(member.getEmail())
			.nickname(member.getNickname())
			.profileUrl(member.getProfileUrl())
			.clubId(clubMember != null ? clubMember.getClub().getId() : null)
			.level(member.getLevel().getId())
			.build();
	}

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

	@Transactional
	@Override
	public void changePassword(String email, MemberChangePasswordReq memberChangePasswordReq) {
		if (!memberChangePasswordReq.getCheckPassword().equals(memberChangePasswordReq.getNewPassword())) {
			throw new GlobalException(ErrorCode.INVALID_INPUT_VALUE);
		}

		final Member member = memberRepository.findByEmailAndIsWithdraw(email, false)
			.orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_UNAUTHORIZED));

		boolean isValidPassword = passwordEncoder.matches(memberChangePasswordReq.getPassword(), member.getPassword());

		if (!isValidPassword) {
			throw new GlobalException(ErrorCode.MEMBER_UNAUTHORIZED);
		}

		memberRepository.setPasswordById(passwordEncoder.encode(memberChangePasswordReq.getNewPassword()),
			member.getId());
	}

	@Transactional
	@Override
	public void changeNickname(String email, MemberNicknameReq memberNicknameReq) {
		final Member member = memberRepository.findByEmailAndIsWithdraw(email, false)
			.orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_UNAUTHORIZED));

		String newNickname = memberNicknameReq.getNickname();

		if (member.getNickname().equals(newNickname)) {
			return;
		}

		if (memberRepository.existsByNicknameAndIsWithdraw(newNickname, false)) {
			throw new GlobalException(ErrorCode.DUPLICATE_NICKNAME);
		}

		if (!setNickname(newNickname, member.getId()))
			throw new GlobalException(ErrorCode.INTERNAL_SERVER_ERROR);
	}

	@Transactional
	@Override
	public String changeProfileImage(String email, MultipartFile photo) {
		final Member member = memberRepository.findByEmailAndIsWithdraw(email, false)
			.orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_UNAUTHORIZED));

		try {
			String url = s3FileUtil.upload(photo, "members/" + member.getId() + "/profiles");
			memberRepository.setProfileUrlById(url, member.getId());

			return url;
		} catch (IOException e) {
			throw new GlobalException(ErrorCode.FAIL_TO_SAVE_PHOTO);
		}
	}

	@Transactional
	boolean setNickname(String nickname, Long memberId) {
		return memberRepository.setNicknameById(nickname, memberId) > 0;
	}
}
