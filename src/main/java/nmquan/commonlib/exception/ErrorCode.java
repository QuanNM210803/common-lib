package nmquan.commonlib.exception;

import lombok.Getter;
import nmquan.commonlib.constant.MessageConstants;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    UNAUTHENTICATED(401, MessageConstants.UNAUTHENTICATED, HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED(403, MessageConstants.ACCESS_DENIED, HttpStatus.FORBIDDEN),
    ERROR(500, MessageConstants.ERROR, HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_FORMAT(101, MessageConstants.INVALID_FORMAT, HttpStatus.BAD_REQUEST),
    DATA_NOT_FOUND(102, MessageConstants.DATA_NOTFOUND, HttpStatus.NOT_FOUND),
    ;

    ErrorCode(Integer code, String message, HttpStatus statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final Integer code;
    private final String message;
    private final HttpStatus statusCode;
}
