/*
package org.lightnsalt.hikingdom.domain.info;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.lightnsalt.hikingdom.domain.info.dto.request.MountainAddReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class InfoControllerTest {
	@Autowired
	private MockMvc mockMvc;

	private Gson gson;

	@BeforeEach
	public void init() {
		gson = new Gson();
	}

	@Test
	@DisplayName("산 데이터 입력값 Null")
	@Transactional
	@WithMockUser(username = "ssafy@ssafy.com", password = "ssafy!1234", roles = {"USER", "ADMIN"})
	public void MountainInfo_input_isNull() throws Exception {
		// given
		final String url = "/api/v1/info/mountains";

		// when
		final ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.post(url)
				.content(gson.toJson(mountainInfoRequestNull()))
				.contentType(MediaType.APPLICATION_JSON)
		);

		// then
		resultActions.andExpect(status().isBadRequest());

	}

	@Test
	@DisplayName("산 데이터 입력 성공")
	@Transactional
	@WithMockUser(username = "ssafy@ssafy.com", password = "ssafy!1234", roles = {"USER", "ADMIN"})
	public void Mountain_create_success() throws Exception {
		// given
		final String url = "/api/v1/info/mountains";

		// when
		final ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.post(url)
				.content(gson.toJson(mountainInfoRequest()))
				.contentType(MediaType.APPLICATION_JSON)
		);

		// then
		resultActions.andExpect(status().isCreated());
	}

	private MountainAddReq mountainInfoRequest() {
		return MountainAddReq.builder()
			.name("산 테스트")
			.description("테스트 설명")
			.address("테스트 주소")
			.topAlt(233)
			.topLat(333.4)
			.topLng(333.4)
			.totalDuration(60)
			.build();
	}

	private MountainAddReq mountainInfoRequestNull() {
		return MountainAddReq.builder()
			.name("")
			.description("")
			.address("")
			.build();
	}

}
*/