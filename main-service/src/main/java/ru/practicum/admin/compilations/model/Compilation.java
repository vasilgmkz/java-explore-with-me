package ru.practicum.admin.compilations.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.privates.events.model.EventDto;

import java.util.List;

@Entity
@Table(name = "compilations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Compilation {
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "compilations_events",
            joinColumns = @JoinColumn(name = "compilation_id", referencedColumnName = "compilation_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id", referencedColumnName = "event_id"))
    private List<EventDto> events;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "compilation_id")
    private Integer id;
    @Column(name = "compilation_pinned")
    private Boolean pinned;
    @Column(name = "compilation_title")
    private String title;
}
