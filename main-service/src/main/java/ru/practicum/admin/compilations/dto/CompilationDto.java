package ru.practicum.admin.compilations.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.privates.events.dto.EventShortDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompilationDto {
    private List<EventShortDto> events;
    private Integer id;
    private Boolean pinned;
    private String title;
}
