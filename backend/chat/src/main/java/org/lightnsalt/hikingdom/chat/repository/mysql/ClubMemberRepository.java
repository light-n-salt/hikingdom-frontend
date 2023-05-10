package org.lightnsalt.hikingdom.chat.repository.mysql;

import org.lightnsalt.hikingdom.domain.entity.club.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
}
