package ru.practicum.privates.events;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.privates.events.model.EventDto;

import java.util.List;

public interface PrivateEventsRepository extends JpaRepository<EventDto, Long> {

    @Query(value = "select count(*) from events where category_id = :catId", nativeQuery = true)
    Long countByCategoryId(@Param("catId") Long catId);

    @Query(value = "select * from events where user_id = :userId order by event_id OFFSET :from LIMIT :size", nativeQuery = true)
    List<EventDto> getEventsByUserId(@Param("userId") Long userId, @Param("from") Long from, @Param("size") Long size);
}
