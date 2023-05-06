package org.lightnsalt.hikingdom.domain.repository.club;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.lightnsalt.hikingdom.domain.entity.club.meetup.MeetupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MeetupMemberRepository extends JpaRepository<MeetupMember, Long> {
	int countByMeetupId(Long id);

	boolean existsByMeetupIdAndMemberId(Long meetupId, Long memberId);

	Optional<MeetupMember> findByMeetupIdAndMemberId(Long meetupId, Long memberId);

	List<MeetupMember> findByMeetupId(Long meetupId);

	List<MeetupMember> findTop6ByMeetupId(Long meetupId);

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("UPDATE MeetupMember m "
		+ "SET m.withdrawAt = :now, m.isWithdraw = :isWithdraw "
		+ "WHERE m.meetup.id = :meetupId")
	void updateMeetupMemberIsWithdrawByMeetupId(Long meetupId, boolean isWithdraw, LocalDateTime now);
}
