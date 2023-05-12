package org.lightnsalt.hikingdom.service.hiking.dto.request;

import io.swagger.v3.core.util.Json;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private Boolean isSummit;
    private List<GpsRoute> gpsRoute;
}