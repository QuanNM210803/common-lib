package nmquan.commonlib.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import nmquan.commonlib.model.JwtUser;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.List;

public class JwtUtils {
    public static String getToken(HttpServletRequest request) {
        String headerAuth = (request.getHeader("Authorization") == null || request.getHeader("Authorization").isEmpty())
                ? request.getParameter("token") : request.getHeader("Authorization");
        if (!StringUtils.hasText(headerAuth)) {
            return null;
        }
        if (headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return headerAuth;
    }

    public static JwtUser validate(String token, String secretKey) {
        try{
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSignInKey(secretKey))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            Long userId = Long.valueOf(claims.get("userId", Integer.class));
            List<String> roles = claims.get("roles", List.class);
            String username = claims.getSubject();
            String email = claims.get("email", String.class);
            String fullName = claims.get("fullName", String.class);
            return new JwtUser(userId, roles, email, fullName, claims, username, "", List.of());
        }catch (JwtException e) {
            throw new JwtException("Invalid JWT token" + e.getMessage());
        }
    }

    public static Key getSignInKey(String secretKey) {
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }
}
