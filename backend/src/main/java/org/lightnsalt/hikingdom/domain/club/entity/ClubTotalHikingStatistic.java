package org.lightnsalt.hikingdom.domain.club.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "club_total_hiking_statistic")
public class ClubTotalHikingStatistic {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "BIGINT UNSIGNED")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "club_id", columnDefinition = "BIGINT UNSIGNED")
	@ToString.Exclude
	private Club club;

	@Column(name = "total_hiking_count", nullable = false)
	private int totalHikingCount;

	@Column(name = "total_mountain_count", nullable = false)
	private int totalMountainCount;

	@Column(name = "total_duration", nullable = false)
	private LocalDateTime totalDuration;

	@Column(name = "total_distance", nullable = false)
	private double totalDistance;

	@Column(name = "total_alt", nullable = false)
	private double totalAlt;

	@Column(name = "participation_rate", nullable = false)
	private double participationRate;

}
