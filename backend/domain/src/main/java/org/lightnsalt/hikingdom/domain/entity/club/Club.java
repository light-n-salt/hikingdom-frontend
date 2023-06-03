package org.lightnsalt.hikingdom.domain.entity.club;

import java.time.LocalDateTime;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.lightnsalt.hikingdom.domain.common.BaseTimeEntity;
import org.lightnsalt.hikingdom.domain.entity.club.record.ClubTotalHikingStatistic;
import org.lightnsalt.hikingdom.domain.entity.info.BaseAddressInfo;
import org.lightnsalt.hikingdom.domain.entity.member.Member;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "club")
@Getter
@ToString
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Club extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "BIGINT UNSIGNED")
	private Long id;

	@ToString.Exclude
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "host_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Member host;

	@ToString.Exclude
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dong_code", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private BaseAddressInfo baseAddress;

	@Column(nullable = false, length = 20)
	private String name;

	@Column(length = 512)
	private String description;

	@Column(name = "total_member_count", nullable = false, columnDefinition = "INT UNSIGNED DEFAULT 1")
	private Long totalMemberCount;

	@Column(name = "total_meetup_count", nullable = false, columnDefinition = "INT UNSIGNED DEFAULT 0")
	private Long totalMeetupCount;

	@Column(name = "total_mountain_count", nullable = false, columnDefinition = "INT UNSIGNED DEFAULT 0")
	private Long totalMountainCount;

	@Column(name = "total_asset_count", nullable = false, columnDefinition = "INT UNSIGNED DEFAULT 0")
	private Long totalAssetCount;

	@Column(name = "score", nullable = false, columnDefinition = "INT UNSIGNED DEFAULT 0")
	private Long score;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	@Column(name = "is_deleted", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
	private boolean isDeleted;

	@ToString.Exclude
	@JsonIgnore
	@OneToMany(mappedBy = "club")
	private List<ClubAsset> assets;

	@ToString.Exclude
	@JsonIgnore
	@OneToOne(mappedBy = "club")
	private ClubTotalHikingStatistic hikingStatistic;

	@Builder
	public Club(Member host, BaseAddressInfo baseAddress, String name, String description, Long totalMemberCount,
		Long totalMeetupCount, Long totalMountainCount, Long totalAssetCount, Long score, LocalDateTime deletedAt,
		boolean isDeleted, List<ClubAsset> assets, ClubTotalHikingStatistic hikingStatistic) {
		this.host = host;
		this.baseAddress = baseAddress;
		this.name = name;
		this.description = description;
		this.totalMemberCount = totalMemberCount;
		this.totalMeetupCount = totalMeetupCount;
		this.totalMountainCount = totalMountainCount;
		this.totalAssetCount = totalAssetCount;
		this.score = score;
		this.deletedAt = deletedAt;
		this.isDeleted = isDeleted;
		this.assets = assets;
		this.hikingStatistic = hikingStatistic;
	}
}
