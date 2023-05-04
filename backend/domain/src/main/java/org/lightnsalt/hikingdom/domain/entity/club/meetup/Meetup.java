package org.lightnsalt.hikingdom.domain.entity.club.meetup;

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

import org.lightnsalt.hikingdom.domain.entity.club.Club;
import org.lightnsalt.hikingdom.domain.common.BaseTimeEntity;
import org.lightnsalt.hikingdom.domain.entity.info.MountainInfo;
import org.lightnsalt.hikingdom.domain.entity.member.Member;

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
@Table(name = "meetup")
public class Meetup extends BaseTimeEntity {
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
	@JoinColumn(name = "mountain_id", nullable = false, columnDefinition = "BIGINT UNSIGNED", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private MountainInfo mountain;

	@ToString.Exclude
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "host_id", nullable = false, columnDefinition = "BIGINT UNSIGNED", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Member host;

	@Column(name = "name", nullable = false, length = 50)
	private String name;

	@Column(name = "description", length = 512)
	private String description;

	@Column(name = "start_at", nullable = false)
	private LocalDateTime startAt;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	@Column(name = "is_deleted", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
	private boolean isDeleted;

	@Builder
	public Meetup(Club club, MountainInfo mountain, Member host, String name, String description, LocalDateTime startAt,
		LocalDateTime deletedAt, boolean isDeleted) {
		this.club = club;
		this.mountain = mountain;
		this.host = host;
		this.name = name;
		this.description = description;
		this.startAt = startAt;
		this.deletedAt = deletedAt;
		this.isDeleted = isDeleted;
	}
}
