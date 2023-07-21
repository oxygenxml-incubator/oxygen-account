package com.oxygenxml.account.exceptions;

import lombok.Getter;

@Getter
public enum InternalErrorCode {
	
	EMAIL_ALREADY_EXISTS(1);
	
	private final int errorCode;
	
	InternalErrorCode(int errorCode){
		this.errorCode = errorCode;
	}

}
