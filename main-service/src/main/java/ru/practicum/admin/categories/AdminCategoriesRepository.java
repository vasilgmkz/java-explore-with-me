package ru.practicum.admin.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.admin.categories.model.CategoryDto;

public interface AdminCategoriesRepository extends JpaRepository<CategoryDto, Long> {
}
