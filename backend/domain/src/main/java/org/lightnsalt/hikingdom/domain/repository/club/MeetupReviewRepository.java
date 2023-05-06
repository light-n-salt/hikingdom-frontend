package org.lightnsalt.hikingdom.domain.repository.club;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.lightnsalt.hikingdom.domain.entity.club.meetup.MeetupReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MeetupReviewRepository extends JpaRepository<MeetupReview, Long> {
	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("UPDATE MeetupReview m "
		+ "SET m.isDeleted = true, m.deletedAt = :now "
		+ "WHERE m.id = :id")
	int deleteMeetupReviewById(Long id, LocalDateTime now);

	Optional<MeetupReview> findByIdAndIsDeleted(Long id, boolean isDeleted);

	List<MeetupReview> findByMeetupId(Long meetupId);
}
