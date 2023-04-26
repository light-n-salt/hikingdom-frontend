package org.lightnsalt.hikingdom.domain.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "member_hiking_statistic")
public class MemberHikingStatistic {
	@Id
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@ToString.Exclude
	@JsonIgnore
	@JoinColumn(name = "id", nullable = false, columnDefinition = "BIGINT UNSIGNED")
	private Member member;

	@Column(name = "total_hiking_count", nullable = false)
	private int totalHikingCount;

	@Column(name = "total_mountain_count", nullable = false)
	private int totalMountainCount;

	@Column(name = "total_duration", nullable = false, columnDefinition = "INT UNSIGNED")
	private int totalDuration;

	@Column(name = "total_distance", nullable = false)
	private double totalDistance;

	@Column(name = "total_alt", nullable = false)
	private double totalAlt;

}
