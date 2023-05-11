package org.lightnsalt.hikingdom.domain.entity.member;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.lightnsalt.hikingdom.domain.common.BaseTimeEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "member_setting")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSetting extends BaseTimeEntity {
	@Id
	private Long id;

	@ToString.Exclude
	@JsonIgnore
	@MapsId
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id", columnDefinition = "BIGINT UNSIGNED")
	private Member member;

	@Builder
	public MemberSetting(Member member) {
		this.member = member;
	}
}
