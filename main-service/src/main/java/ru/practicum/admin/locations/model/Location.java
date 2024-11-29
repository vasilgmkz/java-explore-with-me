package ru.practicum.admin.locations.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "locations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Integer id;
    @Column(name = "location_name", unique = true)
    private String name;
    @Column(name = "location_lat")
    private Float lat;
    @Column(name = "location_lon")
    private Float lon;
    @Column(name = "location_radius")
    private Float radius;
    @Column(name = "location_created_on")
    private LocalDateTime created;
}
