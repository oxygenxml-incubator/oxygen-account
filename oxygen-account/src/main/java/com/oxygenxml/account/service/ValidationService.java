package com.oxygenxml.account.service;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.oxygenxml.account.exception.InternalErrorCode;
import com.oxygenxml.account.exception.OxygenAccountException;
import com.oxygenxml.account.messages.Messages;


import java.util.HashMap;
import java.util.Map;

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
    private static final Map<String, Messages> MESSAGE_ID_MAP = new HashMap<>();

    static {

        MESSAGE_ID_MAP.put("{jakarta.validation.constraints.Email.message}", Messages.EMAIL_INVALID);
        MESSAGE_ID_MAP.put("{jakarta.validation.constraints.NotBlank.message}", Messages.EMPTY_FIELD);
        MESSAGE_ID_MAP.put("{jakarta.validation.constraints.Size.message}", Messages.SHORT_FIELD);
    }
   
    /**
     * Validates the given object, throwing an exception if any constraints are violated.
     *
     * @param object the object to validate
     * @throws OxygenAccountException if any constraints are violated
     */
    public void validate(Object object) {
    	
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);

        if (!constraintViolations.isEmpty()) {
            ConstraintViolation<Object> violation = constraintViolations.iterator().next();
            generateErrorMessage(violation);
        }
    }

    /**
     * Generates an error message based on the provided constraint violation.
     *
     * @param messageTemplate the message template of the constraint violation
     * @return the corresponding message ID, or a default message ID if the template is not found in the map
     */
    private void generateErrorMessage(ConstraintViolation<?> violation) {    	
        String messageTemplate = violation.getMessageTemplate();
        Messages message = MESSAGE_ID_MAP.getOrDefault(messageTemplate, Messages.BAD_REQUEST);        
        throw new OxygenAccountException(message, HttpStatus.UNPROCESSABLE_ENTITY, InternalErrorCode.THE_CURRENT_REQUEST_VALIDATION_FAILED);
    }
      

}
