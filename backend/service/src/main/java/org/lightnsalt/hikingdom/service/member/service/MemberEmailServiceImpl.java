package org.lightnsalt.hikingdom.service.member.service;

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
import org.lightnsalt.hikingdom.service.member.dto.request.MemberEmailAuthenticationReq;
import org.lightnsalt.hikingdom.service.member.dto.request.MemberEmailReq;
import org.lightnsalt.hikingdom.domain.entity.member.Member;
import org.lightnsalt.hikingdom.service.member.repository.MemberRepository;
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
	private final JavaMailSender javaMailSender;
	private final PasswordEncoder passwordEncoder;
	private final RedisUtil redisUtil;
	private final MemberRepository memberRepository;

	@Value("${values.mail.setFrom}")
	private String fromEmail;
	@Value("${values.password.possibleChars}")
	private char[] possiblePasswordCharacters;

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

			redisUtil.setValueWithExpiration("AUTH" + email, authCode, (long) 60 * 5);
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
		return
			"<table align=\"center\" width=\"670\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border: 3px solid #50B06A;\n"
				+ "    box-shadow: 0px 2px 10px #50B06A;\n"
				+ "    border-radius: 30px;\">\n"
				+ "\t\t<tbody>\n"
				+ "\t\t\t<tr>\n"
				+ "\t\t\t\t<td style=\"background-color: #ffffff;border-radius: 30px; padding: 40px 30px 0 35px; text-align: center;\">\n"
				+ "\t\t\t\t\t<table width=\"605\" cellpadding=\"0\" cellspacing=\"0\" style=\"text-align: left; font-family: '맑은 고딕','돋움';\">\n"
				+ "\t\t\t\t\t\t<tbody>\n"
				+ "\t\t\t\t\t\t\t<tr>\n"
				+ "\t\t\t\t\t\t\t\t<td\n"
				+ "\t\t\t\t\t\t\t\t\tstyle=\"color: #065446; font-size: 25px; text-align: left; width: 352px; word-spacing: -1px; vertical-align: top;\">\n"
				+ "\t\t\t\t\t\t\t\t\t임시 비밀번호 확인 후<br>\n"
				+ "\t\t\t\t\t\t\t\t\t로그인을 완료해 주세요.\n"
				+ "\t\t\t\t\t\t\t\t</td>\n"
				+ "\t\t\t\t\t\t\t\t<td rowspan=\"3\" style=\"text-align: center;\"><img style=\"width: 150px;\"\n"
				+ "\t\t\t\t\t\t\t\t\t\tsrc=\"https://lightnsalt.s3.ap-northeast-2.amazonaws.com/logo.png\" loading=\"lazy\"></td>\n"
				+ "\t\t\t\t\t\t\t</tr>\n"
				+ "\t\t\t\t\t\t\t<tr>\n"
				+ "\t\t\t\t\t\t\t\t<td height=\"39\"><img style=\"width: 60px;\"\n"
				+ "\t\t\t\t\t\t\t\t\t\tsrc=\"https://lightnsalt.s3.ap-northeast-2.amazonaws.com/bar.png\" loading=\"lazy\"></td>\n"
				+ "\t\t\t\t\t\t\t</tr>\n"
				+ "\t\t\t\t\t\t\t<tr>\n"
				+ "\t\t\t\t\t\t\t\t<td style=\"font-size: 17px; vertical-align: bottom; height: 27px;\">안녕하세요? Hikingdom입니다.</td>\n"
				+ "\t\t\t\t\t\t\t</tr>\n"
				+ "\t\t\t\t\t\t\t<tr>\n"
				+ "\t\t\t\t\t\t\t\t<td colspan=\"2\" style=\"font-size: 13px; word-spacing: -1px; height: 30px;\">아래 비밀번호를\n"
				+ "\t\t\t\t\t\t\t\t\t입력하시고 로그인을 계속 진행해 주세요.</td>\n"
				+ "\t\t\t\t\t\t\t</tr>\n"
				+ "\t\t\t\t\t\t</tbody>\n"
				+ "\t\t\t\t\t</table>\n"
				+ "\t\t\t\t</td>\n"
				+ "\t\t\t</tr>\n"
				+ "\t\t\t<tr>\n"
				+ "\t\t\t\t<td style=\"padding: 39px 196px 70px;\">\n"
				+ "\t\t\t\t\t<table width=\"278\" style=\"background-color: #065446; font-family: '맑은 고딕','돋움';\">\n"
				+ "\t\t\t\t\t\t<tbody>\n"
				+ "\t\t\t\t\t\t\t<tr>\n"
				+ "\t\t\t\t\t\t\t\t<td height=\"49\" style=\"text-align: center; color: #fff\">비밀번호 : <span>"
				+ password +
				"</span>\n"
				+ "\t\t\t\t\t\t\t\t</td>\n"
				+ "\t\t\t\t\t\t\t</tr>\n"
				+ "\t\t\t\t\t\t</tbody>\n"
				+ "\t\t\t\t\t</table>\n"
				+ "\t\t\t\t</td>\n"
				+ "\t\t\t</tr>\n"
				+ "\t\t</tbody>\n"
				+ "\t</table>";
	}

	private String createAuthenticationEmail(String authCode) {
		return
			"<table align=\"center\" width=\"670\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border: 3px solid #50B06A;\n"
				+ "    box-shadow: 0px 2px 10px #50B06A;\n"
				+ "    border-radius: 30px;\">\n"
				+ "\t\t<tbody>\n"
				+ "\t\t\t<tr>\n"
				+ "\t\t\t\t<td style=\"background-color: #ffffff;border-radius: 30px; padding: 40px 30px 0 35px; text-align: center;\">\n"
				+ "\t\t\t\t\t<table width=\"605\" cellpadding=\"0\" cellspacing=\"0\" style=\"text-align: left; font-family: '맑은 고딕','돋움';\">\n"
				+ "\t\t\t\t\t\t<tbody>\n"
				+ "\t\t\t\t\t\t\t<tr>\n"
				+ "\t\t\t\t\t\t\t\t<td\n"
				+ "\t\t\t\t\t\t\t\t\tstyle=\"color: #065446; font-size: 25px; text-align: left; width: 352px; word-spacing: -1px; vertical-align: top;\">\n"
				+ "\t\t\t\t\t\t\t\t\t인증 번호 확인 후<br>\n"
				+ "\t\t\t\t\t\t\t\t\t이메일 인증을 완료해 주세요.\n"
				+ "\t\t\t\t\t\t\t\t</td>\n"
				+ "\t\t\t\t\t\t\t\t<td rowspan=\"3\" style=\"text-align: center;\"><img style=\"width: 150px;\"\n"
				+ "\t\t\t\t\t\t\t\t\t\tsrc=\"https://lightnsalt.s3.ap-northeast-2.amazonaws.com/logo.png\" loading=\"lazy\"></td>\n"
				+ "\t\t\t\t\t\t\t</tr>\n"
				+ "\t\t\t\t\t\t\t<tr>\n"
				+ "\t\t\t\t\t\t\t\t<td height=\"39\"><img style=\"width: 60px;\"\n"
				+ "\t\t\t\t\t\t\t\t\t\tsrc=\"https://lightnsalt.s3.ap-northeast-2.amazonaws.com/bar.png\" loading=\"lazy\"></td>\n"
				+ "\t\t\t\t\t\t\t</tr>\n"
				+ "\t\t\t\t\t\t\t<tr>\n"
				+ "\t\t\t\t\t\t\t\t<td style=\"font-size: 17px; vertical-align: bottom; height: 27px;\">안녕하세요? Hikingdom입니다.</td>\n"
				+ "\t\t\t\t\t\t\t</tr>\n"
				+ "\t\t\t\t\t\t\t<tr>\n"
				+ "\t\t\t\t\t\t\t\t<td colspan=\"2\" style=\"font-size: 13px; word-spacing: -1px; height: 30px;\">아래 인증번호를 입력하시고\n"
				+ "\t\t\t\t\t\t\t\t\t회원가입을 계속 진행해 주세요.</td>\n"
				+ "\t\t\t\t\t\t\t</tr>\n"
				+ "\t\t\t\t\t\t</tbody>\n"
				+ "\t\t\t\t\t</table>\n"
				+ "\t\t\t\t</td>\n"
				+ "\t\t\t</tr>\n"
				+ "\t\t\t<tr>\n"
				+ "\t\t\t\t<td style=\"padding: 39px 196px 70px;\">\n"
				+ "\t\t\t\t\t<table width=\"278\" style=\"background-color: #065446; font-family: '맑은 고딕','돋움';\">\n"
				+ "\t\t\t\t\t\t<tbody>\n"
				+ "\t\t\t\t\t\t\t<tr>\n"
				+ "\t\t\t\t\t\t\t\t<td height=\"49\" style=\"text-align: center; color: #fff\">인증번호 : <span>"
				+ authCode
				+ "</span>\n"
				+ "\t\t\t\t\t\t\t\t</td>\n"
				+ "\t\t\t\t\t\t\t</tr>\n"
				+ "\t\t\t\t\t\t</tbody>\n"
				+ "\t\t\t\t\t</table>\n"
				+ "\t\t\t\t</td>\n"
				+ "\t\t\t</tr>\n"
				+ "\t\t</tbody>\n"
				+ "\t</table>";
	}

	@SuppressWarnings("SameParameterValue") // 미래에 다른 길이의 인증코드를 생성할 수도 있음
	private String createAuthCode(int length) {
		StringBuilder authCode = new StringBuilder();

		while (authCode.length() < length) {
			authCode.append(secureRandom.nextInt(10));
		}

		return authCode.toString();
	}
}
