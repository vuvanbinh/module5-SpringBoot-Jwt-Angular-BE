package com.codegym.springbootjwtangular.security.jwt;

import com.codegym.springbootjwtangular.security.userPrinciple.UserPrinciple;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtProvider.class);
    private String jwtSecret = "binh.vu@codegym.vn";
    private int jwtExpiration = 86400;

    public String createToken(Authentication authentication){
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return Jwts.builder().setSubject(userPrinciple.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+jwtExpiration*1000))
                .signWith(SignatureAlgorithm.HS512,jwtSecret)
                .compact();
    }
    public Boolean validateJwtToken(String token){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (SignatureException e){
            LOGGER.error("Invalid JWT signature -> Message: {}", e);
        }catch (MalformedJwtException e){
            LOGGER.error("Invalid JWT token -> Message: {}", e);
        }catch (ExpiredJwtException e){
            LOGGER.error("Invalid JWT token -> Message: {}",e);
        }catch (UnsupportedJwtException e){
            LOGGER.error("Unsupported JWT token -> Message: {}",e);
        }catch (IllegalArgumentException e){
            LOGGER.error("JWT claims string is empty -> Message: {}",e);
        }
        return false;
    }

    public String getUsernameFromJwtToken(String token){
        String userName = Jwts
                .parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return userName;
    }



}
