package ru.practicum.privates.requests;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.privates.requests.model.CountConfirmedRequest;
import ru.practicum.privates.requests.model.ParticipationRequest;

import java.util.List;

public interface PrivateRequestsRepository extends JpaRepository<ParticipationRequest, Long> {
    @Query(value = "select count(*) from requests where event_id = :eventId", nativeQuery = true)
    Long countByEventId(@Param("eventId") Long eventId);

    @Query(value = "select count(*) from requests where event_id = :eventId and request_status = 'CONFIRMED'", nativeQuery = true)
    Long countByEventIdAndStatus(@Param("eventId") Long eventId);

    @Query(value = "select * from requests where user_id = :userId", nativeQuery = true)
    List<ParticipationRequest> getRequests(@Param("userId") Long userId);

    @Query(value = "select event_id as eventId, count(*) from requests where request_status = 'CONFIRMED' group by event_id", nativeQuery = true)
    List<CountConfirmedRequest> getCountConfirmedRequest();
}
