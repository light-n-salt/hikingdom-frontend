package org.lightnsalt.hikingdom.domain.report.repository;

import org.lightnsalt.hikingdom.entity.report.MemberReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberReportRepository extends JpaRepository<MemberReport, Long> {
}
