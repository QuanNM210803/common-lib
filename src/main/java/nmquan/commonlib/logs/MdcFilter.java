package nmquan.commonlib.logs;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import nmquan.commonlib.constant.CommonConstants;
import nmquan.commonlib.utils.WebUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class MdcFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        long start = System.currentTimeMillis();
        try {
            String requestId = Optional.ofNullable(request.getHeader(CommonConstants.REQUEST_ID_KEY_HEADER))
                    .orElse(UUID.randomUUID().toString());
            String username = WebUtils.getCurrentUsername();
            if(ObjectUtils.isEmpty(username)){
                username = CommonConstants.ROLE_ANONYMOUS;
            }

            MDC.put(CommonConstants.REQUEST_ID_KEY, requestId);
            MDC.put("path", request.getRequestURI() +
                    (request.getQueryString() != null ? "?" + request.getQueryString() : ""));
            MDC.put("method", request.getMethod());
            MDC.put("username", username);

            filterChain.doFilter(request, response);
        } finally {
            int status = response.getStatus();
            if (status >= 200 && status < 300) {
                long duration = System.currentTimeMillis() - start;
                log.info("Runtime={}ms", duration);
            }
            MDC.clear();
        }
    }
}