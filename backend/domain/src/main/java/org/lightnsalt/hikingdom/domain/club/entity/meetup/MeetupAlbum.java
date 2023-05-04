package org.lightnsalt.hikingdom.domain.club.entity.meetup;

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
import javax.persistence.Table;

import org.lightnsalt.hikingdom.domain.BaseTimeEntity;
import org.lightnsalt.hikingdom.domain.club.entity.Club;
import org.lightnsalt.hikingdom.domain.member.entity.Member;

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
@Table(name = "meetup_album")
public class MeetupAlbum extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "BIGINT UNSIGNED")
	private Long id;

	@ToString.Exclude
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "club_id", nullable = false, columnDefinition = "BIGINT UNSIGNED", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Club club;

	@ToString.Exclude
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "meetup_id", nullable = false, columnDefinition = "BIGINT UNSIGNED", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Meetup meetup;

	@ToString.Exclude
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false, columnDefinition = "BIGINT UNSIGNED", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Member member;

	@Column(name = "img_url", nullable = false, length = 512)
	private String imgUrl;

	@Column(name = "lat")
	private double lat;

	@Column(name = "lng")
	private double lng;

	@Column(name = "alt")
	private double alt;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	@Column(name = "is_deleted", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
	private boolean isDeleted;

	@Column(name = "is_reported", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
	private boolean isReported;

	@Builder
	public MeetupAlbum(Club club, Meetup meetup, Member member, String imgUrl, double lat, double lng, double alt,
		LocalDateTime deletedAt, boolean isDeleted, boolean isReported) {
		this.club = club;
		this.meetup = meetup;
		this.member = member;
		this.imgUrl = imgUrl;
		this.lat = lat;
		this.lng = lng;
		this.alt = alt;
		this.deletedAt = deletedAt;
		this.isDeleted = isDeleted;
		this.isReported = isReported;
	}
}
