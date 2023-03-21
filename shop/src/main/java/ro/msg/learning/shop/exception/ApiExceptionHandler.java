package ro.msg.learning.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ro.msg.learning.shop.exception.type.BadRequestException;
import ro.msg.learning.shop.exception.type.ConflictException;
import ro.msg.learning.shop.exception.type.ForbiddenException;
import ro.msg.learning.shop.exception.type.NotFoundException;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ConflictException.class})
    public ResponseEntity<Object> handleApiConflictException(ShopAppException e) {
        HttpStatus httpStatus = HttpStatus.CONFLICT;
        ExceptionData exceptionData = new ExceptionData(
                e.getMessage(),
                httpStatus,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(exceptionData, httpStatus);
    }

    @ExceptionHandler(value = {BadRequestException.class,})
    public ResponseEntity<Object> handleApiBadRequestException(ShopAppException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ExceptionData exceptionData = new ExceptionData(
                e.getMessage(),
                httpStatus,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(exceptionData, httpStatus);
    }

    @ExceptionHandler(value = {ForbiddenException.class})
    public ResponseEntity<Object> handleApiForbiddenException(ShopAppException e) {
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        ExceptionData exceptionData = new ExceptionData(
                e.getMessage(),
                httpStatus,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(exceptionData, httpStatus);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleApiNotFoundException(ShopAppException e) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ExceptionData exceptionData = new ExceptionData(
                e.getMessage(),
                httpStatus,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(exceptionData, httpStatus);
    }
}
