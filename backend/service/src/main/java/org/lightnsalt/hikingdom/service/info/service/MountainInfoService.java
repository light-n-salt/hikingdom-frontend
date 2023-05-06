package org.lightnsalt.hikingdom.service.info.service;

import java.util.List;

import org.lightnsalt.hikingdom.service.info.dto.response.MountainAddRes;
import org.lightnsalt.hikingdom.service.info.dto.response.MountainDetailRes;
import org.lightnsalt.hikingdom.service.info.dto.request.MountainAddReq;
import org.lightnsalt.hikingdom.service.info.dto.response.MountainListRes;

public interface MountainInfoService {
	MountainAddRes addMountainInfo(MountainAddReq reqDto);

	MountainDetailRes findMountainInfo(Long id);

	List<MountainListRes> findAllMountainInfo(String query, String word, double lat, double lng, Long id);

	void addMountainInfoDaily(Long mountainId);
}
