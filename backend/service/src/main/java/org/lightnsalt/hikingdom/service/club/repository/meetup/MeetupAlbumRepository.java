package org.lightnsalt.hikingdom.service.club.repository.meetup;

import java.util.List;

import org.lightnsalt.hikingdom.domain.entity.club.meetup.MeetupAlbum;
import org.springframework.data.jpa.repository.JpaRepository;
public interface MeetupAlbumRepository extends JpaRepository<MeetupAlbum, Long> {

	List<MeetupAlbum> findTop3ByMeetupIdOrderByCreatedAtDesc(Long meetupId);

	void deleteAllByMeetupId(Long meetupId);
}
