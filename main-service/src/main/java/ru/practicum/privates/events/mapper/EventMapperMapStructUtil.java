package ru.practicum.privates.events.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import ru.practicum.admin.categories.AdminCategoriesRepository;
import ru.practicum.admin.categories.model.CategoryDto;
import ru.practicum.admin.users.AdminUsersRepository;
import ru.practicum.admin.users.dto.UserShortDto;
import ru.practicum.admin.users.mapper.UserMapperMapStruct;
import ru.practicum.admin.users.model.UserDto;
import ru.practicum.exeption.NotFoundException;
import ru.practicum.privates.events.dto.NewEventDto;
import ru.practicum.privates.events.dto.UpdateEventAdminRequest;
import ru.practicum.privates.events.dto.UpdateEventUserRequest;
import ru.practicum.privates.events.model.EventDto;

@Component
@Named("EventMapperMapStructUtil")
@RequiredArgsConstructor
public class EventMapperMapStructUtil {
    private final AdminCategoriesRepository adminCategoriesRepository;
    private final AdminUsersRepository adminUsersRepository;
    private final UserMapperMapStruct userMapperMapStruct;

    @Named("getCategoryNewEventDto")
    CategoryDto getCategoryNewEventDto(NewEventDto newEventDto) {
        return adminCategoriesRepository.findById(newEventDto.getCategory().longValue())
                .orElseThrow(() -> new NotFoundException(String.format("Category with id=%d was not found", newEventDto.getCategory())));
    }

    @Named("getCategoryUpdateEventUserRequest")
    CategoryDto getCategoryUpdateEventUserRequest(UpdateEventUserRequest updateEventUserRequest) {
        if (updateEventUserRequest.getCategory() == null) {
            return null;
        }
        return adminCategoriesRepository.findById(updateEventUserRequest.getCategory().longValue())
                .orElseThrow(() -> new NotFoundException(String.format("Category with id=%d was not found", updateEventUserRequest.getCategory())));
    }

    @Named("getCategoryUpdateEventAdminRequest")
    CategoryDto getCategoryUpdateEventAdminRequest(UpdateEventAdminRequest updateEventAdminRequest) {
        if (updateEventAdminRequest.getCategory() == null) {
            return null;
        }
        return adminCategoriesRepository.findById(updateEventAdminRequest.getCategory().longValue())
                .orElseThrow(() -> new NotFoundException(String.format("Category with id=%d was not found", updateEventAdminRequest.getCategory())));
    }

    @Named("getInitiator")
    UserDto getInitiator(Long userId) {
        return adminUsersRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id=%d was not found", userId)));
    }

    @Named("getShortUser")
    UserShortDto getShortUser(EventDto eventDto) {
        return userMapperMapStruct.inUserShortDto(eventDto.getInitiator());
    }
}
