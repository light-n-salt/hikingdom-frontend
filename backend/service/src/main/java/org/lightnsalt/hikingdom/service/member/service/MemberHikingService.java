package org.lightnsalt.hikingdom.service.member.service;

import org.lightnsalt.hikingdom.service.member.dto.response.HikingRecordDetailRes;

public interface MemberHikingService {
	HikingRecordDetailRes findHikingRecord(String email, Long hikingRecordId);
}
