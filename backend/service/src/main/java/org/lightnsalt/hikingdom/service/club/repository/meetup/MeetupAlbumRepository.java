package org.lightnsalt.hikingdom.service.club.repository.meetup;

import java.time.LocalDateTime;
import java.util.List;

import org.lightnsalt.hikingdom.domain.entity.club.meetup.MeetupAlbum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MeetupAlbumRepository extends JpaRepository<MeetupAlbum, Long> {
	boolean existsByIdAndIsDeleted(Long photoId, boolean isDeleted);

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query(value = "UPDATE MeetupAlbum m "
		+ "SET m.isDeleted = TRUE, m.deletedAt = :now "
		+ "WHERE m.id = :photoId ")
	void updateIsDeleted(Long photoId, LocalDateTime now);

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("UPDATE MeetupAlbum m "
		+ "SET m.isDeleted = :isDeleted, m.deletedAt = :now "
		+ "WHERE m.meetup.id = :meetupId")
	void updateMeetupAlbumIsDeletedByMeetupId(Long meetupId, boolean isDeleted, LocalDateTime now);

	List<MeetupAlbum> findTop3ByMeetupIdAndIsDeletedOrderByCreatedAtDesc(Long meetupId, boolean isDeleted);
}
