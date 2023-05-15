package org.lightnsalt.hikingdom.service.info.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.lightnsalt.hikingdom.common.dto.CustomSlice;
import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.common.error.GlobalException;
import org.lightnsalt.hikingdom.service.info.dto.response.MountainLocationRes;
import org.lightnsalt.hikingdom.service.info.repository.MountainDailyInfoRepository;
import org.lightnsalt.hikingdom.service.info.repository.MountainInfoRepositoryCustom;
import org.lightnsalt.hikingdom.service.info.dto.request.MountainAddRes;
import org.lightnsalt.hikingdom.service.info.dto.response.MountainDetailRes;
import org.lightnsalt.hikingdom.service.info.dto.request.MountainAddReq;
import org.lightnsalt.hikingdom.service.info.dto.response.MountainListRes;
import org.lightnsalt.hikingdom.domain.entity.info.MountainDailyInfo;
import org.lightnsalt.hikingdom.domain.entity.info.MountainInfo;
import org.lightnsalt.hikingdom.service.info.repository.MountainInfoRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MountainInfoServiceImpl implements MountainInfoService {

	private final MountainDailyInfoRepository mountainDailyInfoRepository;
	private final MountainInfoRepository mountainInfoRepository;
	private final MountainInfoRepositoryCustom mountainInfoRepositoryCustom;

	@Override
	public MountainAddRes addMountainInfo(MountainAddReq reqDto) {
		// 이미 존재하는 산인 경우 예외처리
		log.info("reqDto in service is : {}", reqDto);

		MountainInfo mountain = mountainInfoRepository.findByName(reqDto.getName());
		if (mountain != null) {
			log.error("search mountain info is : {}", mountain);
			throw new GlobalException(ErrorCode.DUPLICATED_MOUNTAIN_REGISTER);
		}

		mountain = MountainInfo.builder()
			.name(reqDto.getName())
			.description(reqDto.getDescription())
			.address(reqDto.getAddress())
			.topAlt(reqDto.getMaxAlt())
			.topLat(reqDto.getTopLat())
			.topLng(reqDto.getTopLng())
			.totalDuration(reqDto.getTotalDuration())
			.build();

		final MountainInfo savedMountain = mountainInfoRepository.save(mountain);

		return MountainAddRes.builder()
			.id(savedMountain.getId())
			.name(savedMountain.getName())
			.description(savedMountain.getDescription())
			.address(savedMountain.getAddress())
			.maxAlt(savedMountain.getTopAlt())
			.topLat(savedMountain.getTopLat())
			.topLng(savedMountain.getTopLng())
			.totalDuration(savedMountain.getTotalDuration())
			.build();

	}

	@Override
	public MountainDetailRes findMountainInfo(Long id) {
		// 산 데이터 DB에서 가져오기
		final MountainInfo mountain = mountainInfoRepository.findById(id)
			.orElseThrow(() -> new GlobalException(ErrorCode.MOUNTAIN_NOT_FOUND));

		// build MountainInfoRes
		return new MountainDetailRes(mountain);

	}

	// TODO: refactoring
	@Override
	public CustomSlice<MountainListRes> findAllMountainInfo(String query, String word, double lat, double lng, Long id,
		Pageable pageable) {
		Slice<MountainListRes> result;
		switch (query) {
			// 전체조회
			case "": {
				Slice<MountainInfo> list = mountainInfoRepositoryCustom.findAll(id, pageable);
				result = list.map(MountainListRes::new);
				break;
			}
			// 이름으로 산 검색
			case "name": {
				Slice<MountainInfo> list = mountainInfoRepositoryCustom.findAllByNameContaining(id, word, pageable);
				result = list.map(MountainListRes::new);
				break;
			}
			// 정해진 query의 입력이 주어지지 않을 때
			default:
				throw new GlobalException(ErrorCode.INVALID_INPUT_VALUE);
		}

		return new CustomSlice<>(result);
	}

	@Override
	@Transactional
	public void addMountainInfoDaily(Long mountainId) {
		// 산 정보 찾기
		final MountainInfo mountain = mountainInfoRepository.findById(mountainId)
			.orElseThrow(() -> new GlobalException(ErrorCode.MOUNTAIN_NOT_FOUND));

		// 오늘의 산 저장
		MountainDailyInfo mountainDailyInfo = MountainDailyInfo.builder()
			.mountain(mountain)
			.setDate(LocalDate.now())
			.build();
		mountainDailyInfoRepository.save(mountainDailyInfo);
	}

	@Override
	public List<MountainLocationRes> findMountainInfoLocation(double lat, double lng) {

		List<MountainInfo> mountainInfoList = mountainInfoRepository.findByLocation(lng, lat, 30000);
		return mountainInfoList.stream().map(MountainLocationRes::new).collect(Collectors.toList());
	}
}
