package org.lightnsalt.hikingdom.domain.club.repository;

import java.util.List;

import org.lightnsalt.hikingdom.domain.club.entity.meetup.MeetupAlbum;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

import static org.lightnsalt.hikingdom.domain.club.entity.meetup.QMeetupAlbum.meetupAlbum;

@Repository
@RequiredArgsConstructor
public class MeetupAlbumRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	public Slice<MeetupAlbum> findPhotosByClubId(Long photoId, Long clubId, Pageable pageable) {
		List<MeetupAlbum> result = queryFactory.selectFrom(meetupAlbum)
			.where(isLast(photoId),
				meetupAlbum.club.id.eq(clubId),
				meetupAlbum.isDeleted.eq(false))
			.orderBy(meetupAlbum.createdAt.desc())
			.limit(pageable.getPageSize() + 1)
			.fetch();

		return checkLastPage(pageable, result);
	}

	// Slice를 이용한 무한 스크롤
	public Slice<MeetupAlbum> findPhotos(Long photoId, Long meetupId, Pageable pageable) {
		List<MeetupAlbum> result = queryFactory.selectFrom(meetupAlbum)
			.where(
				// no-offset 페이징 처리
				isLast(photoId),
				// 기타 조건
				meetupAlbum.meetup.id.eq(meetupId),
				meetupAlbum.isDeleted.eq(false)
			)
			.orderBy(meetupAlbum.createdAt.desc())
			.limit(pageable.getPageSize() + 1)
			.fetch();
		return checkLastPage(pageable, result);
	}

	// no-offset 방식 처리하는 메서드
	private BooleanExpression isLast(Long photoId) {
		if (photoId == null) {
			return null;
		}
		return meetupAlbum.id.lt(photoId);
	}

	// 무한 스크롤 방식 처리하는 메서드
	private Slice<MeetupAlbum> checkLastPage(Pageable pageable, List<MeetupAlbum> result) {
		boolean hasNext = false;

		// 조회한 결과 개수가 요청한 페이지 사이즈보다 크면 뒤에 더 있는 것, next = true
		if (result.size() > pageable.getPageSize()) {
			hasNext = true;
			result.remove(pageable.getPageSize());
		}
		return new SliceImpl<>(result, pageable, hasNext);
	}
}
