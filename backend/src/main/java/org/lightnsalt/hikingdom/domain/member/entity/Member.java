package org.lightnsalt.hikingdom.domain.member.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
@Table(name = "member")
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, columnDefinition = "BIGINT UNSIGNED")
	private Long id;

	@Column(name = "email", nullable = false, length = 100)
	private String email;

	@Column(name = "password", nullable = false, length = 128)
	private String password;

	@Column(name = "nickname", nullable = false, length = 20)
	private String nickname;

	@Column(name = "profile_url", length = 512)
	private String profileUrl;

	@Column(name = "withdraw_at")
	private LocalDateTime modifiedAt;

	@Column(name = "is_withdraw", columnDefinition = "BOOLEAN")
	private int isWithdraw;

	@ManyToOne(optional = false)
	@JoinColumn(name = "level_id")
	private MemberLevelInfo level;

}
