package org.lightnsalt.hikingdom.service.member.repository;

import org.lightnsalt.hikingdom.domain.entity.member.MemberHikingStatistic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberHikingStatisticRepository extends JpaRepository<MemberHikingStatistic, Long> {
}