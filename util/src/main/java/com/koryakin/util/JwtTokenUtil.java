package com.koryakin.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;

import static com.koryakin.util.Constants.ACCESS_TOKEN_VALIDITY_SECONDS;
import static com.koryakin.util.Constants.SIGNING_KEY;
import static com.koryakin.util.Constants.ACCESS_TOKEN_VALIDITY_SECONDS;
import static com.koryakin.util.Constants.SIGNING_KEY;

@Component
public class JwtTokenUtil implements Serializable {

    public static String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public static Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public static <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private static Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private static Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public static String generateToken(UserDetails user) {
        return doGenerateToken(user.getUsername(), user.getAuthorities());
    }

    private static String doGenerateToken(String subject,Collection<? extends GrantedAuthority> roles) {

        Claims claims = Jwts.claims().setSubject(subject);
        claims.put("scopes", roles);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("http://koryakin.com")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS*1000))
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .compact();
    }

    static Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (
              username.equals(userDetails.getUsername())
                    && !isTokenExpired(token));
    }

}
