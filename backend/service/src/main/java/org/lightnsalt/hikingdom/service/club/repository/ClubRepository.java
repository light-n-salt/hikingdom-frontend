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
	Optional<Club> findByIdAndIsDeleted(@Param("id") Long id, @Param("is_deleted") boolean isDeleted);

	Optional<Club> findByNameAndIsDeleted(@Param("name") String name, @Param("is_deleted") boolean isDeleted);

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("UPDATE Club c "
		+ "SET c.name = :name, c.description = :description, c.baseAddress = :baseAddress, c.modifiedAt = :now "
		+ "WHERE c.id = :clubId")
	int updateClubProfile(Long clubId, String name, String description, BaseAddressInfo baseAddress, LocalDateTime now);

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("UPDATE Club c "
		+ "SET c.totalMemberCount = :totalMemberCount, c.modifiedAt = :now "
		+ "WHERE c.id = :clubId")
	void updateClubMemberCount(Long clubId, Long totalMemberCount, LocalDateTime now);
}
