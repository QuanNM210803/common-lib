package nmquan.commonlib.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nmquan.commonlib.dto.response.Response;
import nmquan.commonlib.exception.AppException;
import nmquan.commonlib.exception.ErrorCode;
import nmquan.commonlib.model.JwtUser;
import nmquan.commonlib.utils.JwtUtils;
import nmquan.commonlib.utils.LocalizationUtils;
import nmquan.commonlib.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenFilter extends OncePerRequestFilter {
    @Value("${jwt.secret-key}")
    private String SECRET_KEY;
    @Autowired
    private LocalizationUtils localizationUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String jwt = JwtUtils.getToken(request);
            if(jwt != null){
                JwtUser jwtUser = JwtUtils.validate(jwt, SECRET_KEY);
                var authentication = new UsernamePasswordAuthenticationToken(jwtUser.getUser(), jwtUser.getUsername(), jwtUser.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }catch (AppException e){
            ErrorCode errorCode = e.getErrorCode();
            Response<Object> apiResponse = Response.builder()
                    .code(errorCode.getCode())
                    .message(localizationUtils.getLocalizedMessage(errorCode.getMessage(), e.getParams()))
                    .build();

            response.setStatus(errorCode.getStatusCode().value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.getWriter().write(ObjectMapperUtils.convertToJson(apiResponse));
            response.flushBuffer();
            return;
        }catch (Exception e){
            log.error("Authenticated: {}", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}
