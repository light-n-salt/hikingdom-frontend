package org.lightnsalt.hikingdom.entity.common.enumType;

import org.springframework.security.core.GrantedAuthority;

public enum MemberRoleType implements GrantedAuthority {
	ROLE_ADMIN, // 관리자 권한
	ROLE_USER // 일반 사용자
	;
	
	public String getAuthority() {
		return name();
	}
}
