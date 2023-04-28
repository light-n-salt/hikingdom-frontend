package org.lightnsalt.hikingdom.domain.member.service;

import org.lightnsalt.hikingdom.domain.member.dto.request.MemberEmailAuthenticationReq;
import org.lightnsalt.hikingdom.domain.member.dto.request.MemberEmailReq;

public interface MemberEmailService {
	void sendFindPasswordEmail(MemberEmailReq memberEmailReq);

	void sendAuthenticationEmail(MemberEmailReq memberEmailReq);

	void confirmAuthenticationEmail(MemberEmailAuthenticationReq memberEmailAuthenticationReq);
}
