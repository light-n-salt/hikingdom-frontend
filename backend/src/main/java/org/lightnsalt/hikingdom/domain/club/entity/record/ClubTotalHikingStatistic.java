package org.lightnsalt.hikingdom.domain.club.entity.record;

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

import org.lightnsalt.hikingdom.domain.club.entity.Club;

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
@Table(name = "club_total_hiking_statistic")
public class ClubTotalHikingStatistic {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "BIGINT UNSIGNED")
	private Long id;

	@ToString.Exclude
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "club_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Club club;

	@Column(name = "total_hiking_count", nullable = false, columnDefinition = "INT UNSIGNED")
	private Long totalHikingCount;

	@Column(name = "total_mountain_count", nullable = false, columnDefinition = "INT UNSIGNED")
	private Long totalMountainCount;

	@Column(name = "total_duration", nullable = false, columnDefinition = "INT UNSIGNED")
	private Long totalDuration; // in seconds

	@Column(name = "total_distance", nullable = false, columnDefinition = "INT UNSIGNED")
	private Long totalDistance; // in metres

	@Column(name = "total_alt", nullable = false, columnDefinition = "INT UNSIGNED")
	private Long totalAlt; // in metres

	@Column(name = "participation_rate", nullable = false)
	private double participationRate;

	@Builder
	public ClubTotalHikingStatistic(Club club, Long totalHikingCount, Long totalMountainCount, Long totalDuration,
		Long totalDistance, Long totalAlt, double participationRate) {
		this.club = club;
		this.totalHikingCount = totalHikingCount;
		this.totalMountainCount = totalMountainCount;
		this.totalDuration = totalDuration;
		this.totalDistance = totalDistance;
		this.totalAlt = totalAlt;
		this.participationRate = participationRate;
	}
}
