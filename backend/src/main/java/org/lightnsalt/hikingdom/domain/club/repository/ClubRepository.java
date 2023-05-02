package org.lightnsalt.hikingdom.domain.club.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.lightnsalt.hikingdom.domain.club.entity.Club;
import org.lightnsalt.hikingdom.domain.info.entity.BaseAddressInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClubRepository extends JpaRepository<Club, Long> {
	Optional<Club> findByIdAndIsDeleted(@Param("id") Long id, @Param("is_deleted") boolean isDeleted);

	Optional<Club> findByNameAndIsDeleted(@Param("name") String name, @Param("is_deleted") boolean isDeleted);

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("UPDATE Club c "
		+ "SET c.name = :name, c.description = :description, c.baseAddress = :baseAddress, c.modifiedAt = :now "
		+ "WHERE c.id = :id")
	int updateClub(String name, String description, BaseAddressInfo baseAddress, Long id, LocalDateTime now);
}
