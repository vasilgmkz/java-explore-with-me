package ru.practicum.pablic;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.admin.categories.model.CategoryDto;
import ru.practicum.admin.compilations.dto.CompilationDto;
import ru.practicum.privates.events.dto.EventFullDto;
import ru.practicum.privates.events.dto.EventShortDto;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class PublicController {

    @Qualifier("publicCategoriesService")
    private final PublicService publicCategoriesService;

    @Qualifier("publicCompilationsService")
    private final PublicService publicCompilationsService;

    @Qualifier("publicEventsService")
    private final PublicService publicEventsService;

    @GetMapping("/categories")
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDto> getCategories(@RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Long from, @RequestParam(name = "size", defaultValue = "10") @PositiveOrZero Long size) {
        return publicCategoriesService.getCategories(from, size);
    }

    @GetMapping("/categories/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto getCategoryId(@PathVariable("catId") Long catId) {
        return publicCategoriesService.getCategoryId(catId);
    }

    @GetMapping("/compilations")
    @ResponseStatus(HttpStatus.OK)
    public List<CompilationDto> getCompilations(@RequestParam(name = "from", defaultValue = "0") @Valid @PositiveOrZero Long from,
                                                @RequestParam(name = "size", defaultValue = "10") @Valid @PositiveOrZero Long size,
                                                @RequestParam(name = "pinned", required = false) Boolean pinned) {
        return publicCompilationsService.getCompilations(from, size, pinned);
    }

    @GetMapping("/compilations/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto getCompilationById(@PathVariable("compId") @Valid @Positive Long compId) {
        return publicCompilationsService.getCompilationById(compId);
    }

    @GetMapping("/events")
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> getEvents(@RequestParam(name = "text", required = false) @Size(min = 1, max = 7000) String text,
                                         @RequestParam(name = "categories", required = false) List<Integer> categories,
                                         @RequestParam(name = "paid", required = false) Boolean paid,
                                         @RequestParam(required = false, name = "rangeStart") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                         @RequestParam(required = false, name = "rangeEnd") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                         @RequestParam(defaultValue = "false", name = "onlyAvailable") Boolean onlyAvailable,
                                         @RequestParam(required = false, name = "sort") String sort,
                                         @RequestParam(name = "from", defaultValue = "0") @Valid @PositiveOrZero Long from,
                                         @RequestParam(name = "size", defaultValue = "10") @Valid @PositiveOrZero Long size) {
        return publicEventsService.getEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
    }

    @GetMapping("/events/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto getEventsById(@PathVariable("id") @Valid @Positive Long id) {
        return publicEventsService.getEventsById(id);
    }
}
