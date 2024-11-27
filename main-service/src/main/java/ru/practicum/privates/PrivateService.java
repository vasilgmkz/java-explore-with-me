package ru.practicum.privates;

import ru.practicum.privates.events.dto.EventFullDto;
import ru.practicum.privates.events.dto.EventShortDto;
import ru.practicum.privates.events.dto.NewEventDto;
import ru.practicum.privates.events.dto.UpdateEventUserRequest;
import ru.practicum.privates.requests.dto.EventRequestStatusUpdateRequest;
import ru.practicum.privates.requests.dto.EventRequestStatusUpdateResult;
import ru.practicum.privates.requests.dto.ParticipationRequestDto;

import java.util.List;

public interface PrivateService {
    default EventFullDto addEvent(NewEventDto newEventDto, Long userId) {
        return null;
    }

    default EventFullDto updateEventUser(UpdateEventUserRequest updateEventUserRequest, Long userId, Long eventId) {
        return null;
    }

    default ParticipationRequestDto addRequest(Long userId, Long eventId) {
        return null;
    }

    default ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        return null;
    }

    default List<ParticipationRequestDto> getRequests(Long userId) {
        return null;
    }

    default List<EventShortDto> getEventsByUserId(Long userId, Long from, Long size) {
        return null;
    }

    default EventFullDto getEventByUserIdAndEventId(Long userId, Long eventId) {
        return null;
    }

    default List<ParticipationRequestDto> getRequestsByUserIdAndEventId(Long userId, Long eventId) {
        return null;
    }

    default EventRequestStatusUpdateResult updateRequests(EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest, Long userId, Long eventId) {
        return null;
    }
}
