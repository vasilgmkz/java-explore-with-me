package ru.practicum.exeption;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {


    @ExceptionHandler
    public ResponseEntity<ApiError> argumentNotValidException(final MethodArgumentNotValidException e) {
        if (e.getBindingResult().getFieldErrors().getFirst().getRejectedValue() == null) {
            log.info("400 {}", e.getMessage());
            String reason = "Incorrectly made request.";
            String message = "Field: " + e.getFieldError().getField()
                    + ". Error: " + e.getFieldError().getDefaultMessage()
                    + ". Value: " + e.getBindingResult().getFieldErrors().getFirst().getRejectedValue();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(reason, message, Status.BAD_REQUEST));
        } else {
            log.info("409 {}", e.getMessage());
            String reason = "For the requested operation the conditions are not met.";
            String message = "Field: " + e.getFieldError().getField()
                    + ". Error: " + e.getFieldError().getDefaultMessage()
                    + ". Value: " + e.getBindingResult().getFieldErrors().getFirst().getRejectedValue();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiError(reason, message, Status.FORBIDDEN));
        }
    }

    @ExceptionHandler(value = {jakarta.validation.ConstraintViolationException.class, ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError argumentNotValidException(Exception e) {
        log.info("409 {}", e.getMessage());
        String reason = "Integrity constraint has been violated.";
        String message = e.getLocalizedMessage();
        return new ApiError(reason, message, Status.CONFLICT);
    }

    @ExceptionHandler (value = {MethodArgumentTypeMismatchException.class, BadRequest.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError defaultHandlerExceptionResolver(Exception e) {
        log.info("400 {}", e.getMessage());
        String reason = "Incorrectly made request.";
        String message = e.getMessage();
        return new ApiError(reason, message, Status.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError notFoundException(final NotFoundException e) {
        log.info("404 {}", e.getMessage());
        String reason = "The required object was not found.";
        String message = e.getMessage();
        return new ApiError(reason, message, Status.NOT_FOUND);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError editingConditionsException(EditingConditionsException e) {
        log.info("409 {}", e.getMessage());
        String reason = "For the requested operation the conditions are not met.";
        String message = e.getMessage();
        return new ApiError(reason, message, e.status);
    }
}

