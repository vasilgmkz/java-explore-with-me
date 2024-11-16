package ru.practicum;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsClient {
    void addHit(StatFromConsoleDto statFromConsoleDto);

    List<StatInConsoleDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);
}
