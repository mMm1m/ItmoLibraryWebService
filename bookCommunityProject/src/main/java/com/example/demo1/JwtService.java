package com.example.demo1;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
	
	@Value("${application.security.jwt.secret-key}")
	private String secretKey;
	@Value("${application.security.jwt.expiration}")
	private long jwtExpiration;
	@Value("${application.security.jwt.refresh-token.expiration}")
	private long refreshExpiration;
	
	public String extractUserLogin(String token)
	{
		return extractClaim(token, Claims::getSubject);
	}
	
	public <T> T extractClaim(String token, Function<Claims , T> claimsResolver)
	{
		final Claims claims = extractAllClaimes(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims extractAllClaimes(String token)
	{
		return Jwts
				.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	private Key getSignInKey()
	{
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	private String buildToken(
	          Map<String, Object> extraClaims,
	          UserDetails userDetails,
	          long expiration
	  ) {
	    return Jwts
	            .builder()
	            .setClaims(extraClaims)
	            .setSubject(userDetails.getUsername())
	            .setIssuedAt(new Date(System.currentTimeMillis()))
	            .setExpiration(new Date(System.currentTimeMillis() + expiration))
	            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
	            .compact();
	  }
	
	public String generateRefreshToken(UserDetails userDetails) {
		    return buildToken(new HashMap<>(), userDetails, refreshExpiration);
		  }
	
	public String generateToken(UserDetails details)
	{
		return generateToken(new HashMap<>(), details);
	}
	
	public String generateToken(Map<String, Object> extraClaims, UserDetails details )
	{
		
		return Jwts.builder()
				.setClaims(extraClaims).setSubject(details.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000*60*24*60))
				.signWith(getSignInKey() , SignatureAlgorithm.HS256)
				.compact();
	}
	
	public boolean isTokenValid(String token , UserDetails details)
	{
		final String userLogin = extractUserLogin(token);
		return (userLogin.equals(details.getUsername()))
				&& !isTokenExpired(token);
		
	}
	
	private Date extractExpiration(String token)
	{
		return extractClaim(token , Claims::getExpiration);
	}
	
	public boolean isTokenExpired(String token)
	{
		return extractExpiration(token).before(new Date());
	}
	
	
}
