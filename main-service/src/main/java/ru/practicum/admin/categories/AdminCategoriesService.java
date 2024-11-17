package ru.practicum.admin.categories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.admin.AdminService;
import ru.practicum.admin.categories.dto.NewCategoryDto;
import ru.practicum.admin.categories.mapper.CategoryMapperMapStruct;
import ru.practicum.admin.categories.model.CategoryDto;

@Service
@RequiredArgsConstructor
public class AdminCategoriesService implements AdminService {
    private final AdminCategoriesRepository adminCategoriesRepository;
    private final CategoryMapperMapStruct categoryMapperMapStruct;

    @Override
    public CategoryDto addCategory(NewCategoryDto newCategoryDto) {
        CategoryDto categoryDto = categoryMapperMapStruct.inCategoryDto(newCategoryDto);
        Integer id = adminCategoriesRepository.save(categoryDto).getId();
        categoryDto.setId(id);
        return categoryDto;
    }
}
