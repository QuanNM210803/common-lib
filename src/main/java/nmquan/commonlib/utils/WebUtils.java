package nmquan.commonlib.utils;

import jakarta.servlet.http.HttpServletRequest;
import nmquan.commonlib.model.JwtUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class WebUtils {
    public static HttpServletRequest getCurrentRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    public static JwtUser getCurrentUserCustom() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof JwtUser) {
            return (JwtUser) principal;
        }
        return null;
    }

    public static List<String> getCurrentRole(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication) || !authentication.isAuthenticated()) {
            return null;
        }
        Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();
        return roles.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }

}
