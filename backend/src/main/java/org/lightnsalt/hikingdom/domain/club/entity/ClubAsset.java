package org.lightnsalt.hikingdom.domain.club.entity;

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

import org.lightnsalt.hikingdom.domain.BaseTimeEntity;
import org.lightnsalt.hikingdom.domain.club.entity.meetup.Meetup;
import org.lightnsalt.hikingdom.domain.info.entity.AssetInfo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "club_asset")
public class ClubAsset extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "BIGINT UNSIGNED")
	private Long id;

	@ToString.Exclude
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "club_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Club club;

	@ToString.Exclude
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "asset_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private AssetInfo asset;

	@ToString.Exclude
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "meetup_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Meetup meetup;

	@Column(name = "row_index", nullable = false, columnDefinition = "SMALLINT UNSIGNED")
	private int rowIndex;

	@Column(name = "col_index", nullable = false, columnDefinition = "SMALLINT UNSIGNED")
	private int colIndex;

	@Builder
	public ClubAsset(Club club, AssetInfo asset, Meetup meetup, int rowIndex, int colIndex) {
		this.club = club;
		this.asset = asset;
		this.meetup = meetup;
		this.rowIndex = rowIndex;
		this.colIndex = colIndex;
	}
}