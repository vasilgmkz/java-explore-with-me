package ru.practicum.admin.locations.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.practicum.admin.locations.dto.LocationFromConsole;
import ru.practicum.admin.locations.dto.LocationInConsole;
import ru.practicum.admin.locations.model.Location;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LocationsMapperMapStruct {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    Location inLocation(LocationFromConsole locationFromConsole);

    LocationInConsole inLocationInConsole(Location location);
}
