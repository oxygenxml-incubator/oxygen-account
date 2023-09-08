package com.oxygenxml.account.Config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * Configuration class that binds the properties defined in the application's properties file prefixed with "oxygen.account" to this class's fields.
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "oxygen.account")
public class OxygenAccountPorpertiesConfig {

	/**
	 * The number of days until a deleted user is permanently deleted from the system.
	 */
	 private int daysUntilDeletion;
}
