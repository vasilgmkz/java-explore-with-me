package ru.practicum;

import java.util.List;

public interface StatsService {
    void addHit(StatFromConsoleDto statFromConsoleDto);

    List<StatInConsoleDto> getStats(String start, String end, List<String> uris, boolean unique);
}
