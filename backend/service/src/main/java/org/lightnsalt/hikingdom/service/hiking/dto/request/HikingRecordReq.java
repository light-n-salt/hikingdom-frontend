package org.lightnsalt.hikingdom.service.hiking.dto.request;

import io.swagger.v3.core.util.Json;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HikingRecordReq {
    private Boolean isGroup;
    private Long clubId;
    private Long meetupId;
    private int duration;
    private String gpsRoute;
}
