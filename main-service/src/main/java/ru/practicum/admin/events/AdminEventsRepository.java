package ru.practicum.admin.events;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.privates.events.model.EventDto;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminEventsRepository extends JpaRepository<EventDto, Long> {

    @Query(value = "select * from events where case when (:users) is not null then user_id in (:users) else user_id in (user_id) end\n" +
            "and case when (:states) is not null then event_state in (:states) else event_state in (event_state) end\n" +
            "and case when (:categories) is not null then category_id in (:categories) else category_id in (category_id) end\n" +
            "and event_event_date between :rangeStart and :rangeEnd\n" +
            "order by event_id OFFSET :from LIMIT :size", nativeQuery = true)
    List<EventDto> getEventsAdmin(@Param("users") List<Integer> users,
                                  @Param("states") List<String> states,
                                  @Param("categories") List<Integer> categories,
                                  @Param("rangeStart") LocalDateTime rangeStart,
                                  @Param("rangeEnd") LocalDateTime rangeEnd,
                                  @Param("from") Integer from,
                                  @Param("size") Integer size
    );
}
