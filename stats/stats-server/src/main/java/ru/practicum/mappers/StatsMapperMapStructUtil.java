package ru.practicum.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import ru.practicum.StatFromConsoleDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Named("StatsMapperMapStructUtil")
@RequiredArgsConstructor
public class StatsMapperMapStructUtil {

    @Named("inLocalDateTime")
    LocalDateTime inLocalDateTime(StatFromConsoleDto statFromConsoleDto) throws JsonProcessingException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(statFromConsoleDto.getTimestamp(), formatter);
    }
}
