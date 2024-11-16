package ru.practicum.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {
    private String error;

    public ErrorResponse(String message) {
        this.error = message;
    }
}
