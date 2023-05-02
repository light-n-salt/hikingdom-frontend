package org.lightnsalt.hikingdom.domain.report.repository;

import org.lightnsalt.hikingdom.domain.report.entity.MemberReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberReportRepository extends JpaRepository<MemberReport, Long> {
}
