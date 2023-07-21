package com.oxygenxml.account.exceptions;

import org.springframework.http.HttpStatus;
import com.oxygenxml.account.messages.Messages;

import lombok.Getter;

@Getter
public class OxygenAccountExceptions extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	private final HttpStatus status;
	
	private final String messageId;
	
	private final InternalErrorCode errorCode;
	
	public OxygenAccountExceptions(Messages message, HttpStatus status, InternalErrorCode errorCode) {
		super(message.getMessage());
		this.status = status;
		this.messageId = message.getId();
		this.errorCode = errorCode;
	}

	
}
