package org.lightnsalt.hikingdom.service.club.repository;

import java.util.List;

import org.lightnsalt.hikingdom.domain.entity.club.ClubAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClubAssetRepository extends JpaRepository<ClubAsset, Long> {
	@Query("SELECT c from ClubAsset c WHERE c.club.id = :clubId")
	List<ClubAsset> findAllByClubId(Long clubId);
}
