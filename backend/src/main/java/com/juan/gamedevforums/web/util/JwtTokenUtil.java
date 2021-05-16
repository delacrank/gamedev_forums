package com.juan.gamedevforums.web.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;

@Service
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = 234234523523L;

    private final String SECRET = "zdtlD3JK56m6wTTgsNFhqzjqP";
    private final int SECONDS =  1000 * 60 * 60 * 10;

    public String getEmailFromToken(final String token) {
	return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationFromToken(final String token) {
	return getClaimFromToken(token, Claims::getExpiration);
    }
    
    public <T> T getClaimFromToken(final String token, final Function<Claims, T> claimsResolver) {
	final Claims claims = getAllClaimsFromToken(token);
	return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(final String token) {
	return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
	Map<String, Object> claims = new HashMap<>();
	return getExpirationFromToken(token).before(new Date());
    }

    public String generateToken(final UserDetails userDetails) {
	Map<String, Object> claims = new HashMap<>();
	return createToken(claims, userDetails.getUsername());
    }

    public String createToken(Map<String, Object> claims, String subject) {
	return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + SECONDS))
	    .signWith(SignatureAlgorithm.HS256, SECRET).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
	final String email = getEmailFromToken(token);
	return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
