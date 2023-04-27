package org.lightnsalt.hikingdom.domain.info.entity;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "asset_info")
public class AssetInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, columnDefinition = "BIGINT UNSIGNED")
	private Long id;

	@Column(nullable = false, length = 50)
	private String name;

	@Column(name = "asset_url", nullable = false, length = 512)
	private String assetUrl;

	@Column(nullable = false, length = 512)
	private String rule;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mountain_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	@ToString.Exclude
	private MountainInfo mountainInfo;

	@Builder
	public AssetInfo(String name, String assetUrl, String rule, MountainInfo mountainInfo) {
		this.name = name;
		this.assetUrl = assetUrl;
		this.rule = rule;
		this.mountainInfo = mountainInfo;
	}
}
