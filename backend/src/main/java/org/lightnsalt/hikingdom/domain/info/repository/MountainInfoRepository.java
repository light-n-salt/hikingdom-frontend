package org.lightnsalt.hikingdom.domain.info.repository;

import java.util.Optional;

import org.lightnsalt.hikingdom.domain.info.entity.MountainInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MountainInfoRepository extends JpaRepository<MountainInfo, Long> {
	MountainInfo findByName(String name);

	Optional<MountainInfo> findById(Long id);
}
