package org.lightnsalt.hikingdom.domain.member.repository;

import org.lightnsalt.hikingdom.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	boolean existsByEmail(@Param("email") String email);
	boolean existsByNickname(@Param("nickname") String nickname);
}
