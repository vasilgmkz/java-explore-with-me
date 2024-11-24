package ru.practicum.pablic;

import ru.practicum.admin.categories.model.CategoryDto;
import ru.practicum.admin.compilations.dto.CompilationDto;

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
}
