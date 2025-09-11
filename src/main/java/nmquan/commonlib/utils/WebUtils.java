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
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            return null;
        }
        return (Map<String, Object>) authentication.getPrincipal();
    }

    public static String getCurrentUsername() {
        Map<String, Object> currentUser = getCurrentUser();
        return currentUser != null ? (String) currentUser.get("username") : null;
    }

    public static Long getCurrentUserId() {
        Map<String, Object> currentUser = getCurrentUser();
        Object id = currentUser != null ? currentUser.get("id") : null;
        return id != null ? Long.valueOf(id.toString()) : null;
    }

    public static String getCurrentEmail() {
        Map<String, Object> currentUser = getCurrentUser();
        return currentUser != null ? (String) currentUser.get("email") : null;
    }

    public static String getCurrentFullName() {
        Map<String, Object> currentUser = getCurrentUser();
        return currentUser != null ? (String) currentUser.get("fullName") : null;
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
