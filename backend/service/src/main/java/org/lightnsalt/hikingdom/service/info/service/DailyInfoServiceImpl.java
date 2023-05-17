package org.lightnsalt.hikingdom.service.info.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.lightnsalt.hikingdom.domain.entity.info.AssetInfo;
import org.lightnsalt.hikingdom.domain.entity.info.ClubDailyInfo;
import org.lightnsalt.hikingdom.domain.entity.info.MountainDailyInfo;
import org.lightnsalt.hikingdom.service.info.dto.response.AssetInfoRes;
import org.lightnsalt.hikingdom.service.info.repository.ClubDailyInfoRepository;
import org.lightnsalt.hikingdom.service.info.repository.MountainDailyInfoRepository;
import org.lightnsalt.hikingdom.service.info.dto.response.ClubDailyRes;
import org.lightnsalt.hikingdom.service.info.dto.response.MountainDailyRes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class DailyInfoServiceImpl implements DailyInfoService {

	private final MountainDailyInfoRepository mountainDailyInfoRepository;
	private final ClubDailyInfoRepository clubDailyInfoRepository;
	@Value("${values.asset.defaultAsset}")
	private String defaultAssetUrl;
	private AssetInfo defaultAsset;

	@PostConstruct
	private void postConstruct() {
		defaultAsset = new AssetInfo(null, "기본 에셋", defaultAssetUrl, "기본 에셋", 0);
	}

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
		ClubDailyRes clubDailyRes = new ClubDailyRes(clubDailyInfo.getClub());

		// 기본 땅 에셋 추가해서 반환
		clubDailyRes.getAssets().add(0, new AssetInfoRes(defaultAsset));

		return clubDailyRes;
	}
}
