package org.lightnsalt.hikingdom.service.hiking.repository;

import static org.lightnsalt.hikingdom.domain.entity.club.meetup.QMeetup.meetup;
import static org.lightnsalt.hikingdom.domain.entity.club.meetup.QMeetupMember.meetupMember;

import com.querydsl.core.types.dsl.DateExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.lightnsalt.hikingdom.domain.entity.club.meetup.Meetup;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class HikingRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    DateExpression<Date> dateExpr = Expressions.dateTemplate(Date.class, "DATE({0})", meetup.startAt);

    public List<Meetup> findTodayMeetup(Long memberId) {

        return jpaQueryFactory.selectFrom(meetup)
                .innerJoin(meetupMember).on(meetup.id.eq(meetupMember.meetup.id))
                .where(dateExpr.eq(new Date()), meetupMember.member.id.eq(memberId))
                .fetch();
    }
}
