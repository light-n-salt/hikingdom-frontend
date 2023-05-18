package org.lightnsalt.hikingdom.service.info.repository;

import java.util.List;
import java.util.Optional;

import org.lightnsalt.hikingdom.domain.entity.info.MountainInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MountainInfoRepository extends JpaRepository<MountainInfo, Long> {
	MountainInfo findByName(String name);

	Optional<MountainInfo> findById(Long id);

	@Query(value = "SELECT * "
		+ "FROM mountain_info AS m "
		+ "WHERE ST_Distance_Sphere(Point(:lng,:lat),POINT(m.top_lng, m.top_lat)) <= :distance "
		+ "ORDER BY ST_Distance_Sphere(Point(:lng,:lat),POINT(m.top_lng, m.top_lat)) <= :distance "
		+ "LIMIT 5 ", nativeQuery = true)
	List<MountainInfo> findByLocation(@Param("lng") double lng, @Param("lat") double lat,
		@Param("distance") double distance);
}