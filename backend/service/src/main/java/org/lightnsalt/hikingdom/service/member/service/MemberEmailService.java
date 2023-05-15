package org.lightnsalt.hikingdom.service.member.service;

import org.lightnsalt.hikingdom.service.member.dto.request.MemberEmailAuthenticationReq;
import org.lightnsalt.hikingdom.service.member.dto.request.MemberEmailReq;

public interface MemberEmailService {
	void sendFindPasswordEmail(MemberEmailReq memberEmailReq);

	void sendAuthenticationEmail(MemberEmailReq memberEmailReq);

	void confirmAuthenticationEmail(MemberEmailAuthenticationReq memberEmailAuthenticationReq);
}
