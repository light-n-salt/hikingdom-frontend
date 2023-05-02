package org.lightnsalt.hikingdom.domain.info.service;

import java.util.List;
import java.util.stream.Collectors;

import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.common.error.GlobalException;
import org.lightnsalt.hikingdom.domain.info.dto.response.MountainAddRes;
import org.lightnsalt.hikingdom.domain.info.dto.response.MountainDetailRes;
import org.lightnsalt.hikingdom.domain.info.dto.request.MountainAddReq;
import org.lightnsalt.hikingdom.domain.info.dto.response.MountainListRes;
import org.lightnsalt.hikingdom.domain.info.entity.MountainInfo;
import org.lightnsalt.hikingdom.domain.info.repository.MountainInfoRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class InfoServiceImpl implements InfoService {
	private final MountainInfoRepository mountainInfoRepository;

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
	public List<MountainListRes> findAllMountainInfo(String query, String word, double lat, double lng, Long id) {
		List<MountainListRes> result;
		switch (query) {
			// 전체조회
			case "": {
				List<MountainInfo> list = mountainInfoRepository.findAll();
				result = list.stream().map(MountainListRes::new).sorted().collect(
					Collectors.toList());
				break;
			}
			// 가까운 산 검색
			// case "location": {
			// 	List<MountainInfoDto> list = mountainInfoRepository.findDistance(lat, lng, 3000);
			// 	result = list.stream().map(MountainListRes::new).sorted().collect(
			// 		Collectors.toList());
			// 	break;
			// }
			// 이름으로 산 검색
			case "name": {
				List<MountainInfo> list = mountainInfoRepository.findAllByNameContaining(word);
				result = list.stream().map(MountainListRes::new).sorted().collect(
					Collectors.toList());
				break;
			}
			// 정해진 query의 입력이 주어지지 않을 때
			default:
				throw new GlobalException(ErrorCode.INVALID_INPUT_VALUE);
		}

		return result;
	}
}
