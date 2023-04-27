package org.lightnsalt.hikingdom.domain.member.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.lightnsalt.hikingdom.domain.info.entity.MountainInfo;

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
@Table(name = "member_hiking")
public class MemberHiking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, columnDefinition = "BIGINT UNSIGNED")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "member_id",columnDefinition = "BIGINT UNSIGNED")
	private Member member;

	@ManyToOne
	@JoinColumn(name = "mountain_id", columnDefinition = "BIGINT UNSIGNED")
	private MountainInfo mountain;

	// TODO: 일정 JoinColumn 추가
	// @ManyToOne
	// @JoinColumn(name="meetup_id", columnDefinition = "BIGINT UNSIGNED")
	// private Meetup meetup;

	@Column(name = "is_club", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
	private Boolean isGroup;

	@Column(name = "start_at", nullable = false)
	private LocalDateTime startAt;

	@Column(name = "end_at", nullable = false)
	private LocalDateTime endAt;

	@Column(name = "total_duration", nullable = false, columnDefinition = "INT UNSIGNED")
	private Long totalDuration;

	@Column(name = "total_distance", nullable = false)
	private double totalDistance;

	@Column(name = "total_alt", nullable = false)
	private double totalAlt;

}
