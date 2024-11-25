package ru.practicum.privates.events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import jdk.jfr.BooleanFlag;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.privates.events.model.Location;
import ru.practicum.privates.events.validate.CheckEventDate;

import java.time.LocalDateTime;

@Builder
@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewEventDto {
    @NotEmpty
    @NotBlank
    @Size(min = 20, max = 2000)
    private String annotation;
    @NotNull
    @Positive
    private Integer category;
    @NotEmpty
    @NotBlank
    @Size(min = 20, max = 7000)
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @CheckEventDate
    @Future
    private LocalDateTime eventDate;
    @NotNull
    @Valid
    private Location location;
    @BooleanFlag
    @Builder.Default
    private boolean paid = false;
    @PositiveOrZero
    @Builder.Default
    private Integer participantLimit = 0;
    @BooleanFlag
    @Builder.Default
    private boolean requestModeration = true;
    @NotEmpty
    @Size(min = 3, max = 120)
    private String title;
}
