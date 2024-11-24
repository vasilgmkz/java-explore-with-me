package ru.practicum.privates.events.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.admin.categories.model.CategoryDto;
import ru.practicum.admin.users.model.UserDto;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    @Column(name = "event_annotation")
    private String annotation;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryDto category;
    @Column(name = "event_created_on")
    private LocalDateTime createdOn; //Дата и время создания события (в формате "yyyy-MM-dd HH:mm:ss")
    @Column(name = "event_description")
    private String description; //Полное описание события
    @Column(name = "event_event_date")
    private LocalDateTime eventDate; //Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDto initiator; // Пользователь
    @Embedded
    private Location location;
    @Column(name = "event_paid")
    private Boolean paid;
    @Column(name = "event_participant_limit")
    private Integer participantLimit;
    @Column(name = "event_published_on")
    private LocalDateTime publishedOn;
    @Column(name = "event_request_moderation")
    private Boolean requestModeration;
    @Enumerated(EnumType.STRING)
    @Column(name = "event_state")
    private State state;
    @Column(name = "event_title")
    private String title;
}
