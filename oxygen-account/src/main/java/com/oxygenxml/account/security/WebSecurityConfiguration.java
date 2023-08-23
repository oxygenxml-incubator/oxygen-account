package com.oxygenxml.account.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import com.oxygenxml.account.service.OxygenUserDetailsService;

/**
 * Configuration class for web application security.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

	/**
	 * The redirect URL for situations where a user is invalid.
	 */
	private static final String INVALID_USER_URL = "/login#invalid-user";
	/**
	 *  Service for user details retrieval.
	 */
	@Autowired
	private OxygenUserDetailsService userDetailsService;
	
	/**
	 * Instance of BCryptPasswordEncoder used for encoding the password
	 */
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/**
     * Configures authentication using user details and password encoder.
     *
     * @param auth the authentication manager builder
     * @throws Exception if any configuration issues occur
     */
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}
	
	/**
	 * Creates a handler for authentication failures, redirecting the user to the login page with an "#invalid-user" URL hash if authentificatio fails
	 * 
	 * @return the authentification failure handler
	 */
	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
	    SimpleUrlAuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();
	    failureHandler.setDefaultFailureUrl(INVALID_USER_URL);
	    return failureHandler;
	}
	
	@Bean
	public LogoutSuccessHandler logoutSuccessHandler() {
	    SimpleUrlLogoutSuccessHandler successHandler = new SimpleUrlLogoutSuccessHandler();
	    successHandler.setDefaultTargetUrl("/login");
	    return successHandler;
	}
	
	/**
	 * This method defines security like disabling CSRF protection, defining which requests are allowed without authentication
	 * @param http - the HttpSecurity instance
	 * @return the build SecurityFilterChain
	 * @throws Exception if an error occurs during the security configuration
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		.csrf(csrf -> csrf.disable())
		.authorizeHttpRequests(authz->authz
				.requestMatchers("/api/users/register").permitAll()
				.anyRequest().authenticated())
		.formLogin(form -> form
				.loginPage("/login")
				.usernameParameter("email")
			    .passwordParameter("password")
			    .failureHandler(authenticationFailureHandler())
				.permitAll())
		.logout(logout -> logout.logoutSuccessHandler(logoutSuccessHandler()));

		return http.build();
	}
	
	/**
	 *Configures Spring Security to ignore specific URL patterns for security checks.
	 */
	@Bean
	public WebSecurityCustomizer ignoringCustomizer() {
		return web -> web.ignoring()
				.requestMatchers("/app/login.js")
        		.requestMatchers("/img/logo.jpg");
	}
      
}