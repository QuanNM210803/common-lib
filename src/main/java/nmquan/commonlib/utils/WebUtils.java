package nmquan.commonlib.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import nmquan.commonlib.dto.BaseDto;
import nmquan.commonlib.dto.Identifiable;
import nmquan.commonlib.dto.response.FilterResponse;
import nmquan.commonlib.exception.AppException;
import nmquan.commonlib.exception.CommonErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class WebUtils {
    public static HttpServletRequest getCurrentRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    public static Map<String, Object> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            return null;
        }
        log.info("Current User Principal: {}", authentication.getPrincipal());
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

    public static Long getCurrentOrgId() {
        Map<String, Object> currentUser = getCurrentUser();
        Object orgId = currentUser != null ? currentUser.get("orgId") : null;
        return orgId != null ? Long.valueOf(orgId.toString()) : null;
    }

    public static Long checkCurrentOrgId(){
        Long orgId = WebUtils.getCurrentOrgId();
        if(Objects.isNull(orgId)){
            throw new AppException(CommonErrorCode.ACCESS_DENIED);
        }
        return orgId;
    }

    public static <T extends Identifiable,G extends Identifiable> List<Long> getDeleteIds(List<T> newObjects, List<G> oldObjects){
        if(Objects.isNull(newObjects)){
            newObjects = new ArrayList<>();
        }
        if(Objects.isNull(oldObjects)){
            oldObjects = new ArrayList<>();
        }
        List<Long> idsInNew = newObjects.stream()
                .map(T::getId)
                .filter(Objects::nonNull)
                .toList();
        return oldObjects.stream()
                .map(G::getId)
                .filter(id -> !idsInNew.contains(id))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static <T extends BaseDto<Instant>> List<T> configResponsePublic(List<T> dtos){
        if(Objects.nonNull(dtos)){
            for(T dto : dtos){
                dto.setIsActive(null);
                dto.setIsDeleted(null);
                dto.setCreatedBy(null);
                dto.setUpdatedBy(null);
                dto.setCreatedAt(null);
                dto.setUpdatedAt(null);
            }
        }
        return dtos;
    }

    public static <T extends BaseDto<Instant>> FilterResponse<T> configResponsePublic(FilterResponse<T> response){
        if(Objects.nonNull(response) && Objects.nonNull(response.getData())){
            for(T dto : response.getData()){
                dto.setIsActive(null);
                dto.setIsDeleted(null);
                dto.setCreatedBy(null);
                dto.setUpdatedBy(null);
                dto.setCreatedAt(null);
                dto.setUpdatedAt(null);
            }
        }
        return response;
    }

}
