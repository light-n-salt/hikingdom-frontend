package org.lightnsalt.hikingdom.service.hiking.repository;

import java.util.List;

import org.lightnsalt.hikingdom.domain.entity.hiking.MemberHiking;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberHikingRepository extends JpaRepository<MemberHiking, Long> {
	List<MemberHiking> findAllByMemberIdOrderByEndAtDescStartAtDesc(Long id, Pageable pageable);

}
