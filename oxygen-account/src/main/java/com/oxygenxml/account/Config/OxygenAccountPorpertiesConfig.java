package com.oxygenxml.account.Config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

/**
 * Configuration class that binds the properties defined in the application's properties file prefixed with "oxygen.account" to this class's fields.
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "oxygen.account")
public class OxygenAccountPorpertiesConfig {

	/**
	 * The number of days until a deleted user is permanently deleted from the system.
	 */
	 private int daysUntilDeletion;
	 
	 /**
	  * The secret key for JWT Token
	  */
	 private String secretKey;
}
