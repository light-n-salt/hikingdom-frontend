package org.lightnsalt.hikingdom.service.hiking.service;

import org.lightnsalt.hikingdom.service.hiking.dto.response.HikingRecordDetailRes;

public interface MemberHikingService {
	HikingRecordDetailRes findHikingRecord(String email, Long hikingRecordId);
}
