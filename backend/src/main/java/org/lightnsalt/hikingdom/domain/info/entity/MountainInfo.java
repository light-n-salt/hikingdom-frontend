package org.lightnsalt.hikingdom.domain.info.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@Builder
@AllArgsConstructor
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

	@Column(name = "top_alt", nullable = false)
	private double topAlt;

	@Column(name = "top_lat", nullable = false)
	private double topLat;

	@Column(name = "top_lng", nullable = false)
	private double topLng;

	@Column(name = "total_duration", columnDefinition = "int unsigned", nullable = false)
	private int totalDuration;

	@Column(name = "img_url", length = 512)
	private String imgUrl;

	@Column(name = "peaks", length = 128)
	private String peaks;

	@OneToMany(mappedBy = "mountain")
	@ToString.Exclude
	private List<AssetInfo> asset;

	public void setAsset(List<AssetInfo> asset) {
		this.asset = asset;
	}

}
