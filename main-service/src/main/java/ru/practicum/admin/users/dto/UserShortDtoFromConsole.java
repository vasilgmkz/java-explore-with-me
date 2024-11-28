package ru.practicum.admin.users.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import ru.practicum.admin.users.userValidate.EmailValidation;

@Data
public class UserShortDtoFromConsole {
    @NotEmpty
    @NotBlank
    @NotNull
    @Size(min = 6, max = 254)
    @EmailValidation
    @Email
    String email;
    @NotEmpty
    @NotBlank
    @Size(min = 2, max = 250)
    String name;
}
