package ru.practicum.admin.locations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import ru.practicum.admin.locations.model.Location;

import java.util.Optional;


public interface AdminLocationsRepository extends JpaRepository<Location, Long>, QuerydslPredicateExecutor<Location> {
    @Query(value = "select * from locations where (lower(location_name) like LOWER(:locationName))", nativeQuery = true)
    Optional<Location> gettingLocationByName(@Param("locationName") String locationName);
}