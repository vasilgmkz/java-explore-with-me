package ru.practicum.pablic.categories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.admin.categories.model.CategoryDto;
import ru.practicum.exeption.NotFoundException;
import ru.practicum.pablic.PublicService;

import java.util.List;

@Service ("publicCategoriesService")
@RequiredArgsConstructor
public class PublicCategoriesService implements PublicService {
    private final PublicCategoriesRepository publicCategoriesRepository;

    @Override
    public List<CategoryDto> getCategories(Long from, Long size) {
        return publicCategoriesRepository.getCategories(from, size);
    }

    @Override
    public CategoryDto getCategoryId(Long catId) {
        return publicCategoriesRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException(String.format("Category with id=%d was not found", catId)));
    }
}
