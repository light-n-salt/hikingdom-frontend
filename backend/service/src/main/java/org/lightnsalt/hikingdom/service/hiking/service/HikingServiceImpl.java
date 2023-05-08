package org.lightnsalt.hikingdom.service.hiking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.common.error.GlobalException;
import org.lightnsalt.hikingdom.domain.entity.club.meetup.Meetup;
import org.lightnsalt.hikingdom.service.club.dto.response.MeetupDailyRes;
import org.lightnsalt.hikingdom.service.club.repository.ClubRepository;
import org.lightnsalt.hikingdom.service.club.repository.meetup.MeetupMemberRepository;
import org.lightnsalt.hikingdom.service.club.repository.meetup.MeetupRepository;
import org.lightnsalt.hikingdom.service.hiking.dto.request.HikingRecordReq;
import org.lightnsalt.hikingdom.service.hiking.dto.response.TodayMeetupRes;
import org.lightnsalt.hikingdom.service.hiking.repository.HikingRepositoryCustom;
import org.lightnsalt.hikingdom.service.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class HikingServiceImpl implements HikingService {

    private final ClubRepository clubRepository;
    private final MeetupRepository meetupRepository;
    private final MeetupMemberRepository meetupMemberRepository;
    private final MemberRepository memberRepository;
    private final HikingRepositoryCustom hikingRepositoryCustom;

//    @Override
//    @Transactional
//    public List<TodayMeetupRes> findTodayMeetup(Long clubId, String date) {
//        // 소모임이 존재하는지 확인
//        final boolean isExit = clubRepository.existsById(clubId);
//        if (!isExit) {
//            throw new GlobalException(ErrorCode.CLUB_NOT_FOUND);
//        }
//
//        // 입력 데이터 가공
//        String[] element = date.split("-");
//
//        // 일정 데이터 가져오기
//        final List<Meetup> meetups = meetupRepository.findByClubIdAndStartDay(clubId, Integer.parseInt(element[0]),
//                Integer.parseInt(element[1]),
//                Integer.parseInt(element[2]));
//
//        // 형 변환
//        return meetups.stream().map(meetup -> {
//            TodayMeetupRes dto = new TodayMeetupRes(meetup);
//            // 일정 참여 멤버 가져오기
//            final int totalMember = meetupMemberRepository.countByMeetupIdAndIsWithdraw(meetup.getId(), false);
//            dto.setTotalMember(totalMember);
//            return dto;
//        }).collect(Collectors.toList());
//    }

    @Override
    @Transactional
    public List<TodayMeetupRes> findTodayMeetup(String email) {
        // 회원 확인
        final Long memberId = memberRepository.findByEmailAndIsWithdraw(email, false)
                .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND))
                .getId();

        final List<Meetup> meetups = hikingRepositoryCustom.findTodayMeetup(memberId);

        return meetups.stream().map(meetup -> {
            TodayMeetupRes dto = new TodayMeetupRes(meetup);
            // 일정 참여 멤버 가져오기
            final int totalMember = meetupMemberRepository.countByMeetupIdAndIsWithdraw(meetup.getId(), false);
            dto.setTotalMember(totalMember);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Long saveHikingRecord(String email, HikingRecordReq hikingRecordReq) {
        // 회원 확인
        final Long memberId = memberRepository.findByEmailAndIsWithdraw(email, false)
                .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND))
                .getId();

        return null;
    }
}
