package org.lightnsalt.hikingdom.domain.info.service;

import org.lightnsalt.hikingdom.domain.info.dto.response.MountainAddRes;
import org.lightnsalt.hikingdom.domain.info.dto.response.MountainDetailRes;
import org.lightnsalt.hikingdom.domain.info.dto.request.MountainAddReq;

public interface InfoService {
	MountainAddRes addMountainInfo(MountainAddReq reqDto);

	MountainDetailRes findMountainInfo(Long id);
}
