package ru.practicum.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.StatInConsoleDto;
import ru.practicum.StatsClient;
import ru.practicum.privates.events.dto.EventFullDto;
import ru.practicum.privates.requests.PrivateRequestsRepository;
import ru.practicum.privates.requests.model.CountConfirmedRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class Converter {
    private final PrivateRequestsRepository privateRequestsRepository;
    private final StatsClient statsClient;

    public List<EventFullDto> addConfirmedRequestsAndViews(List<EventFullDto> eventFullDtos) {

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
        List<StatInConsoleDto> statInConsoleDtosList = statsClient.getStats(LocalDateTime.now().minusYears(100), LocalDateTime.now().plusYears(100), eventFullDtosId, false);
        Map<Integer, Integer> statInConsoleDtosMap = new HashMap<>();
        for (StatInConsoleDto statInConsoleDto: statInConsoleDtosList) {
            String numberString = statInConsoleDto.getUri().replace("/events/", "");
            Integer numberInteger = Integer.parseInt(numberString);
            statInConsoleDtosMap.put(numberInteger, statInConsoleDto.getHits());
        }
        for (EventFullDto eventFullDto : eventFullDtos) {
            eventFullDto.setViews(statInConsoleDtosMap.getOrDefault(eventFullDto.getId(), 0));
        }
        return eventFullDtos;
    }
}