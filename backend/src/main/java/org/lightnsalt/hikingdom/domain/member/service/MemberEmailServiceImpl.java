package org.lightnsalt.hikingdom.domain.member.service;

import java.security.SecureRandom;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.common.error.GlobalException;
import org.lightnsalt.hikingdom.common.util.RedisUtil;
import org.lightnsalt.hikingdom.domain.member.dto.request.MemberEmailAuthenticationReq;
import org.lightnsalt.hikingdom.domain.member.dto.request.MemberEmailReq;
import org.lightnsalt.hikingdom.domain.member.entity.Member;
import org.lightnsalt.hikingdom.domain.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberEmailServiceImpl implements MemberEmailService {
	private static final SecureRandom secureRandom = new SecureRandom();
	private static final char[] possiblePasswordCharacters =
		("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~!@#$%^&*()+|=").toCharArray();
	private final JavaMailSender javaMailSender;
	private final PasswordEncoder passwordEncoder;
	private final RedisUtil redisUtil;
	private final MemberRepository memberRepository;
	@Value("{mail.setFrom}")
	private String fromEmail;

	@Transactional
	@Override
	public void sendFindPasswordEmail(MemberEmailReq memberEmailReq) {
		String email = memberEmailReq.getEmail();
		final Member member = memberRepository.findByEmailAndIsWithdraw(email, false)
			.orElseThrow(() -> new GlobalException(ErrorCode.INVALID_INPUT_VALUE));

		String tempPassword = RandomStringUtils.random(10, 0, possiblePasswordCharacters.length - 1, false, false,
			possiblePasswordCharacters, secureRandom);

		// send mail with temporary password
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			message.setFrom(fromEmail);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
			message.setSubject("Hikingdom 임시 비밀번호 발급 메일입니다.");
			message.setText(createFindPasswordEmail(tempPassword), "UTF-8", "html");
			javaMailSender.send(message);

			if (!setTemporaryPassword(tempPassword, member.getId()))
				throw new GlobalException(ErrorCode.INTERNAL_SERVER_ERROR);
		} catch (MessagingException e) {
			log.error("Failure while sending authentication email to " + email);
			throw new GlobalException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public void sendAuthenticationEmail(MemberEmailReq memberEmailReq) {
		if (memberRepository.existsByEmailAndIsWithdraw(memberEmailReq.getEmail(), false)) {
			throw new GlobalException(ErrorCode.DUPLICATE_EMAIL);
		}

		String email = memberEmailReq.getEmail();
		String authCode = createAuthCode(6);

		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			message.setFrom(fromEmail);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
			message.setSubject("Hikingdom 회원가입 인증번호 메일입니다.");
			message.setText(createAuthenticationEmail(authCode), "UTF-8", "html");
			javaMailSender.send(message);

			redisUtil.setValueWithExpiration("AUTH" + email, authCode, 60 * 5);
		} catch (MessagingException e) {
			log.error("Failure while sending find authentication email to " + email);
			throw new GlobalException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public void confirmAuthenticationEmail(MemberEmailAuthenticationReq memberEmailAuthenticationReq) {
		String redisKey = "AUTH" + memberEmailAuthenticationReq.getEmail();

		if (redisUtil.getValue(redisKey) == null) {
			throw new GlobalException(ErrorCode.INVALID_INPUT_VALUE);
		}

		if (redisUtil.getValue(redisKey).equals(memberEmailAuthenticationReq.getAuthCode())) {
			redisUtil.deleteValue(redisKey);
		} else {
			throw new GlobalException(ErrorCode.INVALID_INPUT_VALUE);
		}
	}

	@Transactional
	boolean setTemporaryPassword(String tempPassword, Long memberId) {
		return memberRepository.setPasswordById(passwordEncoder.encode(tempPassword), memberId) > 0;
	}

	private String createFindPasswordEmail(String password) {
		return "임시 비밀번호는 " + password + "입니다";
	}

	private String createAuthenticationEmail(String authCode) {
		return "인증번호는 " + authCode + "입니다. 5분 간 유효합니다.";
	}

	private String createAuthCode(int length) {
		StringBuilder authCode = new StringBuilder();

		while (authCode.length() < length) {
			authCode.append(secureRandom.nextInt(10));
		}

		return authCode.toString();
	}
}
