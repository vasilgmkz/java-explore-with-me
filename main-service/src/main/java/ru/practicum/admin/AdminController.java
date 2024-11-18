package ru.practicum.admin;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.admin.categories.dto.NewCategoryDto;
import ru.practicum.admin.categories.model.CategoryDto;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin")
@Validated
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto addCategory(@RequestBody @Valid NewCategoryDto newCategoryDto) {
        return adminService.addCategory(newCategoryDto);
    }

    @DeleteMapping("/categories/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable("catId") Long catId) {
        adminService.deleteCategory(catId);
    }

    @PatchMapping("/categories/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto updateCategory(@RequestBody @Valid NewCategoryDto newCategoryDto, @PathVariable("catId") Long catId) {
        return adminService.updateCategory(newCategoryDto, catId);
    }
}
