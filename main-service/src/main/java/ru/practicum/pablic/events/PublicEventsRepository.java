package ru.practicum.pablic.events;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.privates.events.model.EventDto;

import java.time.LocalDateTime;
import java.util.List;

public interface PublicEventsRepository extends JpaRepository<EventDto, Long> {


    @Query(value = "select * from events where (lower(event_annotation) like LOWER(CONCAT('%',:text ,'%')) \n" +
            "or lower(event_description) like LOWER(CONCAT('%',:text ,'%')))\n" +
            "and case when (:categories) is not null then category_id in (:categories) else category_id in (category_id) end\n" +
            "and case when :paid = true then event_paid = true  when :paid = false then event_paid = false else event_paid = event_paid end\n" +
            "and event_state = 'PUBLISHED'\n" +
            "and event_event_date between :rangeStart and :rangeEnd\n" +
            "OFFSET :from LIMIT :size ", nativeQuery = true)
    List<EventDto> getEvents(@Param("text") String text,
                             @Param("categories") List<Integer> categories,
                             @Param("paid") Boolean paid,
                             @Param("rangeStart") LocalDateTime rangeStart,
                             @Param("rangeEnd") LocalDateTime rangeEnd,
                             @Param("from") Long from,
                             @Param("size") Long size);
}
