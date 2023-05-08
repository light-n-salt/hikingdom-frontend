package org.lightnsalt.hikingdom.domain.entity.club.record;

import java.time.LocalDate;

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

import org.lightnsalt.hikingdom.domain.entity.club.Club;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Table(name = "club_ranking")
public class ClubRanking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "BIGINT UNSIGNED")
	private Long id;

	@ToString.Exclude
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "club_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Club club;

	@Column(nullable = false, columnDefinition = "BIGINT UNSIGNED")
	private Long ranking;

	@Column(nullable = false, columnDefinition = "BIGINT UNSIGNED")
	private Long score;

	@Column(nullable = false)
	private LocalDate setDate;

	@Column(name = "total_hiking_count", nullable = false, columnDefinition = "INT UNSIGNED DEFAULT 0")
	private Long totalHikingCount;

	@Column(name = "total_mountain_count", nullable = false, columnDefinition = "INT UNSIGNED DEFAULT 0")
	private Long totalMountainCount;

	@Column(name = "total_duration", nullable = false, columnDefinition = "INT UNSIGNED DEFAULT 0")
	private Long totalDuration; // in seconds

	@Column(name = "total_distance", nullable = false, columnDefinition = "INT UNSIGNED DEFAULT 0")
	private Long totalDistance; // in metres

	@Column(name = "total_alt", nullable = false, columnDefinition = "INT UNSIGNED DEFAULT 0")
	private Long totalAlt; // in metres

	@Column(name = "participation_rate", nullable = false, columnDefinition = "DOUBLE DEFAULT 0")
	private double participationRate; // in %

	@Builder
	public ClubRanking(Club club, Long ranking, Long score, LocalDate setDate, Long totalHikingCount,
		Long totalMountainCount, Long totalDuration, Long totalDistance, Long totalAlt, double participationRate) {
		this.club = club;
		this.ranking = ranking;
		this.score = score;
		this.setDate = setDate;
		this.totalHikingCount = totalHikingCount;
		this.totalMountainCount = totalMountainCount;
		this.totalDuration = totalDuration;
		this.totalDistance = totalDistance;
		this.totalAlt = totalAlt;
		this.participationRate = participationRate;
	}
}
