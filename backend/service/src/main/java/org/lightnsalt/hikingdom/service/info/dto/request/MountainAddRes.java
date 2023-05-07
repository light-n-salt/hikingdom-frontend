package org.lightnsalt.hikingdom.service.info.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MountainAddRes {
	private Long id;
	private String name;
	private String description;
	private String address;
	private double maxAlt;
	private double topLat;
	private double topLng;
	private int totalDuration;
	private String imgUrl;
	private String peaks;
	private String transport;
	private String facility;
}
