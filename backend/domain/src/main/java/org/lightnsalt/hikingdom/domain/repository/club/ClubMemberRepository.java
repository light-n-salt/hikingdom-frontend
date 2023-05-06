package org.lightnsalt.hikingdom.domain.repository.club;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.lightnsalt.hikingdom.domain.entity.club.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
	boolean existsByClubIdAndMemberIdAndIsWithdraw(@Param("club_id") Long clubId,
		@Param("member_id") Long memberId, @Param("is_withdraw") boolean isWithdraw);

	Optional<ClubMember> findByMemberIdAndIsWithdraw(@Param("member_id") Long memberId,
		@Param("is_withdraw") boolean isWithdraw);

	Optional<ClubMember> findByClubIdAndMemberIdAndIsWithdraw(@Param("club_id") Long clubId,
		@Param("member_id") Long memberId, @Param("is_withdraw") boolean isWithdraw);

	List<ClubMember> findByClubIdAndIsWithdraw(Long clubId, boolean isWithdraw);

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("UPDATE ClubMember c "
		+ "SET c.withdrawAt = :now, c.isWithdraw = :isWithdraw "
		+ "WHERE c.id = :id")
	void updateClubMemberWithdraw(Long id, boolean isWithdraw, LocalDateTime now);
}
