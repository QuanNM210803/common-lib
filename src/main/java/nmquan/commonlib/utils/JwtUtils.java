package nmquan.commonlib.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import nmquan.commonlib.exception.AppException;
import nmquan.commonlib.exception.CommonErrorCode;
import nmquan.commonlib.model.JwtUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            String username = claims.getSubject();
            List<String> roles = claims.get("roles", List.class);
            List<GrantedAuthority> authorities = roles == null ? Collections.emptyList() : roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            String user = claims.get("user", String.class);
            Map<String, Object> userObj = user == null ? null : ObjectMapperUtils.convertToMap(user);

            return new JwtUser(userObj, username, "", authorities);
        }catch (ExpiredJwtException e) {
            throw new AppException(CommonErrorCode.TOKEN_EXPIRED);
        }catch (JwtException e) {
            throw new AppException(CommonErrorCode.TOKEN_INVALID);
        }catch (Exception e) {
            throw new AppException(CommonErrorCode.ERROR);
        }
    }

    public static Key getSignInKey(String secretKey) {
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }
}
