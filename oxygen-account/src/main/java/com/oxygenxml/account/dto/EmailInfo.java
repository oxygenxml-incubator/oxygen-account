package com.oxygenxml.account.dto;

import java.util.Map;
import com.oxygenxml.account.messages.EmailTypes;
import lombok.Data;

/**
 * An object representing email information.
 */
@Data
public class EmailInfo {

	/**
	 * The type of the email.
	 */
	private EmailTypes type;

	/**
	 * The email address of the recipient.
	 */
	private String emailAddress;

	/**
	 * Data for constructing the email content.
	 */
	private Map<String, Object> emailData;
}
