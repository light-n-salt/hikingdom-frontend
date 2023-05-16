package org.lightnsalt.hikingdom.chat.repository.mysql;

import java.util.List;

import org.lightnsalt.hikingdom.chat.dto.response.MemberRes;
import org.lightnsalt.hikingdom.domain.entity.club.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
	@Query("SELECT new org.lightnsalt.hikingdom.chat.dto.response.MemberRes("
		+ "m.id, m.nickname, m.profileUrl, m.level.id) "
		+ "FROM ClubMember cb "
		+ "LEFT JOIN Member m "
		+ "ON cb.member.id = m.id "
		+ "WHERE cb.club.id = :clubId AND cb.isWithdraw = false")
	List<MemberRes> findByClubId(Long clubId);
}
