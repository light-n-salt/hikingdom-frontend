package org.lightnsalt.hikingdom.service.club.repository.record;

import org.lightnsalt.hikingdom.domain.entity.club.record.ClubRanking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRankingRepository extends JpaRepository<ClubRanking, Long> {
	ClubRanking findTop1ByClubIdOrderBySetDate(Long id);
}
