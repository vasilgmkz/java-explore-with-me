package ru.practicum.admin.events;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.admin.AdminService;
import ru.practicum.converter.Converter;
import ru.practicum.exeption.EditingConditionsException;
import ru.practicum.exeption.NotFoundException;
import ru.practicum.exeption.Status;
import ru.practicum.privates.events.dto.EventFullDto;
import ru.practicum.privates.events.dto.UpdateEventAdminRequest;
import ru.practicum.privates.events.mapper.EventMapperMapStruct;
import ru.practicum.privates.events.model.EventDto;
import ru.practicum.privates.events.model.State;
import ru.practicum.privates.events.model.StateAction;
import ru.practicum.privates.requests.PrivateRequestsRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service("adminEventsService")
@RequiredArgsConstructor
public class AdminEventsService implements AdminService {
    private final EventMapperMapStruct eventMapperMapStruct;
    private final AdminEventsRepository adminEventsRepository;
    private final PrivateRequestsRepository privateRequestsRepository;
    private final Converter converter;

    @Override
    public EventFullDto updateEvent(UpdateEventAdminRequest updateEventAdminRequest, Long eventId) {
        EventDto eventDto = adminEventsRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("Event with id=%d was not found", eventId)));
        if (updateEventAdminRequest.getStateAction() != null && updateEventAdminRequest.getStateAction().equals(StateAction.PUBLISH_EVENT)) {
            if (eventDto.getState().equals(State.PENDING)) {
                eventDto.setState(State.PUBLISHED);
                eventDto.setPublishedOn(LocalDateTime.now());
                if (!eventDto.getEventDate().minusHours(1).isAfter(eventDto.getPublishedOn())) {
                    throw new EditingConditionsException("The start date of the events to be changed must be no earlier than one hour from the publication date.", Status.FORBIDDEN);
                }
            } else {
                throw new EditingConditionsException("Cannot publish the event because it's not in the right state: PUBLISHED or CANCELED", Status.FORBIDDEN);
            }
        }
        if (updateEventAdminRequest.getStateAction() != null && updateEventAdminRequest.getStateAction().equals(StateAction.REJECT_EVENT)) {
            if (!eventDto.getState().equals(State.PUBLISHED)) {
                eventDto.setState(State.CANCELED);
            } else {
                throw new EditingConditionsException("Cannot reject the event because it's not in the right state: PUBLISHED", Status.FORBIDDEN);
            }
        }
        EventDto eventDtoUpdate = eventMapperMapStruct.inEventDtoFromUpdateEventAdminRequest(updateEventAdminRequest);
        eventDtoUpdate.setId(eventDto.getId());
        comparisonMethod(eventDto, eventDtoUpdate);
        adminEventsRepository.save(eventDto);
        EventFullDto eventFullDto = eventMapperMapStruct.inEventFullDtoFromEventDto(eventDto);
        return converter.addConfirmedRequestsAndViews(List.of(eventFullDto)).getFirst();
    }

    @Override
    public List<EventFullDto> getEvents(List<Integer> users, List<String> states, List<Integer> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size) {
        if (users == null || users.size() == 1 && users.getFirst().equals(0)) {
            users = new ArrayList<>();
        }
        if (categories == null || categories.size() == 1 && categories.getFirst().equals(0)) {
            categories = new ArrayList<>();
        }
        if (states == null) {
            states = new ArrayList<>();
        }
        List<EventDto> eventDtos = adminEventsRepository.getEventsAdmin(users, states, categories, rangeStart, rangeEnd, from, size);
        if (eventDtos.isEmpty()) {
            return new ArrayList<>();
        }
        List<EventFullDto> eventFullDtos = eventDtos.stream().map(eventMapperMapStruct::inEventFullDtoFromEventDto).toList();
        return converter.addConfirmedRequestsAndViews(eventFullDtos);
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

            if (eventDto.getPublishedOn() != null) {
                if (!eventDtoUpdate.getEventDate().minusHours(1).isAfter(eventDto.getPublishedOn())) {
                    throw new EditingConditionsException("The start date of the events to be changed must be no earlier than one hour from the publication date.", Status.FORBIDDEN);
                } else {
                    eventDto.setEventDate(eventDtoUpdate.getEventDate());
                }
            } else {
                eventDto.setEventDate(eventDtoUpdate.getEventDate());
            }
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
