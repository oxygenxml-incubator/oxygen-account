package com.oxygenxml.account.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration class for web application security.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

	/**
	 * This password encoder use BCryptoPasswordEncoder to encode the password
	 * @return a BCryptoPasswordEncoder
	 */
	@Bean("encoder")
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	/**
	 * This method defines security like disabling CSRF protection, defining which requests are allowed without authentification
	 * @param http - the HttpSecurity instance
	 * @return the build SecurityFilterChain
	 * @throws Exception if an error occurs during the security configuration
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        	http
        		.csrf((csrf) -> csrf.disable())
        		.authorizeHttpRequests(authz->authz
        		.requestMatchers("/api/users/register").permitAll()
        		.anyRequest().authenticated())
        		.formLogin((form) -> form
        				.loginPage("/login")
        				.permitAll());
        	
        return http.build();
    }
	
	/**
	 *Configures Spring Security to ignore specific URL patterns for security checks.
	 */
	@Bean
	public WebSecurityCustomizer ignoringCustomizer() {
		return web -> web.ignoring()
				.requestMatchers("/app/login.js")
        		.requestMatchers("/app/63c72bb6ba828872ed1d09fd3d8f83d6.jpg");
	}
      
}