package ru.practicum;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StatFromConsoleDto {
    @NotNull
    String app;
    @NotNull
    String uri;
    @NotNull
    String ip;
    @NotNull
    String timestamp;
}
