package com.oxygenxml.account.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.oxygenxml.account.OxygenAccountApplication;
import com.oxygenxml.account.service.JwtService;
import com.oxygenxml.account.utility.DateUtility;

/**
 * The JwtServiceTest class tests the functionality of JwtService
 *
 */
@SpringBootTest(classes=OxygenAccountApplication.class)
@ActiveProfiles("test")
public class JwtServiceTest {

	/**
	 * Instance of JwtService to interact with the methods from JwtService.
	 */
	@Autowired
	private JwtService jwtService;
	
	/**
	 * Tests that the token generation method functions correctly.
	 */
	@Test
	void testGenerateToken() throws Exception {
		
		Long userId = 1L;
		Timestamp accountCreationDate = DateUtility.getCurrentUTCTimestamp();

		String token = jwtService.generateEmailConfirmationToken(userId, accountCreationDate);

		assertNotNull(token);

		Long extractedUserId = jwtService.getUserIdFromToken(token);
		assertEquals(userId, extractedUserId);

		Timestamp extractedCreationDate = jwtService.getCreationDateFromToken(token);

		long timeDifferenceInMillis = Math.abs(accountCreationDate.getTime() - extractedCreationDate.getTime());
		assertTrue(timeDifferenceInMillis < 1);
	}
}
