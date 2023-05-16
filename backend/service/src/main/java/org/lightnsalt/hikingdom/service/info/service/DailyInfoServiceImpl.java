package org.lightnsalt.hikingdom.service.info.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.lightnsalt.hikingdom.domain.entity.info.ClubDailyInfo;
import org.lightnsalt.hikingdom.domain.entity.info.MountainDailyInfo;
import org.lightnsalt.hikingdom.service.info.repository.ClubDailyInfoRepository;
import org.lightnsalt.hikingdom.service.info.repository.MountainDailyInfoRepository;
import org.lightnsalt.hikingdom.service.info.dto.response.ClubDailyRes;
import org.lightnsalt.hikingdom.service.info.dto.response.MountainDailyRes;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class DailyInfoServiceImpl implements DailyInfoService {

	private final MountainDailyInfoRepository mountainDailyInfoRepository;
	private final ClubDailyInfoRepository clubDailyInfoRepository;

	@Override
	public List<MountainDailyRes> findDailyMountainList() {
		final List<MountainDailyInfo> mountainDailyInfoList = mountainDailyInfoRepository.findBySetDate(
			LocalDate.now());
		return mountainDailyInfoList.stream()
			.map(mountainDailyInfo -> new MountainDailyRes(mountainDailyInfo.getMountain()))
			.collect(Collectors.toList());
	}

	@Override
	public ClubDailyRes findDailyClub() {
		final ClubDailyInfo clubDailyInfo = clubDailyInfoRepository.findBySetDate(LocalDate.now());
		return new ClubDailyRes(clubDailyInfo.getClub());
	}
}
