package com.oxygenxml.account.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oxygenxml.account.Config.OxygenAccountPorpertiesConfig;
import com.oxygenxml.account.type.TokenClaim;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
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
	public String generateEmailConfirmationToken(Integer userId, Timestamp accountCreationDate) {

		Map<TokenClaim, Object> claims = new HashMap<>();
		claims.put(TokenClaim.USER_ID, userId);
		claims.put(TokenClaim.CREATION_DATE, accountCreationDate);

		return generateToken(claims);
	}
	
	/**
	 * Generates a JWT using the specified claims.
	 * 
	 * @param claims A map representing the claims to be included in the token.
	 * @return A JWT string generated with the specified claims and signed with the secret key
	 */
	private String generateToken(Map<TokenClaim, Object> claims) {
		SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(oxygenProperties.getSecretKey()));

		JwtBuilder jwtBuilder = Jwts.builder();

		for (Map.Entry<TokenClaim, Object> claim : claims.entrySet()) {
			jwtBuilder.claim(claim.getKey().getName(), claim.getValue());
		}

		return jwtBuilder
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
}
