package org.lightnsalt.hikingdom.service.info.repository;

import org.lightnsalt.hikingdom.domain.entity.info.AssetInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetInfoRepository extends JpaRepository<AssetInfo, Long> {

	AssetInfo findByMountainId(Long mountainId);
}
