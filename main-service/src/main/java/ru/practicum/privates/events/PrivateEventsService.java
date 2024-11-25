package ru.practicum.privates.events;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.converter.Converter;
import ru.practicum.exeption.EditingConditionsException;
import ru.practicum.exeption.NotFoundException;
import ru.practicum.exeption.Status;
import ru.practicum.privates.PrivateService;
import ru.practicum.privates.events.dto.EventFullDto;
import ru.practicum.privates.events.dto.EventShortDto;
import ru.practicum.privates.events.dto.NewEventDto;
import ru.practicum.privates.events.dto.UpdateEventUserRequest;
import ru.practicum.privates.events.mapper.EventMapperMapStruct;
import ru.practicum.privates.events.model.EventDto;
import ru.practicum.privates.events.model.State;
import ru.practicum.privates.events.model.StateAction;

import java.util.ArrayList;
import java.util.List;

@Service("privateEventsService")
@RequiredArgsConstructor
public class PrivateEventsService implements PrivateService {
    private final EventMapperMapStruct eventMapperMapStruct;
    private final PrivateEventsRepository privateEventsRepository;
    private final Converter converter;

    @Override
    public EventFullDto addEvent(NewEventDto newEventDto, Long userId) {
        EventDto eventDto = eventMapperMapStruct.inEventDtoFromNewEventDto(newEventDto, userId);
        Integer id = privateEventsRepository.save(eventDto).getId();
        eventDto.setId(id);
        return eventMapperMapStruct.inEventFullDtoFromEventDto(eventDto);
    }

    @Override
    public EventFullDto updateEventUser(UpdateEventUserRequest updateEventUserRequest, Long userId, Long eventId) {
        EventDto eventDto = privateEventsRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("Event with id=%d was not found", eventId)));
        if (eventDto.getState() != null && eventDto.getState().equals(State.PUBLISHED)) {
            throw new EditingConditionsException("Only pending or canceled events can be changed", Status.FORBIDDEN);
        }
        if (!eventDto.getInitiator().getId().equals(userId.intValue())) {
            throw new EditingConditionsException("Only the event owner can change it", Status.FORBIDDEN);
        }
        EventDto eventDtoUpdate = eventMapperMapStruct.inEventDtoFromUpdateEventUserRequest(updateEventUserRequest);
        eventDtoUpdate.setId(eventDto.getId());
        if (updateEventUserRequest.getStateAction() != null && updateEventUserRequest.getStateAction().equals(StateAction.SEND_TO_REVIEW)) {
            eventDto.setState(State.PENDING);
        }
        if (updateEventUserRequest.getStateAction() != null && updateEventUserRequest.getStateAction().equals(StateAction.CANCEL_REVIEW)) {
            eventDto.setState(State.CANCELED);
        }
        comparisonMethod(eventDto, eventDtoUpdate);
        privateEventsRepository.save(eventDto);
        return eventMapperMapStruct.inEventFullDtoFromEventDto(eventDto);
    }

    @Override
    public List<EventShortDto> getEventsByUserId(Long userId, Long from, Long size) {
        List<EventDto> eventDtos = privateEventsRepository.getEventsByUserId(userId, from, size);
        if (eventDtos.isEmpty()) {
            return new ArrayList<>();
        }
        List<EventShortDto> eventShortDtos = eventDtos.stream().map(eventMapperMapStruct::inEventShortDtoFromEventDto).toList();
        return converter.addConfirmedRequestsAndViewsInEventShortDto(eventShortDtos);
    }

    @Override
    public EventFullDto getEventByUserIdAndEventId(Long userId, Long eventId) {
        EventDto eventDto = privateEventsRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("Event with id=%d was not found", eventId)));
        if (!eventDto.getInitiator().getId().equals(userId.intValue())) {
            throw new EditingConditionsException("Only the event owner can view it", Status.FORBIDDEN);
        }
        EventFullDto eventFullDto = eventMapperMapStruct.inEventFullDtoFromEventDto(eventDto);
        return converter.addConfirmedRequestsAndViewsInEventFullDto(List.of(eventFullDto)).getFirst();
    }

    private void comparisonMethod(EventDto eventDto, EventDto eventDtoUpdate) {
        if (eventDtoUpdate.getAnnotation() != null) {
            eventDto.setAnnotation(eventDtoUpdate.getAnnotation());
        }
        if (eventDtoUpdate.getCategory() != null) {
            eventDto.setCategory(eventDtoUpdate.getCategory());
        }
        if (eventDtoUpdate.getDescription() != null) {
            eventDto.setDescription(eventDtoUpdate.getDescription());
        }
        if (eventDtoUpdate.getEventDate() != null) {
            eventDto.setEventDate(eventDtoUpdate.getEventDate());
        }
        if (eventDtoUpdate.getLocation() != null) {
            if (eventDtoUpdate.getLocation().getLat() != null) {
                eventDto.getLocation().setLat(eventDtoUpdate.getLocation().getLat());
            }
            if (eventDtoUpdate.getLocation().getLon() != null) {
                eventDto.getLocation().setLon(eventDtoUpdate.getLocation().getLon());
            }
        }
        if (eventDtoUpdate.getPaid() != null) {
            eventDto.setPaid(eventDtoUpdate.getPaid());
        }
        if (eventDtoUpdate.getParticipantLimit() != null) {
            eventDto.setParticipantLimit(eventDtoUpdate.getParticipantLimit());
        }
        if (eventDtoUpdate.getRequestModeration() != null) {
            eventDto.setRequestModeration(eventDtoUpdate.getRequestModeration());
        }
        if (eventDtoUpdate.getTitle() != null) {
            eventDto.setTitle(eventDtoUpdate.getTitle());
        }
    }
}
