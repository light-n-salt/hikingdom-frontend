package org.lightnsalt.hikingdom.chat.repository.mysql;

import org.lightnsalt.hikingdom.domain.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
