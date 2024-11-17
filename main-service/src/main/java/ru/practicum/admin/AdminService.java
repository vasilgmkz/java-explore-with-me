package ru.practicum.admin;

import ru.practicum.admin.categories.dto.NewCategoryDto;
import ru.practicum.admin.categories.model.CategoryDto;

public interface AdminService {
    CategoryDto addCategory(NewCategoryDto newCategoryDto);
}
