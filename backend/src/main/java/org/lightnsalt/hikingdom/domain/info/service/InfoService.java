package org.lightnsalt.hikingdom.domain.info.service;

import org.lightnsalt.hikingdom.domain.info.dto.response.MountainAddRes;
import org.lightnsalt.hikingdom.domain.info.dto.response.MountainGetRes;
import org.lightnsalt.hikingdom.domain.info.dto.request.MountainAddReq;

public interface InfoService {
	MountainAddRes addMountainInfo(MountainAddReq reqDto);

	MountainGetRes findMountainInfo(Long id);
}
