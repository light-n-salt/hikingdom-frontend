package org.lightnsalt.hikingdom.domain.info.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "mountain_info")
public class MountainInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "BIGINT UNSIGNED")
	private Long id;

	@Column(length = 20, nullable = false)
	private String name;

	@Column(nullable = false)
	private String description;

	@Column(length = 200, nullable = false)
	private String address;

	@Column(name = "max_alt", nullable = false)
	private double maxAlt;

	@Column(name = "top_lat", nullable = false)
	private double topLat;

	@Column(name = "top_lng", nullable = false)
	private double topLng;

	@Column(name = "total_duration", columnDefinition = "int unsigned", nullable = false)
	private int totalDuration;

	@Column(name = "img_url", length = 512)
	private String imgUrl;

	@Builder
	public MountainInfo(String name, String description, String address, double maxAlt, double topLat, double topLng,
		int totalDuration, String imgUrl) {
		this.name = name;
		this.description = description;
		this.address = address;
		this.maxAlt = maxAlt;
		this.topLat = topLat;
		this.topLng = topLng;
		this.totalDuration = totalDuration;
		this.imgUrl = imgUrl;
	}
}
