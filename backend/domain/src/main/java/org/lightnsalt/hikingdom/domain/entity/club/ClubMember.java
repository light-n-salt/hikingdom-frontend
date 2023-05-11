package org.lightnsalt.hikingdom.domain.entity.club;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.lightnsalt.hikingdom.domain.common.BaseTimeEntity;
import org.lightnsalt.hikingdom.domain.entity.member.Member;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "club_member")
@Getter
@ToString
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "UPDATE club_member SET is_withdraw = true, withdraw_at = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "is_withdraw = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubMember extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "BIGINT UNSIGNED")
	private Long id;

	@ToString.Exclude
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@ToString.Exclude
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "club_id")
	private Club club;

	@Column(name = "withdraw_at")
	private LocalDateTime withdrawAt;

	@Column(name = "is_withdraw", columnDefinition = "BOOLEAN DEFAULT 0")
	private boolean isWithdraw;

	@Builder
	public ClubMember(Member member, Club club, LocalDateTime withdrawAt, boolean isWithdraw) {
		this.member = member;
		this.club = club;
		this.withdrawAt = withdrawAt;
		this.isWithdraw = isWithdraw;
	}
}
