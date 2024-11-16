package ru.practicum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.model.EndpointHit;
import ru.practicum.model.EndpointHitShort;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepositoryJpa extends JpaRepository<EndpointHit, Long> {

    @Query(value = "select eh.endpointhit_app as app, eh.endpointhit_uri as uri, count(*) as hits from EndpointHit as eh  " +
            "where eh.endpointhit_timestamp between :start and :end group by (eh.endpointhit_app, eh.endpointhit_uri)", nativeQuery = true)
    List<EndpointHitShort> betweenStartAndEnd(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query(value = "select ehs.endpointhit_app as app, ehs.endpointhit_uri as uri, count(*) as hits from (select distinct eh.endpointhit_app, eh.endpointhit_uri, eh.endpointhit_ip from EndpointHit as eh where eh.endpointhit_timestamp between :start and :end) as ehs group by (ehs.endpointhit_app, ehs.endpointhit_uri)", nativeQuery = true)
    List<EndpointHitShort> betweenStartAndEndAndUnique(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query(value = "select eh.endpointhit_app as app, eh.endpointhit_uri as uri, count(*) as hits from EndpointHit as eh " +
            "where eh.endpointhit_uri in (:uris) and eh.endpointhit_timestamp between :start and :end group by (eh.endpointhit_app, eh.endpointhit_uri)", nativeQuery = true)
    List<EndpointHitShort> betweenStartAndEndAndUris(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("uris") List<String> uris);

    @Query(value = "select ehs.endpointhit_app as app, ehs.endpointhit_uri as uri, count(*) as hits from (select distinct eh.endpointhit_app, eh.endpointhit_uri, eh.endpointhit_ip from EndpointHit as eh where eh.endpointhit_uri in (:uris) and eh.endpointhit_timestamp between :start and :end) as ehs group by (ehs.endpointhit_app, ehs.endpointhit_uri)", nativeQuery = true)
    List<EndpointHitShort> betweenStartAndEndAndUrisAndUnique(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("uris") List<String> uris);
}