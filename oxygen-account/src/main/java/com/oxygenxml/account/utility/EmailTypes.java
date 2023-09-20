package com.oxygenxml.account.utility;

import lombok.Getter;


@Getter
public enum EmailTypes {

	CONFIRM_REGISTRATION("confirm registration");

	private final String emailType;

	private EmailTypes(String emailType) {
		this.emailType = emailType;
	}
}