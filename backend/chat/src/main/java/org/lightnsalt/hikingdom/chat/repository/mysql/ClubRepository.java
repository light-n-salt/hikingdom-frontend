package org.lightnsalt.hikingdom.chat.repository.mysql;

import org.lightnsalt.hikingdom.domain.entity.club.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Long> {
}
