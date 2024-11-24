package ru.practicum.pablic;

import ru.practicum.admin.categories.model.CategoryDto;

import java.util.List;

public interface PublicService {
    List<CategoryDto> getCategories(Long from, Long size);

    CategoryDto getCategoryId(Long catId);
}
