package org.lightnsalt.hikingdom.domain.club.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.lightnsalt.hikingdom.domain.BaseTimeEntity;
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
@Table(name = "club_member")
public class ClubMember extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "BIGINT UNSIGNED")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	@ToString.Exclude
	private Member member;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "club_id")
	@ToString.Exclude
	private Club club;

	@Builder
	public ClubMember(Member member, Club club) {
		this.member = member;
		this.club = club;
	}
}
