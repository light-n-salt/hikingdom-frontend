package org.lightnsalt.hikingdom.domain.repository.info;

import java.util.List;

import org.lightnsalt.hikingdom.domain.entity.info.BaseAddressInfo;
import org.lightnsalt.hikingdom.domain.entity.info.QBaseAddressInfo;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BaseAddressInfoRepositoryCustom {
	private final JPAQueryFactory jpaQueryFactory;

	public List<BaseAddressInfo> selectGugunList(String dongCode) {
		QBaseAddressInfo qBaseAddressInfo = QBaseAddressInfo.baseAddressInfo;
		BooleanExpression predicate;

		if (dongCode.equals("3611000000")) {
			predicate = qBaseAddressInfo.dongCode.eq(dongCode);
		} else {
			String likeExpression = String.format("%s___00000", dongCode.substring(0, 2));
			predicate = qBaseAddressInfo.dongCode.like(likeExpression)
				.and(qBaseAddressInfo.dongCode.notLike("__00000000"));
		}

		return jpaQueryFactory.selectFrom(qBaseAddressInfo)
			.where(predicate)
			.orderBy(qBaseAddressInfo.gugunName.asc())
			.fetch();
	}
}
