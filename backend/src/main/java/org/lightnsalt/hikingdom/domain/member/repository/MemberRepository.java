package org.lightnsalt.hikingdom.domain.member.repository;

import java.util.Optional;

import org.lightnsalt.hikingdom.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
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
}
