package org.lightnsalt.hikingdom.domain.repository.hiking;

import static org.lightnsalt.hikingdom.domain.entity.club.meetup.QMeetupAlbum.*;

import java.util.List;

import org.lightnsalt.hikingdom.domain.entity.hiking.MemberHiking;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberHikingRepositoryCustom {

	// no-offset 방식 처리하는 메서드
	private BooleanExpression isLast(Long recordId) {
		if (recordId == null) {
			return null;
		}
		return meetupAlbum.id.lt(recordId);
	}

	// 무한 스크롤 방식 처리하는 메서드
	private Slice<MemberHiking> checkLastPage(Pageable pageable, List<MemberHiking> result) {
		boolean hasNext = false;

		// 조회한 결과 개수가 요청한 페이지 사이즈보다 크면 뒤에 더 있는 것, next = true
		if (result.size() > pageable.getPageSize()) {
			hasNext = true;
			result.remove(pageable.getPageSize());
		}
		return new SliceImpl<>(result, pageable, hasNext);
	}
}
