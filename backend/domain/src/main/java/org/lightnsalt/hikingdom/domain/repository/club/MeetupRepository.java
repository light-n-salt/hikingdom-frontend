package org.lightnsalt.hikingdom.domain.repository.club;

import java.time.LocalDateTime;
import java.util.List;

import org.lightnsalt.hikingdom.domain.entity.club.meetup.Meetup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MeetupRepository extends JpaRepository<Meetup, Long> {
	@Query(value = "SELECT m "
		+ "FROM Meetup m "
		+ "WHERE m.club.id = :clubId "
		+ "AND YEAR(m.startAt) = :year "
		+ "AND MONTH(m.startAt) = :month "
		+ "AND m.isDeleted = false")
	List<Meetup> findByClubIdAndStartMonth(@Param("clubId") Long clubId, @Param("year") int year,
		@Param("month") int month);

	@Query(value = "SELECT m "
		+ "FROM Meetup m "
		+ "WHERE m.club.id = :clubId "
		+ "AND YEAR(m.startAt) = :year "
		+ "AND MONTH(m.startAt) = :month "
		+ "AND DAYOFMONTH(m.startAt) = :date "
		+ "AND m.isDeleted = false")
	List<Meetup> findByClubIdAndStartDay(@Param("clubId") Long clubId, @Param("year") int year,
		@Param("month") int month, @Param("date") int date);

	@Query("SELECT m FROM Meetup m "
		+ "WHERE m.club.id = :clubId AND m.host.id = :hostId AND m.startAt > :startAt AND m.isDeleted = false")
	List<Meetup> findByClubIdAndHostIdAndStartAtAfter(Long clubId, Long hostId, LocalDateTime startAt);
}