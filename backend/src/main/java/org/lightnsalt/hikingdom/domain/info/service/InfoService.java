package org.lightnsalt.hikingdom.domain.info.service;

import java.util.List;

import org.lightnsalt.hikingdom.domain.info.dto.response.MountainAddRes;
import org.lightnsalt.hikingdom.domain.info.dto.response.MountainDetailRes;
import org.lightnsalt.hikingdom.domain.info.dto.request.MountainAddReq;
import org.lightnsalt.hikingdom.domain.info.dto.response.MountainListRes;

public interface InfoService {
	MountainAddRes addMountainInfo(MountainAddReq reqDto);

	MountainDetailRes findMountainInfo(Long id);

	List<MountainListRes> findAllMountainInfo(String query, String word, double lat, double lng, Long id);
}
