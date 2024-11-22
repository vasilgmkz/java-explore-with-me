package ru.practicum.privates.events;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.privates.events.model.EventDto;

public interface PrivateEventsRepository extends JpaRepository<EventDto, Long> {

    @Query(value = "select count(*) from events where category_id = :catId", nativeQuery = true)
    Long countByCategoryId(@Param("catId") Long catId);
}
