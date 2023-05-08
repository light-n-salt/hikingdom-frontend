package org.lightnsalt.hikingdom.service.club.repository;

import static org.lightnsalt.hikingdom.domain.entity.club.QClub.*;
import static org.lightnsalt.hikingdom.domain.entity.club.record.QClubRanking.*;

import java.time.LocalDate;
import java.util.List;

import org.lightnsalt.hikingdom.service.club.dto.response.ClubSearchRes;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ClubSearchRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	private Slice<ClubSearchRes> findClubs(BooleanExpression whereClause, Pageable pageable) {
		LocalDate today = LocalDate.now();

		List<ClubSearchRes> result = queryFactory
			.select(Projections.constructor(ClubSearchRes.class, club,
				Expressions.cases()
					.when(clubRanking.setDate.eq(today)).then(clubRanking.ranking)
					.when(clubRanking.setDate.eq(today.minusDays(1))).then(clubRanking.ranking)
					.otherwise((Long)null).as("ranking")))
			.from(club)
			.leftJoin(clubRanking).on(club.id.eq(clubRanking.club.id))
			.where(whereClause)
			.orderBy(clubRanking.ranking.desc())
			.limit(pageable.getPageSize() + 1)
			.fetch();

		return checkLastPage(pageable, result);
	}

	public Slice<ClubSearchRes> findClubsByName(String word, Long clubId, Pageable pageable) {
		BooleanExpression whereClause = (club.isDeleted.eq(false))
			.and(club.name.likeIgnoreCase("%" + word + "%"))
			.and(isLast(clubId));

		return findClubs(whereClause, pageable);
	}

	public Slice<ClubSearchRes> findClubsByLocation(String dongCode, Long clubId, Pageable pageable) {
		BooleanExpression whereClause = (club.isDeleted.eq(false))
			.and(isLast(clubId));

		if (!dongCode.equals("0000000000")) {
			whereClause = whereClause.and(club.baseAddress.dongCode.eq(dongCode));
		}

		return findClubs(whereClause, pageable);
	}

	private BooleanExpression isLast(Long clubId) {
		if (clubId == null) {
			return null;
		}
		return club.id.lt(clubId);
	}

	private Slice<ClubSearchRes> checkLastPage(Pageable pageable, List<ClubSearchRes> result) {
		boolean hasNext = false;

		// 조회한 결과 개수가 요청한 페이지 사이즈보다 크면 뒤에 더 있는 것, next = true
		if (result.size() > pageable.getPageSize()) {
			hasNext = true;
			result.remove(pageable.getPageSize());
		}
		return new SliceImpl<>(result, pageable, hasNext);
	}
}
