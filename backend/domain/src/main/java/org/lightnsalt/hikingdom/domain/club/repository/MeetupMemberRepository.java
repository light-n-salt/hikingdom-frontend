package org.lightnsalt.hikingdom.domain.club.repository;

import java.util.List;
import java.util.Optional;

import org.lightnsalt.hikingdom.entity.club.meetup.MeetupMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetupMemberRepository extends JpaRepository<MeetupMember, Long> {
	int countByMeetupId(Long id);

	boolean existsByMeetupIdAndMemberId(Long meetupId, Long memberId);

	Optional<MeetupMember> findByMeetupIdAndMemberId(Long meetupId, Long memberId);

	List<MeetupMember> findByMeetupId(Long meetupId);

	List<MeetupMember> findTop6ByMeetupId(Long meetupId);
}
