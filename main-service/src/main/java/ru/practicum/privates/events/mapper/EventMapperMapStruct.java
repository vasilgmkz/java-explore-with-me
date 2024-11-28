package ru.practicum.privates.events.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.practicum.privates.events.dto.*;
import ru.practicum.privates.events.model.EventDto;
import ru.practicum.privates.events.model.State;

import java.time.LocalDateTime;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {EventMapperMapStructUtil.class}, imports = {
        LocalDateTime.class, State.class})
public interface EventMapperMapStruct {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdOn", expression = "java(LocalDateTime.now())")
    @Mapping(target = "publishedOn", ignore = true)
    @Mapping(target = "state", expression = "java(State.PENDING)")
    @Mapping(target = "initiator", qualifiedByName = {"EventMapperMapStructUtil", "getInitiator"}, source = "userId")
    @Mapping(target = "category", qualifiedByName = {"EventMapperMapStructUtil", "getCategoryNewEventDto"}, source = "newEventDto")
    EventDto inEventDtoFromNewEventDto(NewEventDto newEventDto, Long userId);

    @Mapping(target = "confirmedRequests", expression = "java(0)")
    @Mapping(target = "views", expression = "java(0)")
    @Mapping(target = "initiator", qualifiedByName = {"EventMapperMapStructUtil", "getShortUser"}, source = "eventDto")
    EventFullDto inEventFullDtoFromEventDto(EventDto eventDto);

    @Mapping(target = "category", qualifiedByName = {"EventMapperMapStructUtil", "getCategoryUpdateEventUserRequest"}, source = "updateEventUserRequest")
    EventDto inEventDtoFromUpdateEventUserRequest(UpdateEventUserRequest updateEventUserRequest);

    @Mapping(target = "category", qualifiedByName = {"EventMapperMapStructUtil", "getCategoryUpdateEventAdminRequest"}, source = "updateEventAdminRequest")
    EventDto inEventDtoFromUpdateEventAdminRequest(UpdateEventAdminRequest updateEventAdminRequest);

    @Mapping(target = "confirmedRequests", expression = "java(0)")
    @Mapping(target = "views", expression = "java(0)")
    @Mapping(target = "initiator", qualifiedByName = {"EventMapperMapStructUtil", "getShortUser"}, source = "eventDto")
    EventShortDto inEventShortDtoFromEventDto(EventDto eventDto);
}
