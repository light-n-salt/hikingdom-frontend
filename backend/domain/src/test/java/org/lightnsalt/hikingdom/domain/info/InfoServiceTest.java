/*
package org.lightnsalt.hikingdom.domain.info;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.common.error.GlobalException;
import org.lightnsalt.hikingdom.domain.info.dto.request.MountainAddReq;
import org.lightnsalt.hikingdom.domain.info.dto.response.MountainAddRes;
import org.lightnsalt.hikingdom.entity.info.MountainInfo;
import org.lightnsalt.hikingdom.domain.info.repository.MountainInfoRepository;
import org.lightnsalt.hikingdom.domain.info.service.InfoServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

// @ExtendWith(MockitoExtension.class)
@SpringBootTest
public class InfoServiceTest {
	// 이미 산 데이터가 존재하는 경우
	private final String name = "테스트";
	private final String description = "테스트 설명";
	private final String address = "테스트 주소";
	private final String peaks = "테스트";
	private final double topAlt = 233;
	private final double topLat = 333.4;
	private final double topLng = 333.4;
	private final int totalDuration = 60;

	// 값 넣어두기
	final MountainInfo mountainInfo = MountainInfo.builder()
		.id(1L)
		.name(name)
		.description(description)
		.address(address)
		.topAlt(topAlt)
		.topLat(topLat)
		.topLng(topLng)
		.peaks(peaks)
		.totalDuration(totalDuration)
		.build();

	@InjectMocks
	private InfoServiceImpl target;
	@Mock
	private MountainInfoRepository mountainInfoRepository;

	@Test
	@DisplayName("산 데이터 등록 실패_이미 존재")
	public void MountainInfo_already_exist() {
		// given
		doReturn(MountainInfo.builder().build()).when(mountainInfoRepository).findByName(name);

		// when
		final GlobalException result = assertThrows(GlobalException.class,
			() -> target.addMountainInfo(
				new MountainAddReq(name, description, address, topAlt, topLat, topLng, totalDuration)));

		// then
		assertThat(result.getErrorCode()).isEqualTo(ErrorCode.DUPLICATED_MOUNTAIN_REGISTER);
	}

	@Test
	@DisplayName("산 데이터 등록_성공")
	public void MountainInfo_create_success() {
		// given
		doReturn(null).when(mountainInfoRepository).findByName(name);
		doReturn(mountainInfo).when(mountainInfoRepository).save(any(MountainInfo.class));

		// when
		final MountainAddRes mountainName = target.addMountainInfo(
			new MountainAddReq(name, description, address, topAlt, topLat, topLng, totalDuration));

		// then
		assertThat(mountainName.getName()).isEqualTo(name);
		assertThat(mountainName.getDescription()).isEqualTo(description);

		// verify
		verify(mountainInfoRepository, times(1)).findByName(name);
		verify(mountainInfoRepository, times(1)).save(any(MountainInfo.class));
	}

	/*
	@Test
	@DisplayName("산 데이터 ID 조회")
	@Transactional
	public void MountainInfo_findById() {
		// given
		doReturn(null).when(mountainInfoRepository).findById(mountainInfo.getId());
		doReturn(mountainInfo).when(mountainInfoRepository).save(any(MountainInfo.class));

		// when
		mountainInfoRepository.save(mountainInfo);
		final MountainRes findMountain = target.findMountainInfo(1L);

		// then
		assertThat(findMountain.getName()).isEqualTo(name);
		assertThat(findMountain.getName()).isEqualTo(name);
		assertThat(findMountain.getName()).isEqualTo(name);
		assertThat(findMountain.getName()).isEqualTo(name);
		assertThat(findMountain.getName()).isEqualTo(name);

		// then
	}

}
*/