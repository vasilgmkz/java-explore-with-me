package ru.practicum.admin.categories.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.practicum.admin.categories.dto.NewCategoryDto;
import ru.practicum.admin.categories.model.CategoryDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapperMapStruct {

    @Mapping(target = "id", ignore = true)
    CategoryDto inCategoryDto(NewCategoryDto newCategoryDto);
}

