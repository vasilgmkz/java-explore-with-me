package ru.practicum.pablic.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.admin.categories.model.CategoryDto;

import java.util.List;

public interface PublicCategoriesRepository extends JpaRepository<CategoryDto, Long> {

    @Query(value = "select ct.category_id,  ct.category_name from categories as ct order by category_id OFFSET :from LIMIT :size", nativeQuery = true)
    List<CategoryDto> getCategories(@Param("from") Long from, @Param("size") Long size);
}
