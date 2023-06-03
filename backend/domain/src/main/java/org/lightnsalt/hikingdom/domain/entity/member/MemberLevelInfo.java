package org.lightnsalt.hikingdom.domain.entity.member;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "member_level_info")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberLevelInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "TINYINT")
	private Integer id;

	@Column(name = "name", nullable = false, length = 10)
	private String name;

	@Column(name = "description", nullable = false, length = 50)
	private String description;

	@Column(name = "level_condition", nullable = false)
	private int levelCondition;

	@Builder
	public MemberLevelInfo(String name, String description, int levelCondition) {
		this.name = name;
		this.description = description;
		this.levelCondition = levelCondition;
	}
}
