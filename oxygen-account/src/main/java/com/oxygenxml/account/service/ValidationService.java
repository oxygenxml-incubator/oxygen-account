package com.oxygenxml.account.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.oxygenxml.account.exception.InputValidationError;
import com.oxygenxml.account.exception.MultipleOxygenAccountException;
import com.oxygenxml.account.messages.Message;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

/**
 * Service class responsible for validating objects against a set of predefined constraints.
 */
@Service
@Scope("singleton")
public class ValidationService {

	/**
	 * An instance of a Validator, injected by Spring. This is used to validate objects.
	 */
	@Autowired
	private Validator validator ;

	/**
	 * A static map used to translate constraint violation messages to application-specific message IDs.
	 */
	private static final Map<String, Message> MESSAGE_ID_MAP = new HashMap<>();

	static {

		MESSAGE_ID_MAP.put("{jakarta.validation.constraints.Email.message}", Message.EMAIL_INVALID);
		MESSAGE_ID_MAP.put("{jakarta.validation.constraints.NotBlank.message}", Message.EMPTY_FIELD);
		MESSAGE_ID_MAP.put("{jakarta.validation.constraints.Size.message}", Message.SHORT_FIELD);
	}

	/**
	 * Validates the given object, throwing an exception if any constraints are violated.
	 *
	 * @param object the object to validate
	 * @throws MultipleOxygenAccountException if any constraints are violated
	 */
	public void validate(Object object) {

		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);

		if (!constraintViolations.isEmpty()) {
			List<InputValidationError> errorMessages = new ArrayList<>();
			for(ConstraintViolation<Object> violation : constraintViolations){
				String messageTemplate = violation.getMessageTemplate();
				Message message = MESSAGE_ID_MAP.getOrDefault(messageTemplate, Message.BAD_REQUEST);
				errorMessages.add(new InputValidationError( violation.getPropertyPath().toString(), message.getMessage(), message.getId()));
			}
			throw new MultipleOxygenAccountException(errorMessages);
		}
	}
}
