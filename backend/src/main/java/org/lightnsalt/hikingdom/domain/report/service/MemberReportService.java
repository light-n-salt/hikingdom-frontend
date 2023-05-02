package org.lightnsalt.hikingdom.domain.report.service;

import org.lightnsalt.hikingdom.domain.report.dto.MemberReportReq;

public interface MemberReportService {
	Long saveMemberReport(String email, MemberReportReq req);
}
