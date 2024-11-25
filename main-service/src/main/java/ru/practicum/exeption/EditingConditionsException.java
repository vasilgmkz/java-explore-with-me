package ru.practicum.exeption;

public class EditingConditionsException extends RuntimeException {
    Status status;

    public EditingConditionsException(String message, Status status) {
        super(message);
        this.status = status;
    }
}
