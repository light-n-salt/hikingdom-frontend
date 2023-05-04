package org.lightnsalt.hikingdom.domain.common.enumType;

import lombok.Getter;

/**
 * 소모임 가입 신청 상태
 */
@Getter
public enum JoinRequestStatusType {
	PENDING, // 가입 신청
	RETRACTED, // 가입 신청 취소
	ACCEPTED, // 가입 신청 수락
	REJECTED // 가입 신청 거절
}