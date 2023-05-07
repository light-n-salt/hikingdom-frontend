package org.lightnsalt.hikingdom.service.hiking.service;

import org.lightnsalt.hikingdom.common.dto.CustomSlice;
import org.lightnsalt.hikingdom.service.hiking.dto.response.HikingRecordDetailRes;
import org.lightnsalt.hikingdom.service.hiking.dto.response.HikingRecordListRes;
import org.springframework.data.domain.Pageable;

public interface MemberHikingService {
	HikingRecordDetailRes findHikingRecord(String email, Long hikingRecordId);

	CustomSlice<HikingRecordListRes> findHikingRecordList(String email, Long hikingRecordId, Pageable pageable);
}
