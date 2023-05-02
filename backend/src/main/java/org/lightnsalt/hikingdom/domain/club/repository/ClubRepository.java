package org.lightnsalt.hikingdom.domain.club.repository;

import java.util.Optional;

import org.lightnsalt.hikingdom.domain.club.entity.Club;
import org.lightnsalt.hikingdom.domain.info.entity.BaseAddressInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClubRepository extends JpaRepository<Club, Long> {
	@Query("SELECT c FROM Club c WHERE c.name = :name AND c.isDeleted = false")
	Optional<Club> findByNameAndIsNotDeleted(@Param("name") String name);

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("UPDATE Club c set c.name = :name, c.description = :description, c.baseAddress = :baseAddress where c.id = :id")
	void updateClub(@Param("name") String name, @Param("description") String description,
		@Param("baseAddress") BaseAddressInfo baseAddressInfo, @Param("id") Long id);
}
