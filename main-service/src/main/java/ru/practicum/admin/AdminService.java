package ru.practicum.admin;

import ru.practicum.admin.categories.dto.NewCategoryDto;
import ru.practicum.admin.categories.model.CategoryDto;
import ru.practicum.admin.users.dto.UserShortDtoFromConsole;
import ru.practicum.admin.users.model.UserDto;
import ru.practicum.privates.events.dto.EventFullDto;
import ru.practicum.privates.events.dto.UpdateEventAdminRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminService {
    default CategoryDto addCategory(NewCategoryDto newCategoryDto) {
        return null;
    }

    //TODO Обратите внимание: с категорией не должно быть связано ни одного события!
    default void deleteCategory(Long catId) {
    }

    default CategoryDto updateCategory(NewCategoryDto newCategoryDto, Long catId) {
        return null;
    }

    default UserDto addUser(UserShortDtoFromConsole userShortDtoFromConsole) {
        return null;
    }

    default List<UserDto> getUsers(List<Integer> ids, Long from, Long size) {
        return null;
    }

    default void deleteUser(Long userId) {

    }

    default EventFullDto updateEvent(UpdateEventAdminRequest updateEventAdminRequest, Long eventId) {
        return null;
    }

    default List<EventFullDto> getEvents(List<Integer> users, List<String> states, List<Integer> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size) {
        return null;
    }
}
