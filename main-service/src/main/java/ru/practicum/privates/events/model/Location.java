package ru.practicum.privates.events.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Location {
    @NotNull
    @Column(name = "event_location_lat")
    private Float lat;
    @NotNull
    @Column(name = "event_location_lon")
    private Float lon;
}
