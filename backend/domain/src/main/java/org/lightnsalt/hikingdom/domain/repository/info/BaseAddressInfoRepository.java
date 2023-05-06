package org.lightnsalt.hikingdom.domain.repository.info;

import java.util.List;

import org.lightnsalt.hikingdom.domain.entity.info.BaseAddressInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BaseAddressInfoRepository extends JpaRepository<BaseAddressInfo, String> {
	@Query("SELECT b FROM BaseAddressInfo b WHERE b.dongCode LIKE '%00000000' OR b.dongCode = '3611000000'")
	List<BaseAddressInfo> selectSidoList();
}
