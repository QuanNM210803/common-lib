package nmquan.commonlib.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import nmquan.commonlib.constant.CommonConstants;
import nmquan.commonlib.exception.AppException;
import nmquan.commonlib.exception.CommonErrorCode;
import nmquan.commonlib.model.JwtUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.*;
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
        return null;
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
            List<GrantedAuthority> roleAuthorities = roles == null ? Collections.emptyList() : roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            Map<String, List<String>> permissionMap = claims.get("permissions", Map.class);
            List<GrantedAuthority> permissionAuthorities = permissionMap == null ? Collections.emptyList() : permissionMap.entrySet().stream()
                    .flatMap(entry -> entry.getValue().stream()
                        .map(value -> new SimpleGrantedAuthority(entry.getKey() + ":" + value)))
                    .collect(Collectors.toList());

            List<GrantedAuthority> authorities = new ArrayList<>(roleAuthorities);
            authorities.addAll(permissionAuthorities);

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

    public static String generateTokenInternal(String secretKey) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", List.of(CommonConstants.ROLE_INTERNAL));

        Long id = WebUtils.getCurrentUserId();
        String username = WebUtils.getCurrentUsername() != null ? WebUtils.getCurrentUsername() : CommonConstants.INTERNAL;
        Long orgId = WebUtils.getCurrentOrgId();

        Map<String, Object> userDto = new HashMap<>();
        userDto.put("id", id);
        userDto.put("username", username);
        userDto.put("orgId", orgId);
        claims.put("user", ObjectMapperUtils.convertToJson(userDto));
        try {
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(username)
                    .setExpiration(new Date(System.currentTimeMillis() + 30 * 1000L))
                    .signWith(getSignInKey(secretKey), SignatureAlgorithm.HS256)
                    .compact();
        }
        catch (Exception e) {
            throw new AppException(CommonErrorCode.ERROR);
        }
    }
}
