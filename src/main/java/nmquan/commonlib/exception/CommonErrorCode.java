package nmquan.commonlib.exception;

import nmquan.commonlib.constant.MessageConstants;
import org.springframework.http.HttpStatus;

public enum CommonErrorCode implements ErrorCode{
    UNAUTHENTICATED(401, MessageConstants.UNAUTHENTICATED, HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED(403, MessageConstants.ACCESS_DENIED, HttpStatus.FORBIDDEN),
    ERROR(500, MessageConstants.ERROR, HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_FORMAT(101, MessageConstants.INVALID_FORMAT, HttpStatus.BAD_REQUEST),
    DATA_NOT_FOUND(102, MessageConstants.DATA_NOTFOUND, HttpStatus.NOT_FOUND),
    TOKEN_EXPIRED(103, MessageConstants.TOKEN_EXPIRED, HttpStatus.UNAUTHORIZED),
    TOKEN_INVALID(104, MessageConstants.TOKEN_INVALID, HttpStatus.UNAUTHORIZED)
    ;

    CommonErrorCode(Integer code, String message, HttpStatus statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final Integer code;
    private final String message;
    private final HttpStatus statusCode;

    @Override
    public HttpStatus getStatusCode() { return statusCode; }

    @Override
    public Integer getCode() { return code; }

    @Override
    public String getMessage() { return message; }
}
