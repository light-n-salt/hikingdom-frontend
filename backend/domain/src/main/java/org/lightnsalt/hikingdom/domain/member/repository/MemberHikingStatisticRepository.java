package org.lightnsalt.hikingdom.domain.member.repository;

import org.lightnsalt.hikingdom.domain.member.entity.MemberHikingStatistic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberHikingStatisticRepository extends JpaRepository<MemberHikingStatistic, Long> {
}