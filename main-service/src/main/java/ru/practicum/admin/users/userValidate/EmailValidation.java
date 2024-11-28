package ru.practicum.admin.users.userValidate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidationValidate.class)
@Documented
public @interface EmailValidation {
    String message() default "Длинна domain - не больше 63, длинна localpart - не больше 64";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
