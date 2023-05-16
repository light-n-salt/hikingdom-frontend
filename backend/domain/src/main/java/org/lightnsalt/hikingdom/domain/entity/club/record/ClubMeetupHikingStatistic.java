package org.lightnsalt.hikingdom.domain.entity.club.record;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.lightnsalt.hikingdom.domain.entity.club.Club;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.lightnsalt.hikingdom.domain.entity.club.meetup.Meetup;

@Entity
@Table(name = "club_meetup_hiking_statistic")
@Getter
@ToString
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubMeetupHikingStatistic {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "BIGINT UNSIGNED")
	private Long id;

	@ToString.Exclude
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "club_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Club club;

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

	@ToString.Exclude
	@JsonIgnore
	@MapsId
	@OneToOne
	@JoinColumn(name = "meetup_id", nullable = false, columnDefinition = "BIGINT UNSIGNED")
	private Meetup meetup;

	@Builder
	public ClubMeetupHikingStatistic(Club club, Long totalHikingCount, Long totalMountainCount, Long totalDuration,
									 Long totalDistance, Long totalAlt, double participationRate, Meetup meetup) {
		this.club = club;
		this.totalHikingCount = totalHikingCount;
		this.totalMountainCount = totalMountainCount;
		this.totalDuration = totalDuration;
		this.totalDistance = totalDistance;
		this.totalAlt = totalAlt;
		this.participationRate = participationRate;
		this.meetup = meetup;
	}
}
