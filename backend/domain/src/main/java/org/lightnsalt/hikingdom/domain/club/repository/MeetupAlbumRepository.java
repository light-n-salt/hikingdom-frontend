package org.lightnsalt.hikingdom.domain.club.repository;

import java.util.List;

import org.lightnsalt.hikingdom.entity.club.meetup.MeetupAlbum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MeetupAlbumRepository extends JpaRepository<MeetupAlbum, Long> {
	boolean existsByIdAndIsDeleted(Long photoId, boolean isDeleted);

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query(value = "UPDATE MeetupAlbum m "
		+ "SET m.isDeleted = TRUE "
		+ "WHERE m.id = :photoId ")
	void updateIsDeleted(@Param("photoId") Long photoId);

	List<MeetupAlbum> findTop3ByMeetupIdOrderByCreatedAtDesc(Long meetupId);
}
