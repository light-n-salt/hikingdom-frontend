package org.lightnsalt.hikingdom.service.info.service;

import java.util.List;

import org.lightnsalt.hikingdom.service.info.dto.response.ClubDailyRes;
import org.lightnsalt.hikingdom.service.info.dto.response.MountainDailyRes;

public interface DailyInfoService {
	List<MountainDailyRes> findDailyMountainList();

	ClubDailyRes findDailyClub();
}
