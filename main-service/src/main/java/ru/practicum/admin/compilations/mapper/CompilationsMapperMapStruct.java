package ru.practicum.admin.compilations.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.practicum.admin.compilations.dto.CompilationDto;
import ru.practicum.admin.compilations.model.Compilation;
import ru.practicum.privates.events.model.State;

import java.time.LocalDateTime;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {CompilationsMapperMapStructUtil.class}, imports = {
        LocalDateTime.class, State.class})
public interface CompilationsMapperMapStruct {


    @Mapping(target = "events", qualifiedByName = {"CompilationsMapperMapStructUtil", "getEventShortDtos"}, source = "compilation")
    CompilationDto inCompilationDtoFromCompilation(Compilation compilation);
}
