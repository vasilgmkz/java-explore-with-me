package ru.practicum.exeption;

public class BadRequest extends RuntimeException {
    public BadRequest(String message) {
        super(message);
    }
}
