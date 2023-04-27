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
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "club")
public class Club extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "BIGINT UNSIGNED")
	private Long id;

	@Column(nullable = false, length = 20)
	private String name;
	@Column(length = 512)
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "host_id")
	@ToString.Exclude
	private Member host;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dong_code")
	@ToString.Exclude
	private BaseAddressInfo baseAddressInfo;

	@Builder
	public Club(String name, String description, Member host, BaseAddressInfo baseAddressInfo) {
		this.name = name;
		this.description = description;
		this.host = host;
		this.baseAddressInfo = baseAddressInfo;
	}
}
