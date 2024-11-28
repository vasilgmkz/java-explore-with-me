package ru.practicum.admin.locations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.admin.locations.model.Location;


public interface AdminLocationsRepository extends JpaRepository<Location, Long>, QuerydslPredicateExecutor<Location> {
}
