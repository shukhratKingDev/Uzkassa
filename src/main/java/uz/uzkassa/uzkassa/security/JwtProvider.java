package uz.uzkassa.uzkassa.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class JwtProvider {
    private final long expireTime=60*60*100000000000L;
    private final String key="Secret_key_do_not_share_with_anyone";
    public String generateJwt(String username){
        String jwt = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        return jwt;
    }
    public String getUsernameFromToken(String token){
        try {
            return Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody().getSubject();
        }catch (Exception e){
            return null;
        }
    }
}