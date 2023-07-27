package com.oxygenxml.account.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface JsonUtil {

	 /**
	   * Helper method to convert an object to JSON string.
	   */
	  default String asJsonString(final Object obj) throws JsonProcessingException {
		  return new ObjectMapper().writeValueAsString(obj);
	  }
}
