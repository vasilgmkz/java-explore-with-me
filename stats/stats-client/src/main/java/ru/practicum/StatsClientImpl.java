package ru.practicum;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@Slf4j
public class StatsClientImpl implements StatsClient {
    final RestClient restClient;
    final String statUrl;

    public StatsClientImpl(@Value("${client.url}") String statUrl) {
        this.restClient = RestClient.create();
        this.statUrl = statUrl;
    }

    @Override
    public void addHit(StatFromConsoleDto statFromConsoleDto) {
        try {
            ResponseEntity<Void> response = restClient.post()
                    .uri(statUrl + "/hit")
                    .contentType(APPLICATION_JSON)
                    .body(statFromConsoleDto)
                    .retrieve()
                    .toBodilessEntity();
        } catch (ResourceAccessException e) {
            return;
        }
    }

    @Override
    public List<StatInConsoleDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        String startString = start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String endString = end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String urisString = uris.stream().collect(Collectors.joining(","));
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost:9090")
                .path("/stats")
                .query("start={keyword}")
                .query("end={keyword}")
                .query("uris={keyword}")
                .query("unique={keyword}")
                .buildAndExpand(startString, endString, urisString, unique);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            log.info("uriComponents: {}", uriComponents.toUriString());
            return restClient.get()
                    .uri(uriComponents.toUriString())
                    .exchange((request, response) -> {
                        try {
                            if (response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(200))) {
                                return objectMapper.readValue(response.getBody(), new TypeReference<>() {
                                });
                            } else {
                                log.warn("ErrorStatServer");
                                throw new ResourceAccessException("ErrorStatServer");
                            }
                        } catch (ResourceAccessException e) {
                            log.info("logResourceAccessException1: {}", e.getMessage());
                            return new ArrayList<>();
                        }
                    });
        } catch (ResourceAccessException e) {
            log.info("logResourceAccessException2: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void main(String[] args) {
    }
}