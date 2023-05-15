package org.lightnsalt.hikingdom.service.info.repository;

import java.time.LocalDate;

import org.lightnsalt.hikingdom.domain.entity.info.ClubDailyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ClubDailyInfoRepository extends JpaRepository<ClubDailyInfo, Long> {
	ClubDailyInfo findBySetDate(@Param("now") LocalDate now);
}
