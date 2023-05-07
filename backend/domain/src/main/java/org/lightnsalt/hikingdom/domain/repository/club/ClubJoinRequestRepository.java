package org.lightnsalt.hikingdom.domain.repository.club;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.lightnsalt.hikingdom.domain.common.enumType.JoinRequestStatusType;
import org.lightnsalt.hikingdom.domain.entity.club.Club;
import org.lightnsalt.hikingdom.domain.entity.club.ClubJoinRequest;
import org.lightnsalt.hikingdom.domain.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClubJoinRequestRepository extends JpaRepository<ClubJoinRequest, Long> {
	Optional<ClubJoinRequest> findByMemberIdAndClubIdAndStatus(@Param("member_id") Long memberId,
		@Param("club_id") Long clubId, @Param("status") JoinRequestStatusType status);

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("UPDATE ClubJoinRequest c SET c.status = :status, c.modifiedAt = :now "
		+ "WHERE c.member = :member AND  c.status = 'PENDING'")
	void updatePendingJoinRequestByMember(Member member, JoinRequestStatusType status, LocalDateTime now);

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("UPDATE ClubJoinRequest c SET c.status = :status, c.modifiedAt = :now "
		+ "WHERE c.member = :member AND c.club = :club AND  c.status = 'PENDING'")
	int updatePendingJoinRequestByMemberAndClub(Member member, Club club, JoinRequestStatusType status,
		LocalDateTime now);

	List<ClubJoinRequest> findByClubIdAndMemberIsWithdrawAndStatus(Long clubId, boolean isWithdraw,
		JoinRequestStatusType status);

	List<ClubJoinRequest> findByMemberIdAndStatusAndClubIsDeleted(Long memberId, JoinRequestStatusType status,
		boolean isWithdraw);

}
