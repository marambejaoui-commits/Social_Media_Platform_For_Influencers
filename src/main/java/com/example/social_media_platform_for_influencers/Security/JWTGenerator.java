package com.example.social_media_platform_for_influencers.Security;

import com.example.social_media_platform_for_influencers.entities.User;
import com.example.social_media_platform_for_influencers.repositories.UserRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JWTGenerator {
    @Autowired
    UserRepo userRepo;
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String generatorToken(Authentication authentication) {
        String email=authentication.getName();
        Date currentDate=new Date();
        Date expiredDate=new Date(currentDate.getTime()+SecurityConstants.JWT_EXPIRATION);
        Collection<?extends GrantedAuthority> authorities =authentication.getAuthorities();
        List<String> roles=authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        User user=userRepo.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User not found :"+email));
        String token= Jwts.builder()
                .setSubject(email)
                .claim("user", Map.of(
                        "id",user.getUserId(),
                        "username",user.getUsername(),
                        "email",user.getEmail()
                ))
                .claim("roles",roles)
                .setIssuedAt(new Date())
                .setExpiration(expiredDate)
                .signWith(key,SignatureAlgorithm.HS512)
                .compact();
        return token;
    }
    public  String getEmailFromJWT(String token){
        Claims claims=Jwts.parserBuilder()
                .setSigningKey(key).build()
                .parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
    public boolean validateToken(String token){
        try {

            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch (Exception e ){
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect",e.fillInStackTrace());
        }
    }


}


