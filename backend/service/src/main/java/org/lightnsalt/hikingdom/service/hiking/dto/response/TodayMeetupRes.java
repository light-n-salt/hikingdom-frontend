package org.lightnsalt.hikingdom.service.hiking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.lightnsalt.hikingdom.domain.entity.club.meetup.Meetup;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodayMeetupRes {
    private Long meetupId;
    private String meetupName;
    private Long mountainId;
    private String mountainName;
    private int totalMember;
    private String startAt;

    public TodayMeetupRes(Meetup meetup){
        this.meetupId = meetup.getId();
        this.meetupName = meetup.getName();
        this.mountainId = meetup.getMountain().getId();
        this.mountainName = meetup.getMountain().getName();
        this.startAt = meetup.getStartAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
//    public TodayMeetupRes(Long meetupId, String meetupName, Long mountainId, String mountainName, int totalMember, LocalDateTime startAt) {
//        this.meetupId = meetupId;
//        this.meetupName = meetupName;
//        this.mountainId = mountainId;
//        this.mountainName = mountainName;
//        this.totalMember = totalMember;
//        this.startAt = startAt;
//    }
}
