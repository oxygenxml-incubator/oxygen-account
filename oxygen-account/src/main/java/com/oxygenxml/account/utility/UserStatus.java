package com.oxygenxml.account.utility;

import lombok.Getter;

@Getter
public enum UserStatus {
	
	DELETED("Deleted"),
	
	ACTIVE("Active");
	
	private final String status;

	private UserStatus(String status) {
		this.status = status;
	}

}
