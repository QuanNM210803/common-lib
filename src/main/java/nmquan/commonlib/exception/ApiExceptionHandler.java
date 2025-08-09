package nmquan.commonlib.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nmquan.commonlib.dto.response.Response;
import nmquan.commonlib.utils.LocalizationUtils;
import nmquan.commonlib.utils.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;
import java.util.Objects;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ApiExceptionHandler {
    private final LocalizationUtils localizationUtils;

    @ExceptionHandler({AppException.class})
    protected ResponseEntity<Response<Object>> handleAppException(AppException appException) {
        log.error("AppException: {}", appException.getMessage(), appException);
        ErrorCode errorCode = appException.getErrorCode();

        // for error from service other
        if(errorCode == null) {
            Response<Object> responseError = appException.getResponse();
            responseError.setMessage(this.formatMessage(responseError.getMessage(), appException.getParams()));
            return ResponseEntity.status(appException.getStatusCode()).body(responseError);
        }
        String message = this.formatMessage(errorCode.getMessage(), appException.getParams());
        return ResponseUtils.error(errorCode.getStatusCode(), message, errorCode.getCode());
    }

    // validate error
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Response<Object>> handleValidationException(MethodArgumentNotValidException ex) {
        log.error("Validate error: {}", ex.getMessage(), ex);
        String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        CommonErrorCode errorCode = CommonErrorCode.INVALID_FORMAT;

        //handle PARAM parse error (for param)
        String[] errorCodes = ex.getBindingResult().getAllErrors().get(0).getCodes();
        if (Arrays.asList(Objects.requireNonNull(errorCodes)).contains("typeMismatch")) {
            message = errorCode.getMessage();
        }

        message = this.formatMessage(message);
        return ResponseUtils.error(errorCode.getStatusCode(), message, errorCode.getCode());
    }

    // authorization
    @ExceptionHandler({AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Response<Object>> handleAccessDeniedException(AccessDeniedException ex) {
        log.error("AccessDeniedException: {}", ex.getMessage(), ex);
        CommonErrorCode errorCode= CommonErrorCode.ACCESS_DENIED;
        String message = this.formatMessage(errorCode.getMessage());
        return ResponseUtils.error(errorCode.getStatusCode(), message, errorCode.getCode());
    }

    // JSON parse error (for request body)
    @ExceptionHandler({InvalidFormatException.class, HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response<Object>> handleInvalidFormatException(Exception ex) {
        log.error("JSON parse error: {}", ex.getMessage(), ex);
        CommonErrorCode errorCode= CommonErrorCode.INVALID_FORMAT;
        String message = this.formatMessage(errorCode.getMessage());
        return ResponseUtils.error(errorCode.getStatusCode(), message, errorCode.getCode());
    }

    @ExceptionHandler({RuntimeException.class, Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Response<Object>> handleException(Exception ex) {
        log.error("Exception: {}", ex.getMessage(), ex);
        CommonErrorCode errorCode= CommonErrorCode.ERROR;
        String message = this.formatMessage(errorCode.getMessage());
        return ResponseUtils.error(errorCode.getStatusCode(), message, errorCode.getCode());
    }

    private String formatMessage(String message, Object... params) {
        try {
            message = localizationUtils.getLocalizedMessage(message, params);
        }catch (Exception e){

        }
        return message;
    }
}
