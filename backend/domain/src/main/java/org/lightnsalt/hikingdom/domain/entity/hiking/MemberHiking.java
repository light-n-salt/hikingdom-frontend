package org.lightnsalt.hikingdom.domain.entity.hiking;

import java.time.LocalDateTime;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.lightnsalt.hikingdom.domain.entity.member.Member;
import org.lightnsalt.hikingdom.domain.entity.club.meetup.Meetup;
import org.lightnsalt.hikingdom.domain.entity.info.MountainInfo;

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
@Table(name = "member_hiking")
public class MemberHiking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "BIGINT UNSIGNED")
	private Long id;

	@ToString.Exclude
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Member member;

	@ToString.Exclude
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mountain_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private MountainInfo mountain;

	@ToString.Exclude
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "meetup_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Meetup meetup;

	@Column(name = "is_meetup", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
	private Boolean isMeetup;

	@Column(name = "start_at", nullable = false)
	private LocalDateTime startAt;

	@Column(name = "end_at", nullable = false)
	private LocalDateTime endAt;

	@Column(name = "total_duration", nullable = false, columnDefinition = "INT UNSIGNED")
	private Long totalDuration; // in seconds

	@Column(name = "total_distance", nullable = false, columnDefinition = "INT UNSIGNED")
	private Long totalDistance; // in metres

	@Column(name = "total_alt", nullable = false, columnDefinition = "INT UNSIGNED")
	private Long totalAlt; // in metres

	@Column(name = "is_summit", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
	private Boolean isSummit;

	@OneToOne(mappedBy = "hiking")
	@ToString.Exclude
	private MemberHikingGps gpsRoute;

	@Builder
	public MemberHiking(Member member, MountainInfo mountain, Meetup meetup, Boolean isMeetup, LocalDateTime startAt,
		LocalDateTime endAt, Long totalDuration, Long totalDistance, Long totalAlt, Boolean isSummit) {
		this.member = member;
		this.mountain = mountain;
		this.meetup = meetup;
		this.isMeetup = isMeetup;
		this.startAt = startAt;
		this.endAt = endAt;
		this.totalDuration = totalDuration;
		this.totalDistance = totalDistance;
		this.totalAlt = totalAlt;
		this.isSummit = isSummit;
	}
}
