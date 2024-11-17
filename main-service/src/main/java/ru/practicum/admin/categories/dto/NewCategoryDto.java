package ru.practicum.admin.categories.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class NewCategoryDto {
    @NotEmpty
    String name;
}
