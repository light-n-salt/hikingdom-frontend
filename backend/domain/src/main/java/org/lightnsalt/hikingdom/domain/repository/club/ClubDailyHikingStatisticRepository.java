package org.lightnsalt.hikingdom.domain.repository.club;

import java.time.LocalDateTime;
import java.util.List;

import org.lightnsalt.hikingdom.domain.entity.club.record.ClubDailyHikingStatistic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubDailyHikingStatisticRepository extends JpaRepository<ClubDailyHikingStatistic, Long> {
	List<ClubDailyHikingStatistic> findByDate(LocalDateTime date);
}
