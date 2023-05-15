package org.lightnsalt.hikingdom.service.hiking.service;

import org.lightnsalt.hikingdom.service.hiking.dto.request.HikingRecordReq;
import org.lightnsalt.hikingdom.service.hiking.dto.response.TodayMeetupRes;

import java.util.List;

public interface HikingService {
    List<TodayMeetupRes> findTodayMeetup(String email);

    Long saveHikingRecord(String email, HikingRecordReq hikingRecordReq);
}
