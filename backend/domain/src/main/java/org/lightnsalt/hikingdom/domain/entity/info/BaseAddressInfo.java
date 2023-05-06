package org.lightnsalt.hikingdom.domain.entity.info;

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

	@Column(name = "gugun_name", length = 20)
	private String gugunName;

	@Builder
	public BaseAddressInfo(String dongCode, String sidoName, String gugunName) {
		this.dongCode = dongCode;
		this.sidoName = sidoName;
		this.gugunName = gugunName;
	}
}
