package nmquan.commonlib.annotation;

import nmquan.commonlib.constant.CommonConstants;
import nmquan.commonlib.exception.AppException;
import nmquan.commonlib.exception.CommonErrorCode;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class InternalRequestAspect {
    @Value("${jwt.x-internal-token}")
    private String X_INTERNAL_TOKEN;

    @Around("@annotation(nmquan.commonlib.annotation.InternalRequest)")
    public Object handleInternalApi(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new AppException(CommonErrorCode.UNAUTHENTICATED);
        }
        String xInternalToken = attributes.getRequest().getHeader(CommonConstants.X_INTERNAL_TOKEN);
        if (xInternalToken == null || !xInternalToken.equals(X_INTERNAL_TOKEN)) {
            throw new AppException(CommonErrorCode.UNAUTHENTICATED);
        }
        return joinPoint.proceed();
    }
}
