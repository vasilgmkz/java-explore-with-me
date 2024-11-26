package ru.practicum.converter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.StatInConsoleDto;
import ru.practicum.StatsClient;
import ru.practicum.admin.compilations.dto.CompilationDto;
import ru.practicum.privates.events.dto.EventFullDto;
import ru.practicum.privates.events.dto.EventShortDto;
import ru.practicum.privates.requests.PrivateRequestsRepository;
import ru.practicum.privates.requests.model.CountConfirmedRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class Converter {
    private final PrivateRequestsRepository privateRequestsRepository;
    private final StatsClient statsClient;

    public List<EventFullDto> addConfirmedRequestsAndViewsInEventFullDto(List<EventFullDto> eventFullDtos) {

        // Добавление подтвержденных запросов
        List<CountConfirmedRequest> countConfirmedRequestList = privateRequestsRepository.getCountConfirmedRequest();
        Map<Integer, Integer> countConfirmedRequestMap = new HashMap<>();
        for (CountConfirmedRequest a : countConfirmedRequestList) {
            countConfirmedRequestMap.put(a.getEventId(), a.getCount());
        }
        for (EventFullDto eventFullDto : eventFullDtos) {
            Integer confirmedRequests = countConfirmedRequestMap.getOrDefault(eventFullDto.getId(), 0);
            eventFullDto.setConfirmedRequests(confirmedRequests);
        }

        // Добавление количества просмотров
        List<String> eventFullDtosId = eventFullDtos.stream().map(EventFullDto::getId).map(Object::toString).map(x -> "/events/" + x).toList();
        List<StatInConsoleDto> statInConsoleDtosList = statsClient.getStats(LocalDateTime.now().minusYears(100), LocalDateTime.now().plusYears(100), eventFullDtosId, true);
        Map<Integer, Integer> statInConsoleDtosMap = new HashMap<>();
        for (StatInConsoleDto statInConsoleDto : statInConsoleDtosList) {
            String numberString = statInConsoleDto.getUri().replace("/events/", "");
            Integer numberInteger = Integer.parseInt(numberString);
            statInConsoleDtosMap.put(numberInteger, statInConsoleDto.getHits());
        }
        log.info("Map uri: {}", statInConsoleDtosMap);
        for (EventFullDto eventFullDto : eventFullDtos) {
            eventFullDto.setViews(statInConsoleDtosMap.getOrDefault(eventFullDto.getId(), 0));
        }
        return eventFullDtos;
    }

    public List<EventShortDto> addConfirmedRequestsAndViewsInEventShortDto(List<EventShortDto> eventShortDtos) {
        // Добавление подтвержденных запросов
        List<CountConfirmedRequest> countConfirmedRequestList = privateRequestsRepository.getCountConfirmedRequest();
        Map<Integer, Integer> countConfirmedRequestMap = new HashMap<>();
        for (CountConfirmedRequest a : countConfirmedRequestList) {
            countConfirmedRequestMap.put(a.getEventId(), a.getCount());
        }
        for (EventShortDto eventShortDto : eventShortDtos) {
            Integer confirmedRequests = countConfirmedRequestMap.getOrDefault(eventShortDto.getId(), 0);
            eventShortDto.setConfirmedRequests(confirmedRequests);
        }

        // Добавление количества просмотров
        List<String> eventShortDtosId = eventShortDtos.stream().map(EventShortDto::getId).map(Object::toString).map(x -> "/events/" + x).toList();
        List<StatInConsoleDto> statInConsoleDtosList = statsClient.getStats(LocalDateTime.now().minusYears(100), LocalDateTime.now().plusYears(100), eventShortDtosId, true);
        Map<Integer, Integer> statInConsoleDtosMap = new HashMap<>();
        for (StatInConsoleDto statInConsoleDto : statInConsoleDtosList) {
            String numberString = statInConsoleDto.getUri().replace("/events/", "");
            Integer numberInteger = Integer.parseInt(numberString);
            statInConsoleDtosMap.put(numberInteger, statInConsoleDto.getHits());
        }
        for (EventShortDto eventShortDto : eventShortDtos) {
            eventShortDto.setViews(statInConsoleDtosMap.getOrDefault(eventShortDto.getId(), 0));
        }
        return eventShortDtos;
    }

    public List<CompilationDto> addConfirmedRequestsAndViewsInCompilationDtoList(List<CompilationDto> compilationDtoList) {
        // Добавление подтвержденных запросов
        List<CountConfirmedRequest> countConfirmedRequestList = privateRequestsRepository.getCountConfirmedRequest();
        Map<Integer, Integer> countConfirmedRequestMap = new HashMap<>();
        for (CountConfirmedRequest a : countConfirmedRequestList) {
            countConfirmedRequestMap.put(a.getEventId(), a.getCount());
        }
        for (CompilationDto compilationDto : compilationDtoList) {
            for (EventShortDto eventShortDto : compilationDto.getEvents()) {
                Integer confirmedRequests = countConfirmedRequestMap.getOrDefault(eventShortDto.getId(), 0);
                eventShortDto.setConfirmedRequests(confirmedRequests);
            }
        }
        List<String> eventShortDtosId = new ArrayList<>();
        for (CompilationDto compilationDto : compilationDtoList) {
            eventShortDtosId.addAll(compilationDto.getEvents().stream().map(EventShortDto::getId).map(Object::toString).map(x -> "/events/" + x).toList());
        }
        List<StatInConsoleDto> statInConsoleDtosList = statsClient.getStats(LocalDateTime.now().minusYears(100), LocalDateTime.now().plusYears(100), eventShortDtosId, false);
        Map<Integer, Integer> statInConsoleDtosMap = new HashMap<>();
        for (StatInConsoleDto statInConsoleDto : statInConsoleDtosList) {
            String numberString = statInConsoleDto.getUri().replace("/events/", "");
            Integer numberInteger = Integer.parseInt(numberString);
            statInConsoleDtosMap.put(numberInteger, statInConsoleDto.getHits());
        }
        for (CompilationDto compilationDto : compilationDtoList) {
            for (EventShortDto eventShortDto : compilationDto.getEvents()) {
                eventShortDto.setViews(statInConsoleDtosMap.getOrDefault(eventShortDto.getId(), 0));
            }
        }
        return compilationDtoList;
    }
}
