package org.lightnsalt.hikingdom.domain.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
@Table(name = "member_hiking_statistic")
public class MemberHikingStatistic {
	@Id
	private Long id;

	@ToString.Exclude
	@JsonIgnore
	@MapsId
	@OneToOne
	@JoinColumn(name = "id", columnDefinition = "BIGINT UNSIGNED")
	private Member member;

	@Column(name = "total_hiking_count", nullable = false, columnDefinition = "INT UNSIGNED")
	private Long totalHikingCount;

	@Column(name = "total_mountain_count", nullable = false, columnDefinition = "INT UNSIGNED")
	private Long totalMountainCount;

	@Column(name = "total_duration", nullable = false, columnDefinition = "INT UNSIGNED")
	private Long totalDuration; // in seconds

	@Column(name = "total_distance", nullable = false, columnDefinition = "INT UNSIGNED")
	private Long totalDistance; // in metres

	@Column(name = "total_alt", nullable = false, columnDefinition = "INT UNSIGNED")
	private double totalAlt; // in metres

	@Builder
	public MemberHikingStatistic(Member member, Long totalHikingCount, Long totalMountainCount, Long totalDuration,
		Long totalDistance, double totalAlt) {
		this.member = member;
		this.totalHikingCount = totalHikingCount;
		this.totalMountainCount = totalMountainCount;
		this.totalDuration = totalDuration;
		this.totalDistance = totalDistance;
		this.totalAlt = totalAlt;
	}
}
