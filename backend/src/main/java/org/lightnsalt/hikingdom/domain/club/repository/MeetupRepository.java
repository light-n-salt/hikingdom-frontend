package org.lightnsalt.hikingdom.domain.club.repository;

import java.util.List;

import org.lightnsalt.hikingdom.domain.club.entity.meetup.Meetup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MeetupRepository extends JpaRepository<Meetup, Long> {
	@Query(value = "SELECT m "
		+ "FROM Meetup m "
		+ "WHERE m.club.id = :clubId "
		+ "AND YEAR(m.startAt) = :year "
		+ "AND MONTH(m.startAt) = :month ")
	List<Meetup> findByClubIdAndStartAt(@Param("clubId") Long clubId, @Param("year") int year,
		@Param("month") int month);
}