package org.lightnsalt.hikingdom.service.club.repository.meetup;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.lightnsalt.hikingdom.domain.entity.club.meetup.MeetupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MeetupMemberRepository extends JpaRepository<MeetupMember, Long> {
	int countByMeetupIdAndIsWithdraw(Long id, boolean isWithdraw);

	boolean existsByMeetupIdAndMemberIdAndIsWithdraw(Long meetupId, Long memberId, boolean isWithdraw);

	Optional<MeetupMember> findByMeetupIdAndMemberIdAndIsWithdraw(Long meetupId, Long memberId, boolean isWithdraw);

	List<MeetupMember> findByMeetupIdAndIsWithdraw(Long meetupId, boolean isWithdraw);

	List<MeetupMember> findTop6ByMeetupIdAndIsWithdraw(Long meetupId, boolean isWithdraw);

	@Query("SELECT m FROM MeetupMember m JOIN FETCH m.member JOIN FETCH m.meetup "
		+ "WHERE m.member.id = :memberId AND m.meetup.startAt >= :startAt AND m.isWithdraw = :isWithdraw")
	List<MeetupMember> findByMemberIdAndStartAtAfterAndIsWithdraw(Long memberId, LocalDateTime startAt, boolean isWithdraw);

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("UPDATE MeetupMember m "
		+ "SET m.withdrawAt = :now, m.isWithdraw = :isWithdraw "
		+ "WHERE m.meetup.id = :meetupId")
	void updateMeetupMemberIsWithdrawByMeetupId(Long meetupId, boolean isWithdraw, LocalDateTime now);

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("UPDATE MeetupMember m "
		+ "SET m.withdrawAt = :now, m.isWithdraw = :isWithdraw "
		+ "WHERE m in :meetupMembers")
	void updateMeetupMemberIsWithdraw(List<MeetupMember> meetupMembers, boolean isWithdraw, LocalDateTime now);

}
