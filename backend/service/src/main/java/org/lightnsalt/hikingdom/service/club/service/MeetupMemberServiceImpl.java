package org.lightnsalt.hikingdom.service.club.service;

import java.util.List;
import java.util.stream.Collectors;

import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.common.error.GlobalException;
import org.lightnsalt.hikingdom.service.club.dto.response.MemberListRes;
import org.lightnsalt.hikingdom.domain.entity.club.meetup.Meetup;
import org.lightnsalt.hikingdom.domain.entity.club.meetup.MeetupMember;
import org.lightnsalt.hikingdom.domain.repository.club.ClubMemberRepository;
import org.lightnsalt.hikingdom.domain.repository.club.MeetupMemberRepository;
import org.lightnsalt.hikingdom.domain.repository.club.MeetupRepository;
import org.lightnsalt.hikingdom.domain.entity.member.Member;
import org.lightnsalt.hikingdom.domain.repository.member.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MeetupMemberServiceImpl implements MeetupMemberService {
	private final ClubMemberRepository clubMemberRepository;
	private final MemberRepository memberRepository;
	private final MeetupRepository meetupRepository;
	private final MeetupMemberRepository meetupMemberRepository;

	@Override
	@Transactional
	public void addJoinMeetup(String email, Long clubId, Long meetupId) {
		final Member member = memberRepository.findByEmailAndIsWithdraw(email, false)
			.orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_UNAUTHORIZED));

		if (!clubMemberRepository.existsByClubIdAndMemberIdAndIsWithdraw(clubId, member.getId(), false))
			throw new GlobalException(ErrorCode.CLUB_MEMBER_UNAUTHORIZED);

		if (meetupMemberRepository.existsByMeetupIdAndMemberIdAndIsWithdraw(meetupId, member.getId(), false))
			throw new GlobalException(ErrorCode.MEETUP_ALREADY_JOINED);

		final Meetup meetup = meetupRepository.findByIdAndIsDeleted(meetupId, false)
			.orElseThrow(() -> new GlobalException(ErrorCode.MEETUP_NOT_FOUND));

		meetupMemberRepository.save(MeetupMember.builder()
			.meetup(meetup)
			.member(member)
			.build());
	}

	@Override
	@Transactional
	public void removeJoinMeetup(String email, Long clubId, Long meetupId) {
		final Member member = memberRepository.findByEmailAndIsWithdraw(email, false)
			.orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_UNAUTHORIZED));
		final MeetupMember meetupMember = meetupMemberRepository.findByMeetupIdAndMemberIdAndIsWithdraw(meetupId,
				member.getId(), false)
			.orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_UNAUTHORIZED));

		meetupMemberRepository.delete(meetupMember);
	}

	@Override
	@Transactional
	public List<MemberListRes> findMeetupMember(Long clubId, Long meetupId) {
		// 존재하는 일정인지 확인
		final Meetup meetup = meetupRepository.findByIdAndIsDeleted(meetupId, false)
			.orElseThrow(() -> new GlobalException(ErrorCode.MEETUP_NOT_FOUND));

		// 소모임에 포함된 일정인지 확인
		if (!clubId.equals(meetup.getClub().getId())) {
			throw new GlobalException(ErrorCode.CLUB_MEETUP_NOT_FOUND);
		}
		// 일정 멤버 조회
		List<MeetupMember> list = meetupMemberRepository.findByMeetupIdAndIsWithdraw(meetupId, false);
		// 형 변환
		return list.stream()
			.map((meetupMember) -> new MemberListRes(meetupMember.getMember()))
			.collect(Collectors.toList());
	}
}
