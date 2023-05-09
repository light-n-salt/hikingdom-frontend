package org.lightnsalt.hikingdom.service.hiking.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HikingRecordReq {
    private Boolean isMeetup;
    private Long mountainId;
    private Long meetupId;
    private String startAt;
    private int totalDistance;
    private double maxAlt;
    private int totalDuration;
    private List<GpsRoute> gpsRoute;
}