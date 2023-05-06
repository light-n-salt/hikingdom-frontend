package org.lightnsalt.hikingdom.domain.repository.info;

import static org.lightnsalt.hikingdom.domain.entity.club.meetup.QMeetupAlbum.*;
import static org.lightnsalt.hikingdom.domain.entity.info.QMountainInfo.mountainInfo;

import java.util.List;

import org.lightnsalt.hikingdom.domain.entity.info.MountainInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MountainInfoRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	public Slice<MountainInfo> findAll(Long mountainId, Pageable pageable) {
		List<MountainInfo> result = queryFactory.selectFrom(mountainInfo)
			.where(isLast(mountainId))
			.orderBy(mountainInfo.name.asc())
			.limit(pageable.getPageSize() + 1)
			.fetch();
		return checkLastPage(pageable, result);
	}

	public Slice<MountainInfo> findAllByNameContaining(Long mountainId, String word, Pageable pageable) {
		List<MountainInfo> result = queryFactory.selectFrom(mountainInfo)
			.where(isLast(mountainId)
				, mountainInfo.name.contains(word))
			.orderBy(mountainInfo.name.asc())
			.limit(pageable.getPageSize() + 1)
			.fetch();
		return checkLastPage(pageable, result);
	}

	// no-offset 방식 처리하는 메서드
	private BooleanExpression isLast(Long mountainId) {
		if (mountainId == null) {
			return null;
		}
		return meetupAlbum.id.lt(mountainId);
	}

	// 무한 스크롤 방식 처리하는 메서드
	private Slice<MountainInfo> checkLastPage(Pageable pageable, List<MountainInfo> result) {
		boolean hasNext = false;

		// 조회한 결과 개수가 요청한 페이지 사이즈보다 크면 뒤에 더 있는 것, next = true
		if (result.size() > pageable.getPageSize()) {
			hasNext = true;
			result.remove(pageable.getPageSize());
		}
		return new SliceImpl<>(result, pageable, hasNext);
	}
}
