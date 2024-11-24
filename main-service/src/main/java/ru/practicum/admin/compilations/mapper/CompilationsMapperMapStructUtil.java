package ru.practicum.admin.compilations.mapper;


import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import ru.practicum.admin.compilations.model.Compilation;
import ru.practicum.privates.events.dto.EventShortDto;
import ru.practicum.privates.events.mapper.EventMapperMapStruct;

import java.util.ArrayList;
import java.util.List;

@Component
@Named("CompilationsMapperMapStructUtil")
@RequiredArgsConstructor
public class CompilationsMapperMapStructUtil {
    private final EventMapperMapStruct eventMapperMapStruct;

    @Named("getEventShortDtos")
    List<EventShortDto> getEventShortDtos(Compilation compilation) {
        if (compilation.getEvents().isEmpty()) {
            return new ArrayList<>();
        }
        return compilation.getEvents().stream().map(eventMapperMapStruct::inEventShortDtoFromEventDto).toList();
    }
}