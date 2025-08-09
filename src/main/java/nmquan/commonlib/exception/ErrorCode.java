package nmquan.commonlib.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    HttpStatus getStatusCode();
    Integer getCode();
    String getMessage();
}
