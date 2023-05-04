package org.lightnsalt.hikingdom.domain.club.repository;

import java.util.List;
import java.util.Optional;

import org.lightnsalt.hikingdom.entity.club.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
	boolean existsByClubIdAndMemberIdAndIsWithdraw(@Param("club_id") Long clubId,
		@Param("member_id") Long memberId, @Param("is_withdraw") boolean isWithdraw);

	Optional<ClubMember> findByMemberIdAndIsWithdraw(@Param("member_id") Long memberId,
		@Param("is_withdraw") boolean isWithdraw);

	Optional<ClubMember> findByClubIdAndMemberIdAndIsWithdraw(@Param("club_id") Long clubId,
		@Param("member_id") Long memberId, @Param("is_withdraw") boolean isWithdraw);

	List<ClubMember> findByClubIdAndIsWithdraw(Long clubId, boolean isWithdraw);
}
