package ru.practicum.pablic;

import ru.practicum.admin.categories.model.CategoryDto;
import ru.practicum.admin.compilations.dto.CompilationDto;
import ru.practicum.privates.events.dto.EventFullDto;
import ru.practicum.privates.events.dto.EventShortDto;

import java.time.LocalDateTime;
import java.util.List;

public interface PublicService {
    default List<CategoryDto> getCategories(Long from, Long size) {
        return null;
    }

    default CategoryDto getCategoryId(Long catId) {
        return null;
    }

    default List<CompilationDto> getCompilations(Long from, Long size, Boolean pinned) {
        return null;
    }

    default CompilationDto getCompilationById(Long compId) {
        return null;
    }

    default List<EventShortDto> getEvents(String text, List<Integer> categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, Long from, Long size, String ip, String uri, String locationName) {
        return null;
    }

    default EventFullDto getEventsById(Long id, String ip, String uri) {
        return null;
    }
}
