package org.lightnsalt.hikingdom.service.info.repository;

import java.time.LocalDate;
import java.util.List;

import org.lightnsalt.hikingdom.domain.entity.info.MountainDailyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface MountainDailyInfoRepository extends JpaRepository<MountainDailyInfo, Long> {
	List<MountainDailyInfo> findBySetDate(@Param("setDate") LocalDate setDate);
}
