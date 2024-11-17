package ru.practicum.exeption;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiError {
    Status status;
    String reason;
    String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime timestamp;

    public ApiError(MethodArgumentNotValidException e, Status status) {
        this.status = status;
        this.reason = e.getBody().getDetail();
        this.message = "Field: " + e.getFieldError().getField() + ". Error: " + e.getFieldError().getDefaultMessage() + ". Value: " + e.getBindingResult().getFieldErrors().getFirst().getRejectedValue();
        this.timestamp = LocalDateTime.now();
    }
}
