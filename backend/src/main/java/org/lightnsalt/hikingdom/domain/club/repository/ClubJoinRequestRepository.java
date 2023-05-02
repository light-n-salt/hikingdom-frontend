package org.lightnsalt.hikingdom.domain.club.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.lightnsalt.hikingdom.domain.club.entity.Club;
import org.lightnsalt.hikingdom.domain.club.entity.ClubJoinRequest;
import org.lightnsalt.hikingdom.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ClubJoinRequestRepository extends JpaRepository<ClubJoinRequest, Long> {
	@Query("SELECT c FROM ClubJoinRequest c "
		+ "WHERE c.member= :member AND c.club = :club AND c.status = 'PENDING'")
	Optional<ClubJoinRequest> findPendingRequestByMemberAndClub(Member member, Club club);

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("UPDATE ClubJoinRequest c SET c.status = 'RETRACTED', c.modifiedAt = :now "
		+ "WHERE c.member = :member AND c.club = :club AND  c.status = 'PENDING'")
	void retractPendingJoinRequestByMemberAndClub(Member member, Club club, LocalDateTime now);

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("UPDATE ClubJoinRequest c SET c.status = 'RETRACTED', c.modifiedAt = :now "
		+ "WHERE c.member = :member AND  c.status = 'PENDING'")
	void retractPendingJoinRequestByMember(Member member, LocalDateTime now);

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("UPDATE ClubJoinRequest c SET c.status = 'ACCEPTED', c.modifiedAt = :now "
		+ "WHERE c.member = :member AND c.club = :club AND  c.status = 'PENDING'")
	void acceptPendingJoinRequestByMemberAndClub(Member member, Club club, LocalDateTime now);

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("UPDATE ClubJoinRequest c SET c.status = 'REJECTED', c.modifiedAt = :now "
		+ "WHERE c.member = :member AND c.club = :club AND  c.status = 'PENDING'")
	void rejectPendingJoinRequestByMemberAndClub(Member member, Club club, LocalDateTime now);
}
