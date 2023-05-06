package org.lightnsalt.hikingdom.service.info.service;

import org.lightnsalt.hikingdom.common.dto.CustomSlice;
import org.lightnsalt.hikingdom.service.info.dto.response.MountainAddRes;
import org.lightnsalt.hikingdom.service.info.dto.response.MountainDetailRes;
import org.lightnsalt.hikingdom.service.info.dto.request.MountainAddReq;
import org.lightnsalt.hikingdom.service.info.dto.response.MountainListRes;
import org.springframework.data.domain.Pageable;

public interface MountainInfoService {
	MountainAddRes addMountainInfo(MountainAddReq reqDto);

	MountainDetailRes findMountainInfo(Long id);

	CustomSlice<MountainListRes> findAllMountainInfo(String query, String word, double lat, double lng, Long id,
		Pageable pageable);

	void addMountainInfoDaily(Long mountainId);
}
