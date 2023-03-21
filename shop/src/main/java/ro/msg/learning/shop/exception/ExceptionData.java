package ro.msg.learning.shop.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ExceptionData {

    private final String message;
    private final HttpStatus status;
    private final LocalDateTime dateTime;
}
