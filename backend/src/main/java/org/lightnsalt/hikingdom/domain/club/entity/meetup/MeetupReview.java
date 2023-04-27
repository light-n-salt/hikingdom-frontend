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

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "meetup_review")
public class MeetupReview extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "BIGINT UNSIGNED")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "club_id", nullable = false, columnDefinition = "BIGINT UNSIGNED", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	@ToString.Exclude
	private Club club;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "meetup_id", nullable = false, columnDefinition = "BIGINT UNSIGNED", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	@ToString.Exclude
	private Meetup meetup;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false, columnDefinition = "BIGINT UNSIGNED", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	@ToString.Exclude
	private Member member;

	@Column(name = "content", nullable = false, length = 1024)
	private String content;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	@Column(name = "is_deleted", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
	private boolean isDeleted;

	@Column(name = "is_reported", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
	private boolean isReported;

	@Builder
	public MeetupReview(Club club, Meetup meetup, Member member, String content, LocalDateTime deletedAt,
		boolean isDeleted, boolean isReported) {
		this.club = club;
		this.meetup = meetup;
		this.member = member;
		this.content = content;
		this.deletedAt = deletedAt;
		this.isDeleted = isDeleted;
		this.isReported = isReported;
	}
}
