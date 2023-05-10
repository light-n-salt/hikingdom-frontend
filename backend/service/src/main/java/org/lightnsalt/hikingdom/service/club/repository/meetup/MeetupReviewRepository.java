package org.lightnsalt.hikingdom.service.club.repository.meetup;

import java.time.LocalDateTime;
import java.util.List;

import org.lightnsalt.hikingdom.domain.entity.club.meetup.MeetupReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MeetupReviewRepository extends JpaRepository<MeetupReview, Long> {
	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("UPDATE MeetupReview m "
		+ "SET m.isDeleted = :isDeleted, m.deletedAt = :now "
		+ "WHERE m.id = :id")
	int updateMeetupReviewIsDeletedById(@Param("id") Long id, @Param("isDeleted") boolean isDeleted,
		@Param("now") LocalDateTime now);

	List<MeetupReview> findByMeetupId(Long meetupId);

	void deleteAllByMeetupId(Long meetupId);
}
