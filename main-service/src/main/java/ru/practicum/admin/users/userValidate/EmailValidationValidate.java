package ru.practicum.admin.users.userValidate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidationValidate implements ConstraintValidator<EmailValidation, String> {

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.isEmpty() || email.isBlank()) {
            return true;
        }
        int lengthEmail = email.substring(0, email.indexOf('@')).length();
        int lengthDomain = email.substring(email.indexOf('@'), email.indexOf(".")).length() - 1;
        if (lengthEmail <= 64 && lengthDomain <= 63) {
            return true;
        } else {
            return false;
        }
    }
}
