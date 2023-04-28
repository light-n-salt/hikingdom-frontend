package org.lightnsalt.hikingdom.domain.member.entity;

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
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_level_info")
public class MemberLevelInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "TINYINT")
	private Integer id;

	@Column(name = "name", nullable = false, length = 10)
	private String name;

	@Column(name = "description", nullable = false, length = 50)
	private String description;

	@Builder
	public MemberLevelInfo(String name, String description) {
		this.name = name;
		this.description = description;
	}
}
