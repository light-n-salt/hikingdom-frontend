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
@Table(name = "member_hiking_gps")
public class MemberHikingGps {
	@Id
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@ToString.Exclude
	@JsonIgnore
	@JoinColumn(name = "id", nullable = false, columnDefinition = "BIGINT UNSIGNED")
	private MemberHiking hiking;

	@Column(name = "gps_route", nullable = false, columnDefinition = "JSON")
	private String gpsRoute;
}
