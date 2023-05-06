package org.lightnsalt.hikingdom.service.info.service;

import java.time.LocalDate;

import org.lightnsalt.hikingdom.domain.entity.info.MountainDailyInfo;
import org.lightnsalt.hikingdom.domain.repository.info.MountainDailyInfoRepository;
import org.lightnsalt.hikingdom.service.info.dto.response.MountainDailyRes;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class DailyInfoServiceImpl implements DailyInfoService {

	private final MountainDailyInfoRepository mountainDailyInfoRepository;

	@Override
	public MountainDailyRes findDailyMountain() {
		final MountainDailyInfo mountainDailyInfo = mountainDailyInfoRepository.findBySetDate(LocalDate.now());
		return new MountainDailyRes(mountainDailyInfo.getMountain());
	}
}
