package org.lightnsalt.hikingdom.domain.repository.info;

import java.time.LocalDate;
import java.util.List;

import org.lightnsalt.hikingdom.domain.entity.info.MountainDailyInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MountainDailyInfoRepository extends JpaRepository<MountainDailyInfo, Long> {
	List<MountainDailyInfo> findBySetDate(LocalDate setDate);
}
