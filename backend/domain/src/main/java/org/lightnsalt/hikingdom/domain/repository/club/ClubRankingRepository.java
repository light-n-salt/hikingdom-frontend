package org.lightnsalt.hikingdom.domain.repository.club;

import org.lightnsalt.hikingdom.domain.entity.club.record.ClubRanking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRankingRepository extends JpaRepository<ClubRanking, Long> {
	ClubRanking findTop1ByClubIdOrderBySetDate(Long id);
}
