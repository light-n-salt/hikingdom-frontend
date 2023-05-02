package org.lightnsalt.hikingdom.domain.club.repository;

import java.util.List;

import org.lightnsalt.hikingdom.domain.club.entity.meetup.MeetupAlbum;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static org.lightnsalt.hikingdom.domain.club.entity.meetup.QMeetupAlbum.meetupAlbum;

@Repository
@RequiredArgsConstructor

public class MeetupAlbumRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	// TODO: 쿼리문 작성하기 = Slice를 이용한 무한 스크롤
	public List<MeetupAlbum> findPhotos(Long photoId, Long meetupId, Pageable pageable) {
		// List<MeetupAlbum> result = queryFactory.selectFrom(meetupAlbum)
		return null;
	}
}
