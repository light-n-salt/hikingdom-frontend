package org.lightnsalt.hikingdom.domain.info.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MountainAddReq {
	@NotBlank(message = "산 이름을 필수 데이터입니다")
	private String name;
	@NotBlank(message = "산 설명은 필수 데이터입니다")
	private String description;
	@NotBlank(message = "산 주소는 필수 데이터입니다")
	private String address;
	private double topAlt;
	private double topLat;
	private double topLng;
	private int totalDuration;

}
