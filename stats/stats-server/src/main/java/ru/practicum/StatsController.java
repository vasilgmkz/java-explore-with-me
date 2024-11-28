package ru.practicum;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@Slf4j
public class StatsController {
    private final StatsService statsService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void addHit(@RequestBody @Valid StatFromConsoleDto statFromConsoleDto) {
        log.info("Запрос на сохранение в статистике: {} + {}", statFromConsoleDto.getIp(), statFromConsoleDto.getUri());
        statsService.addHit(statFromConsoleDto);
    }

    @GetMapping("/stats")
    @ResponseStatus(HttpStatus.OK)
    public List<StatInConsoleDto> getStats(@RequestParam(required = true, name = "start") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                           @RequestParam(required = true, name = "end") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                           @RequestParam(required = false, name = "uris") List<String> uris,
                                           @RequestParam(defaultValue = "false", name = "unique") boolean unique) {
        return statsService.getStats(start, end, uris, unique);
    }
}
