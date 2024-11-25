package ru.practicum.privates.events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import jdk.jfr.BooleanFlag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.privates.events.model.Location;
import ru.practicum.privates.events.model.StateAction;

import java.time.LocalDateTime;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEventAdminRequest {
    @Size(min = 20, max = 2000)
    private String annotation;
    @Positive
    private Integer category;
    @Size(min = 20, max = 7000)
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Future
    private LocalDateTime eventDate;
    private Location location;
    @BooleanFlag
    private Boolean paid;
    @PositiveOrZero
    private Integer participantLimit;
    @BooleanFlag
    private Boolean requestModeration;
    private StateAction stateAction;
    @Size(min = 3, max = 120)
    private String title;
}
