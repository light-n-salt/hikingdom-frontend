package org.lightnsalt.hikingdom.domain.repository.info;

import java.time.LocalDate;

import org.lightnsalt.hikingdom.domain.entity.info.ClubDailyInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubDailyInfoRepository extends JpaRepository<ClubDailyInfo, Long> {
	ClubDailyInfo findBySetDate(LocalDate now);
}
