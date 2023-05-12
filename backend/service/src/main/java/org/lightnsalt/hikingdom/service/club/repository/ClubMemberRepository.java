package org.lightnsalt.hikingdom.service.club.repository;

import java.util.List;
import java.util.Optional;

import org.lightnsalt.hikingdom.domain.entity.club.ClubMember;
import org.lightnsalt.hikingdom.service.member.dto.response.MemberInfoRes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
	boolean existsByClubIdAndMemberId(@Param("club_id") Long clubId, @Param("member_id") Long memberId);

	Optional<ClubMember> findByMemberId(@Param("member_id") Long memberId);

	Optional<ClubMember> findByClubIdAndMemberId(@Param("club_id") Long clubId, @Param("member_id") Long memberId);

	List<ClubMember> findByClubId(@Param("clubId") Long clubId);

	@Query("SELECT new org.lightnsalt.hikingdom.service.member.dto.response.MemberInfoRes("
		+ "m.id, m.nickname, m.profileUrl, m.level.id) "
		+ "FROM ClubMember cb "
		+ "LEFT JOIN Member m "
		+ "ON cb.member.id = m.id "
		+ "WHERE cb.club.id = :clubId AND cb.isWithdraw = false")
	List<MemberInfoRes> findByClubIdReturnMemberInfoRes(@Param("clubId") Long clubId);
}
