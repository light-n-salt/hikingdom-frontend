package org.lightnsalt.hikingdom.domain.club.repository;

import org.lightnsalt.hikingdom.domain.club.entity.meetup.Meetup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetupRepository extends JpaRepository<Meetup, Long> {
}