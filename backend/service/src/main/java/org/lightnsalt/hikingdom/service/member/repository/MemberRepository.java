package org.lightnsalt.hikingdom.service.member.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.lightnsalt.hikingdom.domain.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByEmail(@Param("email") String email);

	boolean existsByEmail(@Param("email") String email);

	boolean existsByNickname(@Param("nickname") String nickname);

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("UPDATE Member m SET m.password = :password WHERE m.id = :id")
	int setPasswordById(@Param("password") String password, @Param("id") Long id);

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("UPDATE Member m SET m.nickname = :nickname WHERE m.id = :id")
	int setNicknameById(@Param("nickname") String nickname, @Param("id") Long id);

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("UPDATE Member m SET m.profileUrl = :profileUrl WHERE m.id = :id")
	void setProfileUrlById(String profileUrl, Long id);

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("UPDATE Member m " + "SET m.withdrawAt = :now, m.isWithdraw = :isWithdraw " + "WHERE m.id = :id")
	void updateMemberWithdraw(@Param("id") Long id, @Param("isWithdraw") boolean isWithdraw,
		@Param("now") LocalDateTime now);

	Optional<Member> findByNickname(@Param("nickname") String nickname);
}
