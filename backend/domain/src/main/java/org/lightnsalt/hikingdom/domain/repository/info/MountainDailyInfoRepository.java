package org.lightnsalt.hikingdom.domain.repository.info;

import java.time.LocalDate;

import org.lightnsalt.hikingdom.domain.entity.info.MountainDailyInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MountainDailyInfoRepository extends JpaRepository<MountainDailyInfo, Long> {
	MountainDailyInfo findBySetDate(LocalDate setDate);
}
