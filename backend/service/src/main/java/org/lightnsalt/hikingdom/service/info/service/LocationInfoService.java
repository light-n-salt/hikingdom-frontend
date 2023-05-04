package org.lightnsalt.hikingdom.service.info.service;

import java.util.List;

import org.lightnsalt.hikingdom.service.info.dto.response.BaseAddressInfoRes;

public interface LocationInfoService {
	List<BaseAddressInfoRes> findBaseAddressInfoList(String query, String word);
}
