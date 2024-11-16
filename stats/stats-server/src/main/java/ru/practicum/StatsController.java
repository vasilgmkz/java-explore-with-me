package ru.practicum;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
public class StatsController {
    private final StatsService statsService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void addHit(@RequestBody @Valid StatFromConsoleDto statFromConsoleDto) {
        statsService.addHit(statFromConsoleDto);
    }

    @GetMapping("/stats")
    @ResponseStatus(HttpStatus.OK)
    public List<StatInConsoleDto> getStats(@RequestParam(required = true, name = "start") String start,
                                           @RequestParam(required = true, name = "end") String end,
                                           @RequestParam(required = false, name = "uris") List<String> uris,
                                           @RequestParam(defaultValue = "false", name = "unique") boolean unique) {
        return statsService.getStats(start, end, uris, unique);
    }
}
