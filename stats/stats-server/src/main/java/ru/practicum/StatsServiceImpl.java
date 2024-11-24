package ru.practicum;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.mappers.StatsMapperMapStruct;
import ru.practicum.model.EndpointHit;
import ru.practicum.model.EndpointHitShort;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final StatsMapperMapStruct statsMapperMapStruct;
    private final StatsRepositoryJpa statsRepositoryJpa;

    @Override
    public void addHit(StatFromConsoleDto statFromConsoleDto) {
        EndpointHit endpointHit = statsMapperMapStruct.inEndpointHit(statFromConsoleDto);
        statsRepositoryJpa.save(endpointHit);
    }

    @Override
    public List<StatInConsoleDto> getStats(LocalDateTime startDateTime, LocalDateTime endDateTime, List<String> uris, boolean unique) {
        if (uris == null || uris.isEmpty()) {
            List<EndpointHitShort> endpointHitsShort;
            if (!unique) {
                endpointHitsShort = statsRepositoryJpa.betweenStartAndEnd(startDateTime, endDateTime);
            } else {
                endpointHitsShort = statsRepositoryJpa.betweenStartAndEndAndUnique(startDateTime, endDateTime);
            }
            return statInConsoleDtoList(endpointHitsShort);
        } else {
            List<EndpointHitShort> endpointHitsShort;
            if (!unique) {
                endpointHitsShort = statsRepositoryJpa.betweenStartAndEndAndUris(startDateTime, endDateTime, uris);
            } else {
                endpointHitsShort = statsRepositoryJpa.betweenStartAndEndAndUrisAndUnique(startDateTime, endDateTime, uris);
            }
            return statInConsoleDtoList(endpointHitsShort);
        }
    }

    private List<StatInConsoleDto> statInConsoleDtoList(List<EndpointHitShort> endpointHitsShort) {
        return endpointHitsShort.stream().map(statsMapperMapStruct::inStatInConsoleDtoFromEndpointHitShort).toList();
    }
}


