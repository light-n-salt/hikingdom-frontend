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
@Table(name = "club_ranking")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

	@Column(name = "total_member_count", nullable = false, columnDefinition = "INT UNSIGNED DEFAULT 1")
	private Long totalMemberCount;

	@Column(name = "total_meetup_count", nullable = false, columnDefinition = "INT UNSIGNED DEFAULT 0")
	private Long totalMeetupCount;

	@Column(name = "total_mountain_count", nullable = false, columnDefinition = "INT UNSIGNED DEFAULT 0")
	private Long totalMountainCount;

	@Column(name = "total_asset_count", nullable = false, columnDefinition = "INT UNSIGNED DEFAULT 0")
	private Long totalAssetCount;

	@Builder
	public ClubRanking(Club club, Long ranking, Long score, LocalDate setDate, Long totalMemberCount,
		Long totalMeetupCount, Long totalMountainCount, Long totalAssetCount) {
		this.club = club;
		this.ranking = ranking;
		this.score = score;
		this.setDate = setDate;
		this.totalMemberCount = totalMemberCount;
		this.totalMeetupCount = totalMeetupCount;
		this.totalMountainCount = totalMountainCount;
		this.totalAssetCount = totalAssetCount;
	}
}
