package ru.practicum.pablic.events;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.converter.Converter;
import ru.practicum.exeption.EditingConditionsException;
import ru.practicum.exeption.NotFoundException;
import ru.practicum.exeption.Status;
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
public class PublicEventsService implements PublicService {
    private final PublicEventsRepository publicEventsRepository;
    private final EventMapperMapStruct eventMapperMapStruct;
    private final Converter converter;

    @Override
    public List<EventShortDto> getEvents(String text, List<Integer> categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, Long from, Long size) {
        if (categories == null || categories.size() == 1 && categories.getFirst().equals(0)) {
            categories = new ArrayList<>();
        }
        if (rangeStart == null || rangeEnd == null) {
            rangeStart = LocalDateTime.now();
            rangeEnd = LocalDateTime.now().plusYears(100);
        }
        List<EventDto> eventDtoList = publicEventsRepository.getEvents(text, categories, paid, rangeStart, rangeEnd, from, size);
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
    public EventFullDto getEventsById(Long id) {
        EventDto eventDto = publicEventsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Event with id=%d was not found", id)));
        if (!eventDto.getState().equals(State.PUBLISHED)) {
            throw new EditingConditionsException("The event must have the state: PUBLISHED", Status.FORBIDDEN);
        }
        EventFullDto eventFullDto = eventMapperMapStruct.inEventFullDtoFromEventDto(eventDto);
        return converter.addConfirmedRequestsAndViewsInEventFullDto(List.of(eventFullDto)).getFirst();
    }
}
