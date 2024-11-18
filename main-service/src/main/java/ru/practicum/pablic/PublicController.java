package ru.practicum.pablic;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.admin.categories.model.CategoryDto;
import ru.practicum.pablic.categories.PublicCategoriesService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class PublicController {
    private final PublicCategoriesService publicCategoriesService;

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
}
