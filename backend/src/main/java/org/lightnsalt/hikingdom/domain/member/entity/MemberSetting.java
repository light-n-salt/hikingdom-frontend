package org.lightnsalt.hikingdom.domain.member.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.lightnsalt.hikingdom.domain.BaseTimeEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@NoArgsConstructor
@Table(name = "member_setting")
public class MemberSetting extends BaseTimeEntity {
	@Id
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@ToString.Exclude
	@JsonIgnore
	@JoinColumn(name = "id", nullable = false, columnDefinition = "BIGINT UNSIGNED")
	private Member member;
}