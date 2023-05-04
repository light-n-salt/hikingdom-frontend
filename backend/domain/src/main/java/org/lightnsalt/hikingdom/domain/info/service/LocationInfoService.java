package org.lightnsalt.hikingdom.domain.info.service;

import java.util.List;

import org.lightnsalt.hikingdom.domain.info.dto.response.BaseAddressInfoRes;

public interface LocationInfoService {
	List<BaseAddressInfoRes> findBaseAddressInfoList(String query, String word);
}
