package ru.practicum.admin.locations.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.admin.locations.validation.MarkerLocations;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationFromConsole {
    @NotEmpty(groups = MarkerLocations.AddLocation.class)
    @NotBlank(groups = MarkerLocations.AddLocation.class)
    @Size(min = 1)
    private String name;
    @NotNull(groups = MarkerLocations.AddLocation.class)
    private Float lat;
    @NotNull(groups = MarkerLocations.AddLocation.class)
    private Float lon;
    @NotNull(groups = MarkerLocations.AddLocation.class)
    private Float radius;
}
