package top.news.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import top.news.dto.security.JwtDTO;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JwtUtil {
    private static final int tokenLiveTime = 1000 * 3600; // 1-hour
    private static String secretKey;

    @Value("${jwt.secret.key}")
    public void setSecretKey(String key) {
        JwtUtil.secretKey = key;
    }

    public static String encode(JwtDTO jwt) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("id", jwt.getId());
        extraClaims.put("name", jwt.getName());
        extraClaims.put("surname", jwt.getSurname());
        extraClaims.put("roles", jwt.getRoles());

        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(jwt.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenLiveTime))
                .signWith(getSignInKey())
                .compact();
    }

    public static JwtDTO decode(String token) {
        Claims claims = Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        String username = claims.getSubject();
        Integer id = (Integer) claims.get("id");
        String name = (String) claims.get("name");
        String surname = (String) claims.get("surname");
        List<String> roles = (List<String>) claims.get("roles");
        return new JwtDTO(id, name, surname, username, roles);
    }

    private static SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
