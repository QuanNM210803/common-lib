package nmquan.commonlib.annotation;

import nmquan.commonlib.constant.CommonConstants;
import nmquan.commonlib.exception.AppException;
import nmquan.commonlib.exception.CommonErrorCode;
import nmquan.commonlib.model.JwtUser;
import nmquan.commonlib.utils.JwtUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collection;

@Aspect
@Component
public class InternalRequestAspect {
    @Value("${jwt.secret-internal}")
    private String SECRET_INTERNAL_KEY;

    @Around("@annotation(nmquan.commonlib.annotation.InternalRequest)")
    public Object handleInternalApi(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new AppException(CommonErrorCode.UNAUTHENTICATED);
        }
        String internalToken = attributes.getRequest().getHeader("Authorization");
        JwtUser jwtUser = JwtUtils.validate(internalToken, SECRET_INTERNAL_KEY);
        Collection<GrantedAuthority> authorities = jwtUser.getAuthorities();
        if (authorities.stream().noneMatch(auth -> auth.getAuthority().equals(CommonConstants.ROLE_INTERNAL))) {
            throw new AppException(CommonErrorCode.UNAUTHENTICATED);
        }
        var authentication = new UsernamePasswordAuthenticationToken(jwtUser.getUser(), jwtUser.getUsername(), authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return joinPoint.proceed();
    }
}
