package org.lightnsalt.hikingdom.domain.repository.report;

import org.lightnsalt.hikingdom.domain.entity.report.MemberReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberReportRepository extends JpaRepository<MemberReport, Long> {
}
