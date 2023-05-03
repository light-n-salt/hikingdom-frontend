package org.lightnsalt.hikingdom.domain.club.service;

import org.lightnsalt.hikingdom.common.dto.CustomSlice;
import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.common.error.GlobalException;
import org.lightnsalt.hikingdom.domain.club.dto.response.MeetupAlbumRes;
import org.lightnsalt.hikingdom.domain.club.entity.meetup.MeetupAlbum;
import org.lightnsalt.hikingdom.domain.club.repository.ClubMemberRepository;
import org.lightnsalt.hikingdom.domain.club.repository.MeetupAlbumRepositoryCustom;
import org.lightnsalt.hikingdom.domain.member.entity.Member;
import org.lightnsalt.hikingdom.domain.member.repository.MemberRepository;
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
		final Member member = memberRepository.findByEmailAndIsWithdraw(email, false)
			.orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_UNAUTHORIZED));

		// 소모임 가입 여부 확인
		if (clubMemberRepository.findByClubIdAndMemberIdAndIsWithdraw(clubId, member.getId(), false).isEmpty())
			throw new GlobalException(ErrorCode.CLUB_MEMBER_UNAUTHORIZED);

		Slice<MeetupAlbum> list = meetupRepositoryCustom.findPhotosByClubId(photoId, clubId, pageable);
		return new CustomSlice<>(list.map(MeetupAlbumRes::new));
	}
}
