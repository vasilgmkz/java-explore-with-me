package ru.practicum.privates.events.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckEventDateValidate.class)
@Documented
public @interface CheckEventDate {
    String message() default "Дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
