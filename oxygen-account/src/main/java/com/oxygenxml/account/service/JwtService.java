package com.oxygenxml.account.service;

import java.sql.Timestamp;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oxygenxml.account.Config.OxygenAccountPorpertiesConfig;
import com.oxygenxml.account.utility.TokenClaims;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * Service class for handling JWT operations such as token generation and parsing.
 */
@Service
public class JwtService {

	/**
     * Instance of OxygenAccountPorpertiesConfig to get the secret key
     */
	@Autowired
	private OxygenAccountPorpertiesConfig oxygenProperties;
	
	/**
	 * Generates a JWT for email confirmation using the specified user ID and account creation date.
     *
	 * @param userId The ID of the user for whom the token is generated
	 * @param accountCreationDate The creation date of the user's account
	 * @return A JWT string
	 */
	public String generateEmailConfirmationToken(Long userId, Timestamp accountCreationDate) {
		SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(oxygenProperties.getSecretKey()));
		
		return Jwts.builder()
				.claim(TokenClaims.USER_ID.getTokenClaims(), userId)
				.claim(TokenClaims.CREATION_DATE.getTokenClaims(), accountCreationDate)
				.signWith(key)
				.compact();
	}
	
	/**
	 * Parses the specified JWT and returns its claims.
	 * 
	 * @param token The JWT to be parsed.
	 * @return The claims extracted from the JWT.
	 */
	public Claims parseToken(String token) {
		SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(oxygenProperties.getSecretKey()));
        
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    
	/**
	 * Retrieves the user ID from the specified JWT.
	 * 
	 * @param token The JWT containing the user ID.
	 * @return The user ID extracted from the JWT.
	 */
    public Long getUserIdFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.get(TokenClaims.USER_ID.getTokenClaims(), Long.class);
    }

    /**
     * Retrieves the account creation date from the specified JWT.
     * 
     * @param token The JWT containing the account creation date
     * @return The account creation date extracted from the JWT
     */
    public Timestamp getCreationDateFromToken(String token) {
        Claims claims = parseToken(token);
        Date creationDate = claims.get(TokenClaims.CREATION_DATE.getTokenClaims(), Date.class);
        return new Timestamp(creationDate.getTime());
    }
}
