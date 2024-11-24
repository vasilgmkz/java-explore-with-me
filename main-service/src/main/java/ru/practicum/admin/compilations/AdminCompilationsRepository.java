package ru.practicum.admin.compilations;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.admin.compilations.model.Compilation;

public interface AdminCompilationsRepository extends JpaRepository<Compilation, Long> {
}
