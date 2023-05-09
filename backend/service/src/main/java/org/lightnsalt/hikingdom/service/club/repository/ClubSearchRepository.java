package org.lightnsalt.hikingdom.service.club.repository;

import static org.lightnsalt.hikingdom.domain.entity.club.QClub.*;

import java.util.List;

import org.lightnsalt.hikingdom.service.club.dto.response.ClubSearchRes;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import com.querydsl.core.types.dsl.BooleanExpression;

public class ClubSearchRepository {
	protected BooleanExpression isLast(Long clubId) {
		if (clubId == null) {
			return null;
		}
		return club.id.lt(clubId);
	}

	protected Slice<ClubSearchRes> checkLastPage(Pageable pageable, List<ClubSearchRes> result) {
		boolean hasNext = false;

		// 조회한 결과 개수가 요청한 페이지 사이즈보다 크면 뒤에 더 있는 것, next = true
		if (result.size() > pageable.getPageSize()) {
			hasNext = true;
			result.remove(pageable.getPageSize());
		}
		return new SliceImpl<>(result, pageable, hasNext);
	}
}
