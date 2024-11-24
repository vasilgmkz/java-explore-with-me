package ru.practicum.admin.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserShortDtoFromConsole {
    @Email
    @NotEmpty
    String email;
    @NotEmpty
    String name;
}
