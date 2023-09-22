package com.oxygenxml.account.type;

import lombok.Getter;

/**
 * An enumeration representing data used in email templates.
 */
@Getter
public enum EmailTemplateData {
	
	/**
     * Tag for the user's name.
     */
	NAME("name"),

	/**
     * Tag for an email confirmation token.
     */
	TOKEN("token"),
	
    /**
     * Tag for the base URL of the application.
     */
	BASEURL("baseUrl");

	/**
     * The string key used to store the actual tag.
     */
	private final String name;

	/**
	 * Constructs a new enum instance with the specified string key.
	 */
	private EmailTemplateData(String name) {
		this.name = name;
	}
}
