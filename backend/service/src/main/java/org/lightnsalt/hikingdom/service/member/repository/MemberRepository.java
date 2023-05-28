package org.lightnsalt.hikingdom.service.member.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.lightnsalt.hikingdom.domain.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

	boolean existsByEmail(@Param("email") String email);

	boolean existsByNickname(@Param("nickname") String nickname);

	Optional<Member> findByEmail(@Param("email") String email);

	Optional<Member> findByNickname(@Param("nickname") String nickname);

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("UPDATE Member m SET m.password = :password WHERE m.id = :id")
	int updatePasswordById(@Param("password") String password, @Param("id") Long id);

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("UPDATE Member m SET m.nickname = :nickname WHERE m.id = :id")
	void updateNicknameById(@Param("nickname") String nickname, @Param("id") Long id);

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("UPDATE Member m SET m.profileUrl = :profileUrl WHERE m.id = :id")
	void updateProfileUrlById(@Param("profileUrl") String profileUrl, @Param("id") Long id);

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("UPDATE Member m SET m.isWithdraw = :isWithdraw, m.withdrawAt = :now WHERE m.id = :id")
	void updateIsWithdrawAndWithdrawAtById(@Param("isWithdraw") boolean isWithdraw, @Param("now") LocalDateTime now,
		@Param("id") Long id);
}
