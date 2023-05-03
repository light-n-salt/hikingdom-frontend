package org.lightnsalt.hikingdom.domain.club.service;

import java.util.List;
import java.util.stream.Collectors;

import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.common.error.GlobalException;
import org.lightnsalt.hikingdom.domain.club.dto.response.MemberListRes;
import org.lightnsalt.hikingdom.domain.club.entity.meetup.Meetup;
import org.lightnsalt.hikingdom.domain.club.entity.meetup.MeetupMember;
import org.lightnsalt.hikingdom.domain.club.repository.MeetupMemberRepository;
import org.lightnsalt.hikingdom.domain.club.repository.MeetupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MeetupMemberServiceImpl implements MeetupMemberService {
	private final MeetupRepository meetupRepository;
	private final MeetupMemberRepository meetupMemberRepository;

	@Override
	@Transactional
	public List<MemberListRes> findMeetupMember(Long clubId, Long meetupId) {
		// 존재하는 일정인지 확인
		final Meetup meetup = meetupRepository.findById(meetupId)
			.orElseThrow(() -> new GlobalException(ErrorCode.MEETUP_NOT_FOUND));

		// 소모임에 포함된 일정인지 확인
		if (!clubId.equals(meetup.getClub().getId())) {
			throw new GlobalException(ErrorCode.CLUB_MEETUP_NOT_FOUND);
		}
		// 일정 멤버 조회
		List<MeetupMember> list = meetupMemberRepository.findByMeetupId(meetupId);
		// 형 변환
		return list.stream()
			.map((meetupMember) -> new MemberListRes(meetupMember.getMember()))
			.collect(Collectors.toList());
	}
}
