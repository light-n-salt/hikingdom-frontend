package org.lightnsalt.hikingdom.domain.club.repository;

import org.lightnsalt.hikingdom.domain.club.entity.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;

import io.lettuce.core.dynamic.annotation.Param;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
	boolean existsByMemberId(@Param("member_id") Long memberId);
}
