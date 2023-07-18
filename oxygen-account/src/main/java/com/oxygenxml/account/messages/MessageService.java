package com.oxygenxml.account.messages;

import java.util.HashMap;
import java.util.Map;

	/**
	 * MessageService is a utility class that maps messageId's to their corresponding messages.
	 */

public class MessageService {

	private static Map<String, String> messageMap = new HashMap<>();
	
	static {
		messageMap.put(MessageId.USERNAME_EMPTY.getId(), Messages.EMPTY_USERNAME);
		messageMap.put(MessageId.EMAIL_EMPTY.getId(), Messages.EMPTY_EMAIL);
		messageMap.put(MessageId.EMAIL_INVALID.getId(), Messages.INVALID_EMAIL);
		messageMap.put(MessageId.PASSWORD_EMPTY.getId(), Messages.EMPTY_PASSWORD);
		messageMap.put(MessageId.PASSWORD_INVALID.getId(), Messages.INVALID_PASSWORD);
		messageMap.put(MessageId.EMAIL_ALREADY_EXISTS.getId(), Messages.EMAIL_ALREADY_EXISTS);
		messageMap.put(MessageId.USERNAME_ALREADY_EXISTS.getId(), Messages.USERNAME_ALREADY_EXISTS);
		messageMap.put(MessageId.REGISTRATION_FAILED.getId(), Messages.REGISTRATION_FAILED);
	}
	
	/**
	 * Returns the message corresponding to the provided messageId.
	 *
	 * @param messageId the ID of the message
	 * @return the message corresponding to the messageId, or a default message if no matching messageId is found
	 */
	
	public static String getMessage(String messageId) {
		String message = messageMap.get(messageId);
		if(message == null) {
			message = "Try again later.";
		}
		
		return message;
	}
}
