package ru.practicum.pablic.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.StatFromConsoleDto;
import ru.practicum.StatsClient;
import ru.practicum.converter.Converter;
import ru.practicum.exeption.BadRequest;
import ru.practicum.exeption.NotFoundException;
import ru.practicum.pablic.PublicService;
import ru.practicum.privates.events.dto.EventFullDto;
import ru.practicum.privates.events.dto.EventShortDto;
import ru.practicum.privates.events.mapper.EventMapperMapStruct;
import ru.practicum.privates.events.model.EventDto;
import ru.practicum.privates.events.model.State;

import java.time.LocalDateTime;
import java.util.*;

@Service("publicEventsService")
@RequiredArgsConstructor
@Slf4j
public class PublicEventsService implements PublicService {
    private final PublicEventsRepository publicEventsRepository;
    private final EventMapperMapStruct eventMapperMapStruct;
    private final Converter converter;
    private final StatsClient statsClient;

    @Override
    public List<EventShortDto> getEvents(String text, List<Integer> categories,
                                         Boolean paid, LocalDateTime rangeStart,
                                         LocalDateTime rangeEnd, Boolean onlyAvailable,
                                         String sort, Long from,
                                         Long size, String ip, String uri) {
        if (categories == null || categories.size() == 1 && categories.getFirst().equals(0)) {
            categories = new ArrayList<>();
        }
        if (rangeStart == null || rangeEnd == null) {
            rangeStart = LocalDateTime.now();
            rangeEnd = LocalDateTime.now().plusYears(100);
        }
        if (!rangeEnd.isAfter(rangeStart)) {
            throw new BadRequest("Start date is later than end date");
        }
        List<EventDto> eventDtoList = publicEventsRepository.getEvents(text, categories, paid, rangeStart, rangeEnd, from, size);
        statFromConsoleDto(ip, uri);
        if (eventDtoList.isEmpty()) {
            return new ArrayList<>();
        }
        List<EventShortDto> eventShortDtoList = new ArrayList<>(eventDtoList.stream().map(eventMapperMapStruct::inEventShortDtoFromEventDto).toList());
        converter.addConfirmedRequestsAndViewsInEventShortDto(eventShortDtoList);
        if (onlyAvailable) {
            Map<Integer, Integer> participantLimit = new HashMap<>();
            for (EventDto eventDto : eventDtoList) {
                participantLimit.put(eventDto.getId(), eventDto.getParticipantLimit());
            }
            Iterator<EventShortDto> iterator = eventShortDtoList.iterator();
            while (iterator.hasNext()) {
                EventShortDto nextEventShortDto = iterator.next();
                boolean check = nextEventShortDto.getConfirmedRequests() != 0 && nextEventShortDto.getConfirmedRequests().equals(participantLimit.get(nextEventShortDto.getId()));
                if (check) {
                    iterator.remove();
                }
            }
        }
        if (eventShortDtoList.isEmpty()) {
            return new ArrayList<>();
        }
        if (sort != null && sort.equals("EVENT_DATE")) {
            eventShortDtoList.sort(Comparator.comparing(EventShortDto::getEventDate));
            return eventShortDtoList;
        } else if (sort != null && sort.equals("VIEWS")) {
            eventShortDtoList.sort(Comparator.comparing(EventShortDto::getViews).reversed());
            return eventShortDtoList;
        } else {
            return eventShortDtoList;
        }
    }

    @Override
    public EventFullDto getEventsById(Long id, String ip, String uri) {
        EventDto eventDto = publicEventsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Event with id=%d was not found", id)));
        if (!eventDto.getState().equals(State.PUBLISHED)) {
            throw new NotFoundException("The event must have the state: PUBLISHED");
        }
        EventFullDto eventFullDto = eventMapperMapStruct.inEventFullDtoFromEventDto(eventDto);
        statFromConsoleDto(ip, uri);
        return converter.addConfirmedRequestsAndViewsInEventFullDto(List.of(eventFullDto)).getFirst();
    }

    private void statFromConsoleDto(String ip, String uri) {
        StatFromConsoleDto statFromConsoleDto = new StatFromConsoleDto();
        statFromConsoleDto.setApp("ewm-main-service");
        statFromConsoleDto.setIp(ip);
        statFromConsoleDto.setUri(uri);
        statFromConsoleDto.setTimestamp(LocalDateTime.now());
        log.info("Запрос на сохранение: {} + {}", uri, ip);
        statsClient.addHit(statFromConsoleDto);
    }
}
