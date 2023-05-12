package org.lightnsalt.hikingdom.service.club.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.lightnsalt.hikingdom.domain.entity.club.Club;
import org.lightnsalt.hikingdom.domain.entity.info.BaseAddressInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClubRepository extends JpaRepository<Club, Long> {

	Optional<Club> findByName(@Param("name") String name);

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("UPDATE Club c "
		+ "SET c.name = :name, c.description = :description, c.baseAddress = :baseAddress, c.modifiedAt = :now "
		+ "WHERE c.id = :clubId")
	int updateClubProfile(@Param("clubId") Long clubId, @Param("name") String name,
		@Param("description") String description, @Param("baseAddress") BaseAddressInfo baseAddress,
		@Param("now") LocalDateTime now);

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("UPDATE Club c "
		+ "SET c.totalMemberCount = :totalMemberCount, c.modifiedAt = :now "
		+ "WHERE c.id = :clubId")
	void updateClubMemberCount(@Param("clubId") Long clubId, @Param("totalMemberCount") Long totalMemberCount,
		@Param("now") LocalDateTime now);
}
