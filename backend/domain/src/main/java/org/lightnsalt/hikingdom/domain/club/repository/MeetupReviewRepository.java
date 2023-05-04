package org.lightnsalt.hikingdom.domain.club.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.lightnsalt.hikingdom.domain.club.dto.response.MeetupReviewRes;
import org.lightnsalt.hikingdom.entity.club.meetup.MeetupReview;
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

	@Query(
		"SELECT new org.lightnsalt.hikingdom.domain.club.dto.response.MeetupReviewRes(m.id, m.nickname, m.profileUrl, m.level.id, r.id, r.content)"
			+ "FROM Member m LEFT JOIN MeetupReview r ON m.id = r.member.id "
			+ "WHERE r.meetup.id = :meetupId AND r.meetup.isDeleted = false")
	List<MeetupReviewRes> selectMeetupReviewAsMeetupReviewRes(Long meetupId);

	List<MeetupReview> findByMeetupId(Long meetupId);
}
