package org.lightnsalt.hikingdom.domain.repository.info;

import java.util.List;
import java.util.Optional;

import org.lightnsalt.hikingdom.domain.entity.info.MountainInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MountainInfoRepository extends JpaRepository<MountainInfo, Long> {
	MountainInfo findByName(String name);

	Optional<MountainInfo> findById(Long id);

	// TODO: add query
	// @Query(value =
	// 	"SELECT *, ST_Distance_Sphere(Point(:lng,:lat), POINT(top_lng, top_lat)) AS diff_distance "
	// 		+ "FROM MountainInfo "
	// 		+ "WHERE ST_Distance_Sphere(Point(:lng,:lat), POINT(top_lng, top_lat)) <= :distance ", nativeQuery = true)
	// List<MountainInfoDto> findDistance(@Param("lat") double lat, @Param("lng") double lng,
	// 	@Param("distance") double distance);

	List<MountainInfo> findAllByNameContaining(String name);

}