package org.lightnsalt.hikingdom.service.club.repository.meetup;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.lightnsalt.hikingdom.domain.entity.club.meetup.MeetupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MeetupMemberRepository extends JpaRepository<MeetupMember, Long> {
	int countByMeetupId(Long id);

	boolean existsByMeetupIdAndMemberId(Long meetupId, Long memberId);

	Optional<MeetupMember> findByMeetupIdAndMemberId(Long meetupId, Long memberId);

	List<MeetupMember> findByMeetupId(Long meetupId);

	List<MeetupMember> findTop6ByMeetupId(Long meetupId);

	@Query("SELECT m FROM MeetupMember m JOIN FETCH m.member JOIN FETCH m.meetup "
		+ "WHERE m.member.id = :memberId AND m.meetup.startAt >= :startAt AND m.isWithdraw = false")
	List<MeetupMember> findByMemberIdAndStartAtAfter(@Param("memberId") Long memberId,@Param("startAt")  LocalDateTime startAt);

	void deleteByMeetupId(Long meetupId);
}
