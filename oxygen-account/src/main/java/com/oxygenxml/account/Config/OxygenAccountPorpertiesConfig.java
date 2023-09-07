package com.oxygenxml.account.Config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "oxygen.account")
public class OxygenAccountPorpertiesConfig {

	 private int daysUntilDeletion;
}
