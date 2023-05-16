package org.lightnsalt.hikingdom.service.info.service;

import java.util.List;
import java.util.stream.Collectors;

import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.common.error.GlobalException;
import org.lightnsalt.hikingdom.service.info.dto.response.BaseAddressInfoRes;
import org.lightnsalt.hikingdom.service.info.repository.BaseAddressInfoRepository;
import org.lightnsalt.hikingdom.service.info.repository.BaseAddressInfoRepositoryCustom;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocationInfoServiceImpl implements LocationInfoService {
	private static final String SIDO_PATTERN = "^(?=(.*\\d))(\\d{2}00000000|3611000000)$";

	private final BaseAddressInfoRepository baseAddressInfoRepository;
	private final BaseAddressInfoRepositoryCustom baseAddressInfoRepositoryCustom;

	@Override
	public List<BaseAddressInfoRes> findBaseAddressInfoList(String query, String word) {
		if (!(query.equals("sido") && word.equals("0000000000") ||
			query.equals("gugun") && word.matches(SIDO_PATTERN))) {
			log.error("LocationInfoService:findSidoList: Invalid Query {}, Word {}", query, word);
			throw new GlobalException(ErrorCode.INVALID_INPUT_VALUE);
		}

		List<BaseAddressInfoRes> baseAddressInfoList;

		if (query.equals("gugun")) {
			baseAddressInfoList = baseAddressInfoRepositoryCustom.selectGugunList(word)
				.stream().map(BaseAddressInfoRes::new).collect(Collectors.toList());
		} else {
			baseAddressInfoList = baseAddressInfoRepository.selectSidoList()
				.stream().map(BaseAddressInfoRes::new).collect(Collectors.toList());
		}

		return baseAddressInfoList;
	}
}
