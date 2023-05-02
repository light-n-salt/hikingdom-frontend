package org.lightnsalt.hikingdom.domain.club.service;

import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.common.error.GlobalException;
import org.lightnsalt.hikingdom.domain.club.dto.request.MeetupReviewReq;
import org.lightnsalt.hikingdom.domain.club.entity.Club;
import org.lightnsalt.hikingdom.domain.club.entity.meetup.Meetup;
import org.lightnsalt.hikingdom.domain.club.entity.meetup.MeetupReview;
import org.lightnsalt.hikingdom.domain.club.repository.ClubRepository;
import org.lightnsalt.hikingdom.domain.club.repository.MeetupMemberRepository;
import org.lightnsalt.hikingdom.domain.club.repository.MeetupRepository;
import org.lightnsalt.hikingdom.domain.club.repository.MeetupReviewRepository;
import org.lightnsalt.hikingdom.domain.member.entity.Member;
import org.lightnsalt.hikingdom.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MeetupReviewServiceImpl implements MeetupReviewService {
	private final ClubRepository clubRepository;
	private final MeetupRepository meetupRepository;
	private final MeetupMemberRepository meetupMemberRepository;
	private final MeetupReviewRepository meetupReviewRepository;
	private final MemberRepository memberRepository;

	@Transactional
	@Override
	public Long saveMeetupReview(String email, Long clubId, Long meetupId, MeetupReviewReq meetupReviewReq) {
		final Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_UNAUTHORIZED));
		final Club club = clubRepository.findById(clubId)
			.orElseThrow(() -> new GlobalException(ErrorCode.CLUB_NOT_FOUND));
		final Meetup meetup = meetupRepository.findById(meetupId)
			.orElseThrow(() -> new GlobalException(ErrorCode.MEETUP_NOT_FOUND));

		// 일정 참여 여부 확인
		if (meetupMemberRepository.existsByMeetupIdAndMemberId(meetupId, member.getId()))
			throw new GlobalException(ErrorCode.MEETUP_MEMBER_UNAUTHORIZED);

		MeetupReview meetupReview = MeetupReview.builder()
			.club(club)
			.meetup(meetup)
			.member(member)
			.content(meetupReviewReq.getContent())
			.build();

		meetupReviewRepository.save(meetupReview);

		return meetupReview.getId();
	}
}
