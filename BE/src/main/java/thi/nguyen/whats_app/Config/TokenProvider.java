package thi.nguyen.whats_app.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class TokenProvider {
    SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    public String generateToken(Authentication authentication){
        String jwt = Jwts.builder().setIssuer("Code with thinguyen")//Người phát hành
                .setIssuedAt(new Date())//Thời điểm phát hành
                .setExpiration(new Date(new Date().getTime() + 86400000))//Hiệu lực
                .claim("email",authentication.getName())//Thêm claim vào token
                .signWith(key)
                .compact();
                return jwt;
    }

    public String getEmailFromToken(String jwt){
        jwt = jwt.substring(7);
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
        String email = String.valueOf(claims.get("email"));
        return email;
    }


}
