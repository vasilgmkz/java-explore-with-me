package ru.practicum.admin.locations;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.admin.AdminService;
import ru.practicum.admin.locations.dto.LocationFromConsole;
import ru.practicum.admin.locations.dto.LocationInConsole;
import ru.practicum.admin.locations.mapper.LocationsMapperMapStruct;
import ru.practicum.admin.locations.model.Location;
import ru.practicum.exeption.NotFoundException;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service("adminLocationsService")
@RequiredArgsConstructor
public class AdminLocationsService implements AdminService {

    private final LocationsMapperMapStruct locationsMapperMapStruct;
    private final AdminLocationsRepository adminLocationsRepository;

    @Override
    public LocationInConsole addLocation(LocationFromConsole locationFromConsole) {
        Location location = locationsMapperMapStruct.inLocation(locationFromConsole);
        location.setCreated(LocalDateTime.now());
        adminLocationsRepository.save(location);
        return locationsMapperMapStruct.inLocationInConsole(location);
    }

    @Override
    public LocationInConsole updateLocation(LocationFromConsole locationFromConsole, Long locationId) {
        Location location = adminLocationsRepository.findById(locationId)
                .orElseThrow(() -> new NotFoundException(String.format("Location with id=%d was not found", locationId)));
        if (locationFromConsole.getName() != null) {
            location.setName(locationFromConsole.getName());
        }
        if (locationFromConsole.getLon() != null) {
            location.setLon(locationFromConsole.getLon());
        }
        if (locationFromConsole.getLat() != null) {
            location.setLat(locationFromConsole.getLat());
        }
        if (locationFromConsole.getRadius() != null) {
            location.setRadius(locationFromConsole.getRadius());
        }
        adminLocationsRepository.save(location);
        return locationsMapperMapStruct.inLocationInConsole(location);
    }

    @Override
    public void deleteLocation(Long locationId) {
        adminLocationsRepository.findById(locationId)
                .orElseThrow(() -> new NotFoundException(String.format("Location with id=%d was not found", locationId)));
        adminLocationsRepository.deleteById(locationId);
    }

    @Override
    public LocationInConsole getLocation(Long locationId) {
        Location location = adminLocationsRepository.findById(locationId)
                .orElseThrow(() -> new NotFoundException(String.format("Location with id=%d was not found", locationId)));
        return locationsMapperMapStruct.inLocationInConsole(location);
    }

    @Override
    public List<LocationInConsole> getLocations() {
        List<Location> locations = adminLocationsRepository.findAll();
        locations.sort(Comparator.comparing(Location::getCreated).reversed());
        return locations.stream().map(locationsMapperMapStruct::inLocationInConsole).toList();
    }
}
