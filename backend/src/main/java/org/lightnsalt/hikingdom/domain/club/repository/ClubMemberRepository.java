package org.lightnsalt.hikingdom.domain.club.repository;

import java.util.Optional;

import org.lightnsalt.hikingdom.domain.club.entity.ClubMember;
import org.lightnsalt.hikingdom.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.lettuce.core.dynamic.annotation.Param;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
	boolean existsByMemberId(@Param("member_id") Long memberId);

	Optional<ClubMember> findByClubIdAndMemberId(Long clubId, Long memberId);

	@Query("SELECT c FROM ClubMember c WHERE c.member =:member AND c.isWithdraw=false")
	Optional<ClubMember> findCurrentClubByMember(Member member);
}
