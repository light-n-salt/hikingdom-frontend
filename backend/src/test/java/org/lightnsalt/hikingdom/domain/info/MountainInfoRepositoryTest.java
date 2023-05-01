package org.lightnsalt.hikingdom.domain.info;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.lightnsalt.hikingdom.domain.info.entity.MountainInfo;
import org.lightnsalt.hikingdom.domain.info.repository.MountainInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MountainInfoRepositoryTest {
	@Autowired
	private MountainInfoRepository mountainInfoRepository;

	@Test
	@DisplayName("산 데이터 생성")
	public void MountainInfo_create() {
		// given
		final MountainInfo mountainInfo = MountainInfo.builder()
			.name("테스트")
			.description("테스트 설명")
			.address("테스트 주소")
			.topAlt(233)
			.topLat(333.4)
			.topLng(333.4)
			.peaks("테스트 봉우리")
			.totalDuration(60)
			.build();

		// when
		final MountainInfo result = mountainInfoRepository.save(mountainInfo);

		// then
		assertThat(result.getId()).isNotNull();
		assertThat(result.getName()).isEqualTo("테스트");
		assertThat(result.getDescription()).isEqualTo("테스트 설명");
		assertThat(result.getAddress()).isEqualTo("테스트 주소");
		assertThat(result.getTopAlt()).isEqualTo(233);
		assertThat(result.getTopLat()).isEqualTo(333.4);
		assertThat(result.getTopLng()).isEqualTo(333.4);
		assertThat(result.getPeaks()).isEqualTo("테스트 봉우리");
		assertThat(result.getTotalDuration()).isEqualTo(60);
	}

	@Test
	@DisplayName("산 데이터 생성 및 확인")
	public void MountainInfo_select() {
		// given
		final MountainInfo mountainInfo = MountainInfo.builder()
			.name("테스트")
			.description("테스트 설명")
			.address("테스트 주소")
			.topAlt(233)
			.topLat(333.4)
			.topLng(333.4)
			.peaks("테스트 봉우리")
			.totalDuration(60)
			.build();

		// when
		mountainInfoRepository.save(mountainInfo);
		final MountainInfo result = mountainInfoRepository.findByName("테스트");

		// then
		assertThat(result.getId()).isNotNull();
		assertThat(result.getName()).isEqualTo("테스트");
		assertThat(result.getDescription()).isEqualTo("테스트 설명");
		assertThat(result.getAddress()).isEqualTo("테스트 주소");
		assertThat(result.getTopAlt()).isEqualTo(233);
		assertThat(result.getTopLat()).isEqualTo(333.4);
		assertThat(result.getTopLng()).isEqualTo(333.4);
		assertThat(result.getPeaks()).isEqualTo("테스트 봉우리");
		assertThat(result.getTotalDuration()).isEqualTo(60);
	}

	@Test
	@DisplayName("산 데이터 ID 조회")
	public void MountainInfo_findById() {
		// given
		final MountainInfo mountainInfo = MountainInfo.builder()
			.name("테스트")
			.description("테스트 설명")
			.address("테스트 주소")
			.topAlt(233)
			.topLat(333.4)
			.topLng(333.4)
			.peaks("테스트 봉우리")
			.totalDuration(60)
			.build();

		// when
		Long id = mountainInfoRepository.save(mountainInfo).getId();
		final MountainInfo result = mountainInfoRepository.findById(id).orElseThrow();

		// then
		assertThat(result.getId()).isNotNull();
		assertThat(result.getName()).isEqualTo("테스트");
		assertThat(result.getDescription()).isEqualTo("테스트 설명");
		assertThat(result.getAddress()).isEqualTo("테스트 주소");
		assertThat(result.getTopAlt()).isEqualTo(233);
		assertThat(result.getTopLat()).isEqualTo(333.4);
		assertThat(result.getTopLng()).isEqualTo(333.4);
		assertThat(result.getPeaks()).isEqualTo("테스트 봉우리");
		assertThat(result.getTotalDuration()).isEqualTo(60);
	}

}
