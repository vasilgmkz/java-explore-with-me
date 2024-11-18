package ru.practicum.exeption;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiError {
    Status status;
    String reason;
    String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime timestamp;

    public ApiError(String reason, String message, Status status) {
        this.status = status;
        this.reason = reason;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
