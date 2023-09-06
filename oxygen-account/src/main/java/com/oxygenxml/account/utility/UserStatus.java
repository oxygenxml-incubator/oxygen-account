package com.oxygenxml.account.utility;

import lombok.Getter;

@Getter
public enum UserStatus {
	
	DELETED("deleted"),
	
	ACTIVE("active");
	
	private final String status;

	private UserStatus(String status) {
		this.status = status;
	}

}
