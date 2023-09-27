package com.oxygenxml.account.events;

import com.oxygenxml.account.model.User;

import lombok.Getter;

import org.springframework.context.ApplicationEvent;

/**
 *  Represents a registration event within the application.
 */
@Getter
public class RegistrationEvent extends ApplicationEvent {

	/**
	 * Unique ID used in serialization to verify that the sender and receiver of a serialized object maintain compatibility.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The user for whom this registration event is associated.
	 */
	private final User user;

	/**
	 * Constructs a new RegistrationEvent with the given source and associated user.
	 * 
	 * @param source the object on which the event initially occurred (never {@code null})
	 * @param user the user for whom this registration event is associated
	 */
    public RegistrationEvent(Object source, User user) {
        super(source);
        this.user = user;
    }
}