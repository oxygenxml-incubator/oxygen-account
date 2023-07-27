package com.oxygenxml.account.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * JsonUtil is a utility class that provides methods for converting Java objects to JSON representation
 *
 */
public class JsonUtil {

	 /**
	  * Helper method to convert an object to JSON string.
	  * 
	  * @param obj The object to convert to JSON.
	  * @return The JSON representation of the object.
	  * @throws JsonProcessingException If an error occurs during the JSON processing.
	  */
	  public static String asJsonString(final Object obj) throws JsonProcessingException {
		  return new ObjectMapper().writeValueAsString(obj);
	  }
}
