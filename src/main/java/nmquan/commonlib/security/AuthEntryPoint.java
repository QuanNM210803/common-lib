package nmquan.commonlib.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nmquan.commonlib.dto.response.Response;
import nmquan.commonlib.exception.CommonErrorCode;
import nmquan.commonlib.utils.LocalizationUtils;
import nmquan.commonlib.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {
    @Autowired
    private LocalizationUtils localizationUtils;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        CommonErrorCode errorCode = CommonErrorCode.UNAUTHENTICATED;
        Response<Object> apiResponse = Response.builder()
                .code(errorCode.getCode())
                .message(localizationUtils.getLocalizedMessage(errorCode.getMessage()))
                .build();

        response.setStatus(errorCode.getStatusCode().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(ObjectMapperUtils.convertToJson(apiResponse));
        response.flushBuffer();
    }
}
