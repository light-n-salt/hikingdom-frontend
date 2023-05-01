package org.lightnsalt.hikingdom.domain.info.repository;

import java.util.List;
import java.util.Optional;

import org.lightnsalt.hikingdom.domain.info.dto.repository.MountainInfoDtoInterface;
import org.lightnsalt.hikingdom.domain.info.entity.MountainInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.lettuce.core.dynamic.annotation.Param;

public interface MountainInfoRepository extends JpaRepository<MountainInfo, Long> {
	MountainInfo findByName(String name);

	Optional<MountainInfo> findById(Long id);

	@Query(value =
		"SELECT mountainInfo,  ST_Distance_Sphere(Point(:lat,:lng),POINT(mountainInfo.topLat, mountainInfo.topLng)) AS distance "
			+ "FROM MountainInfo AS mountainInfo "
			+ "WHERE ST_Distance_Sphere(Point(:lat,:lng),POINT(mountainInfo.topLat, mountainInfo.topLng)) <= :distance",
		nativeQuery = true)
	List<MountainInfoDtoInterface> findByDistance(@Param("lat") double lat, @Param("lng") double lng, @Param("distance")
	double distance);

	List<MountainInfo> findAllByName(String name);
}
