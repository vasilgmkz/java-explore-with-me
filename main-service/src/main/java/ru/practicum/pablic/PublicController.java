package ru.practicum.pablic;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.admin.categories.model.CategoryDto;
import ru.practicum.admin.compilations.dto.CompilationDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class PublicController {

    @Qualifier("publicCategoriesService")
    private final PublicService publicCategoriesService;

    @Qualifier("publicCompilationsService")
    private final PublicService publicCompilationsService;

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
}
