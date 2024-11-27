package ru.practicum.admin;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.admin.categories.dto.NewCategoryDto;
import ru.practicum.admin.categories.model.CategoryDto;
import ru.practicum.admin.compilations.dto.CompilationDto;
import ru.practicum.admin.compilations.dto.CompilationDtoFromConsole;
import ru.practicum.admin.compilations.validate.Marker;
import ru.practicum.admin.users.dto.UserShortDtoFromConsole;
import ru.practicum.admin.users.model.UserDto;
import ru.practicum.privates.events.dto.EventFullDto;
import ru.practicum.privates.events.dto.UpdateEventAdminRequest;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin")
@Validated
@Slf4j
public class AdminController {

    @Qualifier("adminCategoriesService")
    private final AdminService adminCategoriesService;

    @Qualifier("adminUsersService")
    private final AdminService adminUsersService;

    @Qualifier("adminEventsService")
    private final AdminService adminEventsService;

    @Qualifier("adminCompilationsService")
    private final AdminService adminCompilationsService;

    @PostMapping("/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto addCategory(@RequestBody @Valid NewCategoryDto newCategoryDto) {
        log.info("Запрос на создание категории");
        return adminCategoriesService.addCategory(newCategoryDto);
    }

    @DeleteMapping("/categories/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable("catId") Long catId) {
        log.info("Запрос на удаление категории");
        adminCategoriesService.deleteCategory(catId);
    }

    @PatchMapping("/categories/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto updateCategory(@RequestBody @Valid NewCategoryDto newCategoryDto, @PathVariable("catId") Long catId) {
        log.info("Запрос на изменение категории администратором");
        return adminCategoriesService.updateCategory(newCategoryDto, catId);
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)

    public UserDto addUser(@RequestBody @Valid UserShortDtoFromConsole userShortDtoFromConsole) {
        log.info("Запрос на создание пользователя администратором");
        return adminUsersService.addUser(userShortDtoFromConsole);
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getUsers(@RequestParam(required = false, name = "ids") List<Integer> ids,
                                  @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Long from,
                                  @RequestParam(name = "size", defaultValue = "10") @PositiveOrZero Long size) {
        log.info("Запрос на получение пользователей администратором");
        return adminUsersService.getUsers(ids, from, size);
    }

    @DeleteMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("userId") Long userId) {
        log.info("Запрос на удаление пользователя администратором");
        adminUsersService.deleteUser(userId);
    }

    @PatchMapping("/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto updateEventUser(@RequestBody @Valid UpdateEventAdminRequest updateEventAdminRequest,
                                        @PathVariable("eventId") @Valid @Positive Long eventId) {
        log.info("Запрос на изменение пользователя администратором");
        return adminEventsService.updateEvent(updateEventAdminRequest, eventId);
    }

    @GetMapping("/events")
    @ResponseStatus(HttpStatus.OK)
    public List<EventFullDto> getEvents(@RequestParam(required = false, name = "users") List<Integer> users,
                                        @RequestParam(required = false, name = "states") List<String> states,
                                        @RequestParam(required = false, name = "categories") List<Integer> categories,
                                        @RequestParam(required = false, name = "rangeStart") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                        @RequestParam(required = false, name = "rangeEnd") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                        @RequestParam(name = "from", defaultValue = "0") Integer from,
                                        @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Запрос на получение списка событий администратором");
        return adminEventsService.getEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PostMapping("/compilations")
    @ResponseStatus(HttpStatus.CREATED)
    @Validated(value = {Marker.AddCompilation.class})
    public CompilationDto addCompilation(@RequestBody @Valid CompilationDtoFromConsole compilationDtoFromConsole) {
        log.info("Запрос на добавление подборки администратором");
        return adminCompilationsService.addCompilation(compilationDtoFromConsole);
    }

    @DeleteMapping("/compilations/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable("compId") @Valid @Positive Long compId) {
        log.info("Запрос на удаление подборки администратором");
        adminCompilationsService.deleteCompilation(compId);
    }

    @PatchMapping("/compilations/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto updateCompilation(@PathVariable("compId") @Valid @Positive Long compId, @RequestBody @Valid CompilationDtoFromConsole compilationDtoFromConsole) {
        log.info("Запрос на изменение подборки администратором");
        return adminCompilationsService.updateCompilation(compilationDtoFromConsole, compId);
    }

}

