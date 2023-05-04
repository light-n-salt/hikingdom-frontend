package org.lightnsalt.hikingdom.service.info.dto.response;

import org.lightnsalt.hikingdom.domain.entity.info.BaseAddressInfo;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
public class BaseAddressInfoRes {
	private String dongCode;
	private String sidoName;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String gugunName;

	public BaseAddressInfoRes(BaseAddressInfo baseAddressInfo) {
		this.dongCode = baseAddressInfo.getDongCode();
		this.sidoName = baseAddressInfo.getSidoName();
		this.gugunName = baseAddressInfo.getGugunName();
	}
}
