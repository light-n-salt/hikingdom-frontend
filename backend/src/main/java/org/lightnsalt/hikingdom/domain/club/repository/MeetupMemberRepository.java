package org.lightnsalt.hikingdom.domain.club.repository;

import java.util.List;

import org.lightnsalt.hikingdom.domain.club.entity.meetup.MeetupMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetupMemberRepository extends JpaRepository<MeetupMember, Long> {
	int countByMeetupId(Long id);

	boolean existsByMeetupIdAndMemberId(Long meetupId, Long memberId);

	List<MeetupMember> findByMeetupId(Long meetupId);
}
