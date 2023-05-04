package org.lightnsalt.hikingdom.domain.info.dto.repository;

import java.time.LocalDate;
import java.util.List;

import org.lightnsalt.hikingdom.entity.info.MountainDailyInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MountainDailyInfoRepository extends JpaRepository<MountainDailyInfo, Long> {
	List<MountainDailyInfo> findBySetDate(LocalDate setDate);
}
