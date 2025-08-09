package nmquan.commonlib.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class WebUtils {
    public static HttpServletRequest getCurrentRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    public static Map<String, Object> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return (Map<String, Object>) authentication.getPrincipal();
    }

    public static String getCurrentUsername() {
        Object username = Objects.requireNonNull(getCurrentUser()).get("username");
        return username == null ? null : username.toString();
    }

    public static Long getCurrentUserId() {
        Object id = Objects.requireNonNull(getCurrentUser()).get("userId");
        if (id == null) {
            id = getCurrentUser().get("id");
        }
        return id == null ? null : Long.parseLong(id.toString());
    }

    public static String getCurrentEmail() {
        Object email = Objects.requireNonNull(getCurrentUser()).get("email");
        return email == null ? null : email.toString();
    }

    public static String getCurrentFullName() {
        Object fullName = Objects.requireNonNull(getCurrentUser()).get("fullName");
        return fullName == null ? null : fullName.toString();
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
