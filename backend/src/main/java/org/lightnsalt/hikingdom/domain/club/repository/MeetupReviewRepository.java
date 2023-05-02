package org.lightnsalt.hikingdom.domain.club.repository;

import org.lightnsalt.hikingdom.domain.club.entity.meetup.MeetupReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetupReviewRepository extends JpaRepository<MeetupReview, Long> {
}
