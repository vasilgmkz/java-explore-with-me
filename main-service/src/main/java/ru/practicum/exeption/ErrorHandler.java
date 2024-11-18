package ru.practicum.exeption;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError argumentNotValidException(final MethodArgumentNotValidException e) {
        log.info("400 {}", e.getMessage());
        String reason = "Incorrectly made request.";
        String message = "Field: " + e.getFieldError().getField()
                + ". Error: " + e.getFieldError().getDefaultMessage()
                + ". Value: " + e.getBindingResult().getFieldErrors().getFirst().getRejectedValue();
        return new ApiError(reason, message, Status.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError defaultHandlerExceptionResolver(final MethodArgumentTypeMismatchException e) {
        log.info("400 {}", e.getMessage());
        String reason = "Incorrectly made request.";
        String message = e.getMessage();
        return new ApiError(reason, message, Status.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError argumentNotValidException(final ConstraintViolationException e) {
        log.info("409 {}", e.getMessage());
        String reason = "Integrity constraint has been violated.";
        String message = e.getSQLException().getLocalizedMessage();
        return new ApiError(reason, message, Status.CONFLICT);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError notFoundException(final NotFoundException e) {
        log.info("404 {}", e.getMessage());
        String reason = "The required object was not found.";
        String message = e.getMessage();
        return new ApiError(reason, message, Status.NOT_FOUND);
    }
}

