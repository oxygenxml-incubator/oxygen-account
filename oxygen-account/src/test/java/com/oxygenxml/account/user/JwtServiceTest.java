package com.oxygenxml.account.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.oxygenxml.account.OxygenAccountApplication;
import com.oxygenxml.account.messages.TokenClaims;
import com.oxygenxml.account.service.JwtService;
import com.oxygenxml.account.utility.DateUtility;

import io.jsonwebtoken.Claims;

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
		
		Integer userId = 1;
		Timestamp accountCreationDate = DateUtility.getCurrentUTCTimestamp();

		String token = jwtService.generateEmailConfirmationToken(userId, accountCreationDate);

		assertNotNull(token);
		
		Claims claims = jwtService.parseToken(token);

		Integer userIdExtracted = claims.get(TokenClaims.USER_ID.getTokenClaims(), Integer.class);
		assertEquals(userId, userIdExtracted);

		Date extractedCreationDate = claims.get(TokenClaims.CREATION_DATE.getTokenClaims(), Date.class);
		
		long timeDifferenceInMillis = Math.abs(accountCreationDate.getTime() - new Timestamp(extractedCreationDate.getTime()).getTime());
		assertTrue(timeDifferenceInMillis < 1);
	}
}
