package org.lightnsalt.hikingdom.domain.club.repository;

import org.lightnsalt.hikingdom.domain.club.entity.meetup.MeetupAlbum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetupAlbumRepository extends JpaRepository<MeetupAlbum, Long> {
}
