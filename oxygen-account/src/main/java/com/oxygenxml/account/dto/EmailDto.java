package com.oxygenxml.account.dto;

import java.util.Map;

import lombok.Data;

@Data
public class EmailDto {
	private String type;
	private String subject;
	private String emailAddress;
	private Map<String, Object> emailData;
}
