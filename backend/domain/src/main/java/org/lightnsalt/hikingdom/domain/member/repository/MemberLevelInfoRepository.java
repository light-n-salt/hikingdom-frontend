package org.lightnsalt.hikingdom.domain.member.repository;

import org.lightnsalt.hikingdom.domain.member.entity.MemberLevelInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberLevelInfoRepository extends JpaRepository<MemberLevelInfo, Integer> {

}
