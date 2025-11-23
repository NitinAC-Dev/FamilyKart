package com.identify.product.FamilyKart.security.jwt;

import com.identify.product.FamilyKart.security.jwt.service.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;


@Component // spring manages to create object for this class
public class JWTUtils {

    public static final Logger logger = LoggerFactory.getLogger(JWTUtils.class);

    @Value("${spring.app.jwtExpirationMs}")
    private long JWT_EXPIRATION;

    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;

    @Value("${spring.app.jwt.cookieName}")
    private String jwtCookie;

    //getting jwt token from the request header

    public String getJwtCookie(HttpServletRequest request)
    {


        Cookie cookie= WebUtils.getCookie(request,jwtCookie);
        if(cookie!=null)
        {
            return cookie.getValue();
        }
        else
        {
            return null;
        }
    }
    public ResponseCookie generateCookie(UserDetailsImpl userDetails)
    {
        String jwtToken=generateTokenFromUsername(userDetails);
        return ResponseCookie.from(jwtCookie,jwtToken)
                .path("/api")
                .maxAge(24*60*60) //1 day
                .httpOnly(true)
                .build();
    }
    public ResponseCookie getCleanJwtCookie() {
      return  ResponseCookie.from(jwtCookie,null)
                .path("/api")
                .build();

    }

   public String getJWTFromHeader(HttpServletRequest request) {

        String bearerHeader = request.getHeader("Authorization");
        logger.debug("Authorization header: {}", bearerHeader);
        if (bearerHeader != null && bearerHeader.startsWith("Bearer ")) {
            return bearerHeader.substring(7);//remove Bearer and space from the token
        } else
            return null;

    }


    //getting token from username
    public String generateTokenFromUsername(UserDetails userdetails) {
        String username = userdetails.getUsername();
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                (new Date().getTime() + JWT_EXPIRATION)
                        )
                )
                .signWith(key())
                .compact();
    }

    //getting username from the jwt token
    public String getUsernameFromJWT(String token) {
//       return Jwts.parser()
//                .setSigningKey((SecretKey)key())
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();
        //or
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    //generating signing key
    public Key key() {
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret)
        );
    }


    //validating jwt token
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) key())
                    .build()
                    .parseSignedClaims(authToken);
            return true;

        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException ex) {
            logger.error("JWT token is expired: {}", ex.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }


}
