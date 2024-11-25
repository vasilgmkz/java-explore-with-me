package ru.practicum.pablic.compilations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.admin.compilations.model.Compilation;

import java.util.List;

public interface PublicCompilationsRepository extends JpaRepository<Compilation, Long> {

    @Query(value = "select * from compilations where \n" +
            "compilation_pinned = CASE when :pinned = true then true \n" +
            "when :pinned = false then false else compilation_pinned end\n" +
            "order by compilation_id ASC\n" +
            "OFFSET :from LIMIT :size \n", nativeQuery = true)
    List<Compilation> getCompilations(@Param("from") Long from, @Param("size") Long size, @Param("pinned") Boolean pinned);
}
