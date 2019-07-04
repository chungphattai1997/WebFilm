package com.phattai.models;

import org.springframework.security.core.GrantedAuthority;

public class AdminRole implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4945115343405333283L;

	@Override
	public String getAuthority() {
		return "ROLE_ADMIN";
	}

}
