package ru.practicum.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse argumentNotValidException(final MethodArgumentNotValidException e) {
        log.info("400 {}", e.getMessage());
        return new ErrorResponse(e.getBindingResult().getAllErrors().getFirst().getDefaultMessage());
    }

    @ExceptionHandler(value = {BadRequest.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse defaultHandlerExceptionResolver(Exception e) {
        log.info("400 {}", e.getMessage());
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(final Exception e, HttpStatus status) {
        log.warn("500 { }", e.getMessage(), e);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String stackTrace = sw.toString();
        return new ErrorResponse(e.getMessage() + " " + stackTrace);
    }
}
