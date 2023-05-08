package org.lightnsalt.hikingdom.service.club.service;

import org.lightnsalt.hikingdom.common.dto.CustomSlice;
import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.common.error.GlobalException;
import org.lightnsalt.hikingdom.service.club.dto.response.MeetupAlbumRes;
import org.lightnsalt.hikingdom.domain.entity.club.meetup.MeetupAlbum;
import org.lightnsalt.hikingdom.domain.repository.club.ClubMemberRepository;
import org.lightnsalt.hikingdom.domain.repository.club.MeetupAlbumRepositoryCustom;
import org.lightnsalt.hikingdom.domain.repository.member.MemberRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClubPhotoServiceImpl implements ClubPhotoService {
	private final MemberRepository memberRepository;
	private final ClubMemberRepository clubMemberRepository;
	private final MeetupAlbumRepositoryCustom meetupRepositoryCustom;

	@Transactional
	@Override
	public CustomSlice<MeetupAlbumRes> findClubPhotoList(String email, Long clubId, Long photoId, Pageable pageable) {
		final Long memberId = memberRepository.findByEmailAndIsWithdraw(email, false)
			.orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_UNAUTHORIZED)).getId();

		// 소모임 가입 여부 확인
		if (clubMemberRepository.findByClubIdAndMemberIdAndIsWithdraw(clubId, memberId, false).isEmpty())
			throw new GlobalException(ErrorCode.CLUB_MEMBER_UNAUTHORIZED);

		Slice<MeetupAlbum> list = meetupRepositoryCustom.findPhotosByClubId(photoId, clubId, pageable);
		return new CustomSlice<>(
			list.map(meetupAlbum -> new MeetupAlbumRes(meetupAlbum, meetupAlbum.getMember().getId().equals(memberId))));
	}
}
