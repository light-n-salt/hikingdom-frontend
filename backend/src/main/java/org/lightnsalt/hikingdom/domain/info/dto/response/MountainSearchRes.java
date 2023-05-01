package org.lightnsalt.hikingdom.domain.info.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MountainSearchRes {
	private Long id;
	private String name;
	private String address;
	private double maxAlt;
	private String imgUrl;

}
