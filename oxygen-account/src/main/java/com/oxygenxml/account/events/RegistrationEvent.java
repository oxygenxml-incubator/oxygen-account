package com.oxygenxml.account.events;

import com.oxygenxml.account.model.User;

import lombok.Getter;

import org.springframework.context.ApplicationEvent;

@Getter
public class RegistrationEvent extends ApplicationEvent {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final User user;

    public RegistrationEvent(Object source, User user) {
        super(source);
        this.user = user;
    }
}