package com.example.pocketmark.security.provider;

import com.example.pocketmark.domain.User;
import io.jsonwebtoken.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private static final String AUTHORITIES_CLAIM = "authorities";
    private static final String TOKEN_TYPE_CLAIM = "tokenType";
    private static final String SECRET_KEY = "whoKnowsMyKey?blahblah";

    // deploy - 15분
    // developOnly - 6 hours
    // public static long ACCESS_TOKEN_EXPIRATION_TIME = 900000L;
    public static long ACCESS_TOKEN_EXPIRATION_TIME = Duration.ofMinutes(60*6).toMillis();

    // 24주
    public static long REFRESH_TOKEN_EXPIRATION_TIME = 604800000L;



    private static final long AUTH_TIME = 2;
    private static final long REFRESH_TIME = 60*60*24*7;


    public String generateAccessToken(User user) {
        Instant expiryDate = Instant.now().plusMillis(ACCESS_TOKEN_EXPIRATION_TIME);
        String authorities = getUserAuthorities(user);
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(expiryDate))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .claim(AUTHORITIES_CLAIM, authorities)
                .claim(TOKEN_TYPE_CLAIM, TokenType.ACCESS_TOKEN.toString())
                .compact();


    }

    private String getUserAuthorities(User user) {
        return user
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }

    public String generateRefreshToken(User user, String jti) {
        Instant expiryDate = Instant.now().plusMillis(REFRESH_TOKEN_EXPIRATION_TIME);
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(expiryDate))
                .setId(jti)
                .claim(TOKEN_TYPE_CLAIM, TokenType.REFRESH_TOKEN.toString())
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public Date getTokenExpiryFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
        } catch (SignatureException ex) {
            throw ex;
        } catch (MalformedJwtException ex) {
            throw ex;
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (UnsupportedJwtException ex) {
            throw ex;
        } catch (IllegalArgumentException ex) {
            throw ex;
        }
        return true;
    }

    public String getTokenTypeFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return (String) claims.get(TOKEN_TYPE_CLAIM);
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    // authenticationService.authenticate(req) 의 return 타입을 바꾸긴 애매해서
    // 여기에 추가합니다..... 좋은 의견있으시면 의견부탁드립니다.
    public static Long getUserId(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return Long.valueOf(claims.getSubject());
    } 

    public List<GrantedAuthority> getAuthoritiesFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return Arrays.stream(claims.get(AUTHORITIES_CLAIM).toString().split(","))
                .map( role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }


    public enum TokenType {

        REFRESH_TOKEN("refresh_token"),
        ACCESS_TOKEN("access_token");

        private final String tokenType;

        private TokenType(String tokenType) {
            this.tokenType = tokenType;
        }

        public static TokenType fromString(String roleName) throws IllegalArgumentException {
            return Arrays.stream(TokenType.values())
                    .filter(x -> x.tokenType.equals(roleName))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unknown value: " + roleName));
        }

        @Override
        public String toString() {
            return tokenType;
        }

    }
}
