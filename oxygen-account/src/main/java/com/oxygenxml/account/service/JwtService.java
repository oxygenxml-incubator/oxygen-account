package com.oxygenxml.account.service;

import java.sql.Timestamp;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oxygenxml.account.Config.OxygenAccountPorpertiesConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	@Autowired
	private OxygenAccountPorpertiesConfig oxygenProperties;
	
	public String generateToken(Long userId, Timestamp accountCreationDate) {
		SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(oxygenProperties.getSecretKey()));
		
		return Jwts.builder()
				.setSubject(userId.toString())
				.claim("creationDate", accountCreationDate)
				.signWith(key)
				.compact();
	}
	
	public Claims parseToken(String token) {
		SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(oxygenProperties.getSecretKey()));
        
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    
    public Long getUserIdFromToken(String token) {
        Claims claims = parseToken(token);
        return Long.parseLong(claims.getSubject());
    }

    public Timestamp getCreationDateFromToken(String token) {
        Claims claims = parseToken(token);
        Date creationDate = claims.get("creationDate", Date.class);
        return new Timestamp(creationDate.getTime());
    }
}
