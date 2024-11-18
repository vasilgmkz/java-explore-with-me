package ru.practicum.admin;

import ru.practicum.admin.categories.dto.NewCategoryDto;
import ru.practicum.admin.categories.model.CategoryDto;

public interface AdminService {
    CategoryDto addCategory(NewCategoryDto newCategoryDto);

    //TODO Обратите внимание: с категорией не должно быть связано ни одного события!
    void deleteCategory(Long catId);

    CategoryDto updateCategory(NewCategoryDto newCategoryDto, Long catId);
}
