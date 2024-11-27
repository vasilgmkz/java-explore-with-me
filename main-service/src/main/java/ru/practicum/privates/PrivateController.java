package ru.practicum.privates;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.privates.events.dto.EventFullDto;
import ru.practicum.privates.events.dto.EventShortDto;
import ru.practicum.privates.events.dto.NewEventDto;
import ru.practicum.privates.events.dto.UpdateEventUserRequest;
import ru.practicum.privates.requests.dto.EventRequestStatusUpdateRequest;
import ru.practicum.privates.requests.dto.EventRequestStatusUpdateResult;
import ru.practicum.privates.requests.dto.ParticipationRequestDto;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class PrivateController {

    @Qualifier("privateEventsService")
    private final PrivateService privateEventsService;

    @Qualifier("privateRequestsService")
    private final PrivateService privateRequestsService;

    @PostMapping("/users/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto addEvent(@RequestBody @Valid NewEventDto newEventDto, @PathVariable("userId") @Valid @Positive Long userId) {
        log.info("Приватный запрос на создание события");
        return privateEventsService.addEvent(newEventDto, userId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto updateEventUser(@RequestBody @Valid UpdateEventUserRequest updateEventUserRequest,
                                        @PathVariable("userId") @Valid @Positive Long userId,
                                        @PathVariable("eventId") @Valid @Positive Long eventId) {
        log.info("Приватный запрос на изменение события");
        return privateEventsService.updateEventUser(updateEventUserRequest, userId, eventId);
    }

    @PostMapping("/users/{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto addRequest(@PathVariable(name = "userId") @Valid @Positive Long userId,
                                              @RequestParam(name = "eventId") @Valid @Positive Long eventId) {
        log.info("Приватный запрос на создание запроса на участие в событии");
        return privateRequestsService.addRequest(userId, eventId);
    }

    @PatchMapping("/users/{userId}/requests/{requestId}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public ParticipationRequestDto cancelRequest(@PathVariable(name = "userId") @Valid @Positive Long userId,
                                                 @PathVariable(name = "requestId") @Valid @Positive Long requestId) {
        log.info("Приватный запрос на удаление запроса на участие в событии");
        return privateRequestsService.cancelRequest(userId, requestId);
    }

    @GetMapping("/users/{userId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipationRequestDto> getRequests(@PathVariable(name = "userId") @Valid @Positive Long userId) {
        log.info("Приватный запрос на получение запросов на участие в событии");
        return privateRequestsService.getRequests(userId);
    }

    @GetMapping("/users/{userId}/events")
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> getEventsByUserId(@PathVariable(name = "userId") @Valid @Positive Long userId,
                                                 @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Long from,
                                                 @RequestParam(name = "size", defaultValue = "10") @PositiveOrZero Long size) {
        log.info("Приватный запрос на получение запроса на участие в событии");
        return privateEventsService.getEventsByUserId(userId, from, size);
    }

    @GetMapping("/users/{userId}/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto getEventByUserIdAndEventId(@PathVariable(name = "userId") @Valid @Positive Long userId,
                                                   @PathVariable(name = "eventId") @Valid @Positive Long eventId) {
        log.info("Приватный запрос на получение полной информации о событии");
        return privateEventsService.getEventByUserIdAndEventId(userId, eventId);
    }

    @GetMapping("/users/{userId}/events/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipationRequestDto> getRequestsByUserIdAndEventId(@PathVariable(name = "userId") @Valid @Positive Long userId,
                                                                       @PathVariable(name = "eventId") @Valid @Positive Long eventId) {
        log.info("Приватный запрос на получение списка запросов пользователя по id события");
        return privateRequestsService.getRequestsByUserIdAndEventId(userId, eventId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public EventRequestStatusUpdateResult updateRequests(@RequestBody @Valid EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest,
                                                         @PathVariable(name = "userId") @Valid @Positive Long userId,
                                                         @PathVariable(name = "eventId") @Valid @Positive Long eventId) {
        log.info("Приватный запрос на изменение запроса");
        return privateRequestsService.updateRequests(eventRequestStatusUpdateRequest, userId, eventId);
    }

}
