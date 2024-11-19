package ru.practicum.admin.categories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.admin.AdminService;
import ru.practicum.admin.categories.dto.NewCategoryDto;
import ru.practicum.admin.categories.mapper.CategoryMapperMapStruct;
import ru.practicum.admin.categories.model.CategoryDto;
import ru.practicum.exeption.NotFoundException;

@Service("adminCategoriesService")
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

    //TODO *РЕАЛИЗОВАТЬ* Обратите внимание: с категорией не должно быть связано ни одного события! 409 Существуют события, связанные с категорией
    @Override
    public void deleteCategory(Long catId) {
        adminCategoriesRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException(String.format("Category with id=%d was not found", catId)));
        adminCategoriesRepository.deleteById(catId);
    }

    @Override
    public CategoryDto updateCategory(NewCategoryDto newCategoryDto, Long catId) {
        CategoryDto categoryDto = adminCategoriesRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException(String.format("Category with id=%d was not found", catId)));
        categoryDto.setName(newCategoryDto.getName());
        adminCategoriesRepository.save(categoryDto);
        return categoryDto;
    }
}
