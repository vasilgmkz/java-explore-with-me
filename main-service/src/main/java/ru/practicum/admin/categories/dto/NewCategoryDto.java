package ru.practicum.admin.categories.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NewCategoryDto {
    @NotEmpty
    @NotBlank
    @Size(max = 50)
    String name;
}
