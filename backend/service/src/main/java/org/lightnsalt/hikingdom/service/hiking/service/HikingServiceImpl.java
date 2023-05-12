package org.lightnsalt.hikingdom.service.hiking.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.common.error.GlobalException;
import org.lightnsalt.hikingdom.domain.entity.club.meetup.Meetup;
import org.lightnsalt.hikingdom.domain.entity.hiking.MemberHiking;
import org.lightnsalt.hikingdom.domain.entity.hiking.MemberHikingGps;
import org.lightnsalt.hikingdom.domain.entity.info.MountainInfo;
import org.lightnsalt.hikingdom.domain.entity.member.Member;
import org.lightnsalt.hikingdom.service.club.repository.ClubRepository;
import org.lightnsalt.hikingdom.service.club.repository.meetup.MeetupMemberRepository;
import org.lightnsalt.hikingdom.service.club.repository.meetup.MeetupRepository;
import org.lightnsalt.hikingdom.service.hiking.dto.request.GpsRoute;
import org.lightnsalt.hikingdom.service.hiking.dto.request.HikingRecordReq;
import org.lightnsalt.hikingdom.service.hiking.dto.response.TodayMeetupRes;
import org.lightnsalt.hikingdom.service.hiking.repository.HikingRepositoryCustom;
import org.lightnsalt.hikingdom.service.hiking.repository.MemberHikingGpsRepository;
import org.lightnsalt.hikingdom.service.hiking.repository.MemberHikingRepository;
import org.lightnsalt.hikingdom.service.info.repository.MountainInfoRepository;
import org.lightnsalt.hikingdom.service.member.repository.MemberHikingStatisticRepository;
import org.lightnsalt.hikingdom.service.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class HikingServiceImpl implements HikingService {

    private final ClubRepository clubRepository;
    private final MeetupRepository meetupRepository;
    private final MeetupMemberRepository meetupMemberRepository;
    private final MemberRepository memberRepository;
    private final MemberHikingRepository memberHikingRepository;
    private final MemberHikingGpsRepository memberHikingGpsRepository;
    private final HikingRepositoryCustom hikingRepositoryCustom;
    private final MountainInfoRepository mountainInfoRepository;
    private final MemberHikingStatisticRepository memberHikingStatisticRepository;

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
        final Long memberId = memberRepository.findByEmail(email)
                .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND))
                .getId();

        final List<Meetup> meetups = hikingRepositoryCustom.findTodayMeetup(memberId);

        return meetups.stream().map(meetup -> {
            TodayMeetupRes dto = new TodayMeetupRes(meetup);
            // 일정 참여 멤버 가져오기
            final int totalMember = meetupMemberRepository.countByMeetupId(meetup.getId());
            dto.setTotalMember(totalMember);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Long saveHikingRecord(String email, HikingRecordReq hikingRecordReq) {
        final Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_UNAUTHORIZED));

        final MountainInfo mountainInfo = mountainInfoRepository.findById(hikingRecordReq.getMountainId())
                .orElseThrow(() -> new GlobalException(ErrorCode.MOUNTAIN_NOT_FOUND));

        LocalDateTime startAt = LocalDateTime.parse(hikingRecordReq.getStartAt(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endAt = startAt.plusMinutes(hikingRecordReq.getTotalDuration());



        Meetup meetup;
        MemberHiking memberHiking;
        if(hikingRecordReq.getIsMeetup()){
            meetup = meetupRepository.findById(hikingRecordReq.getMeetupId())
                    .orElseThrow(() -> new GlobalException(ErrorCode.MEETUP_NOT_FOUND));
            memberHiking = MemberHiking.builder()
                    .member(member)
                    .mountain(mountainInfo)
                    .meetup(meetup)
                    .isMeetup(hikingRecordReq.getIsMeetup())
                    .startAt(startAt)
                    .endAt(endAt)
                    .totalDuration((long) hikingRecordReq.getTotalDuration())
                    .totalDistance((long) hikingRecordReq.getTotalDistance())
                    .totalAlt((long) hikingRecordReq.getMaxAlt())
                    .isSummit(hikingRecordReq.getIsSummit())
                    .build();
        }else{
            memberHiking = MemberHiking.builder()
                    .member(member)
                    .mountain(mountainInfo)
                    .isMeetup(hikingRecordReq.getIsMeetup())
                    .startAt(startAt)
                    .endAt(endAt)
                    .totalDuration((long) hikingRecordReq.getTotalDuration())
                    .totalDistance((long) hikingRecordReq.getTotalDistance())
                    .totalAlt((long) hikingRecordReq.getMaxAlt())
                    .isSummit(hikingRecordReq.getIsSummit())
                    .build();
        }

//        hikingRecordReq.getGpsRoute()
        JsonObject gpsRoute = new JsonObject();

//        JsonArray jsonArray = new JsonArray();
//        jsonArray.add(hikingRecordReq.getGpsRoute());
        JsonArray jsonArray = new JsonArray();
        for (GpsRoute gps : hikingRecordReq.getGpsRoute()) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("lat", gps.getLat());
            jsonObject.addProperty("lng", gps.getLng());
            jsonObject.addProperty("alt", gps.getAlt());
            jsonArray.add(jsonObject);
        }
//        Map<String, Object> map = new HashMap<>();
//        map.put("gpsRoute", jsonArray);
        gpsRoute.add("gpsRoute", jsonArray);
        MemberHikingGps memberHikingGps = MemberHikingGps.builder()
                .hiking(memberHiking)
                .gpsRoute(getMapFromJsonObject(gpsRoute))
                .build();

        final MemberHiking savedMemberHiking = memberHikingRepository.save(memberHiking);
        final MemberHikingGps savedMemberHikingGps = memberHikingGpsRepository.save(memberHikingGps);



        return savedMemberHiking.getId();
    }

    public static Map<String, Object> getMapFromJsonObject(JsonObject jsonObj){
        Map<String, Object> map = null;

        try {
            map = new ObjectMapper().readValue(jsonObj.toString(), Map.class);
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return map;
    }
}
