package org.lightnsalt.hikingdom.domain.entity.member;

import java.time.LocalDateTime;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.lightnsalt.hikingdom.domain.common.enumType.MemberRoleType;
import org.lightnsalt.hikingdom.domain.common.BaseTimeEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "member")
@Getter
@ToString
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "UPDATE member SET is_withdraw = true WHERE id = ?")
@Where(clause = "is_withdraw = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "BIGINT UNSIGNED")
	private Long id;

	@ToString.Exclude
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "level_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private MemberLevelInfo level;

	@Column(name = "email", nullable = false, length = 100)
	private String email;

	@Column(name = "password", nullable = false, length = 128)
	private String password;

	@Column(name = "nickname", nullable = false, length = 20)
	private String nickname;

	@Column(name = "profile_url", length = 512)
	private String profileUrl;

	@Column(name = "withdraw_at")
	private LocalDateTime withdrawAt;

	@Column(name = "is_withdraw", columnDefinition = "BOOLEAN DEFAULT 0")
	private boolean isWithdraw;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	private MemberRoleType role;

	@ToString.Exclude
	@JsonIgnore
	@OneToOne(mappedBy = "member")
	private MemberHikingStatistic hikingStatistic;

	@Builder
	public Member(MemberLevelInfo level, String email, String password, String nickname, String profileUrl,
		LocalDateTime withdrawAt, boolean isWithdraw, MemberRoleType role, MemberHikingStatistic hikingStatistic) {
		this.level = level;
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.profileUrl = profileUrl;
		this.withdrawAt = withdrawAt;
		this.isWithdraw = isWithdraw;
		this.role = role;
		this.hikingStatistic = hikingStatistic;
	}
}
