package ru.practicum;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    void addHit(StatFromConsoleDto statFromConsoleDto);

    List<StatInConsoleDto> getStats(LocalDateTime startDateTime, LocalDateTime endDateTime, List<String> uris, boolean unique);
}
