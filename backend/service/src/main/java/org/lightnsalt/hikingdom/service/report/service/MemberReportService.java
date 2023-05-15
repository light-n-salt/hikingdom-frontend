package org.lightnsalt.hikingdom.service.report.service;

import org.lightnsalt.hikingdom.service.report.dto.MemberReportReq;

public interface MemberReportService {
	Long saveMemberReport(String email, MemberReportReq req);
}
