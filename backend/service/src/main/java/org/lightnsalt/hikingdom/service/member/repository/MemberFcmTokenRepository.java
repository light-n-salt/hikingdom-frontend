package org.lightnsalt.hikingdom.service.member.repository;

import org.lightnsalt.hikingdom.domain.entity.notification.MemberFcmToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberFcmTokenRepository extends JpaRepository<MemberFcmToken, Long> {
	boolean existsByMemberIdAndBody(Long memberId, String body);

	void deleteAllByMemberId(Long memberId);
}
