package org.lightnsalt.hikingdom.domain.repository.member;

import java.util.List;

import org.lightnsalt.hikingdom.domain.entity.hiking.MemberHiking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberHikingRepository extends JpaRepository<MemberHiking, Long> {
	List<MemberHiking> findTop3ByMemberIdOrderByEndAt(Long id);
}
