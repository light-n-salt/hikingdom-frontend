package org.lightnsalt.hikingdom.domain.info.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "base_address_info")
public class BaseAddressInfo {
	@Id
	@Column(name = "dong_code", length = 10)
	private String dongCode;

	@Column(name = "sido_name", length = 20)
	private String sidoName;

	@Column(name = "sigungu_name", length = 20)
	private String sigunguName;

	@Builder
	public BaseAddressInfo(String dongCode, String sidoName, String sigunguName) {
		this.dongCode = dongCode;
		this.sidoName = sidoName;
		this.sigunguName = sigunguName;
	}
}
