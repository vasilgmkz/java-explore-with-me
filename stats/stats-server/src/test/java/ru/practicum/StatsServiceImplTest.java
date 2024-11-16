package ru.practicum;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.mappers.StatsMapperMapStruct;
import ru.practicum.model.EndpointHit;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class StatsServiceImplTest {
    private final StatsMapperMapStruct statsMapperMapStruct;

    EndpointHit endpointHit;
    StatInConsoleDto statInConsoleDto1;
    StatInConsoleDto statInConsoleDto2;


    @BeforeEach
    void setUp() {
        endpointHit = new EndpointHit();
        endpointHit.setId(1);
        endpointHit.setApp("App");
        endpointHit.setUri("Uri");
        endpointHit.setIp("Ip");
        endpointHit.setTimestamp(LocalDateTime.now());
        statInConsoleDto1 = new StatInConsoleDto();
        statInConsoleDto1.setApp("App");
        statInConsoleDto1.setUri("Uri");
        statInConsoleDto1.setHits(1);
        statInConsoleDto2 = new StatInConsoleDto();
        statInConsoleDto2.setApp("App");
        statInConsoleDto2.setUri("Uri");
        statInConsoleDto2.setHits(2);
    }

    @Test
    @DisplayName("Сравнение StatInConsoleDto")
    void testEqualsStatInConsoleDto() {
        assertEquals(statInConsoleDto1, statInConsoleDto2);
    }

}