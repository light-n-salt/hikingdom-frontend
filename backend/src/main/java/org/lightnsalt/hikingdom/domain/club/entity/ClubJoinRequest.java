package org.lightnsalt.hikingdom.domain.club.entity;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.lightnsalt.hikingdom.domain.BaseTimeEntity;
import org.lightnsalt.hikingdom.domain.club.common.enumtype.JoinRequestStatusType;
import org.lightnsalt.hikingdom.domain.member.entity.Member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Table(name = "club_join_request")
public class ClubJoinRequest extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "BIGINT UNSIGNED")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	@ToString.Exclude
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "club_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	@ToString.Exclude
	private Club club;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private JoinRequestStatusType status;

	@Builder
	public ClubJoinRequest(Member member, Club club, JoinRequestStatusType status) {
		this.member = member;
		this.club = club;
		this.status = status;
	}
}
