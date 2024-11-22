package ru.practicum.privates.events.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class CheckEventDateValidate implements ConstraintValidator<CheckEventDate, LocalDateTime> {
    @Override
    public boolean isValid(LocalDateTime localDateTime, ConstraintValidatorContext context) {
        if (localDateTime == null) {
            return true;
        }
        return localDateTime.isAfter(LocalDateTime.now().plusHours(2));
    }
}
