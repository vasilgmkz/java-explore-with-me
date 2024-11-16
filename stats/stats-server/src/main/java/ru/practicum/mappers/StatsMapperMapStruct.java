package ru.practicum.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.practicum.StatFromConsoleDto;
import ru.practicum.StatInConsoleDto;
import ru.practicum.model.EndpointHit;
import ru.practicum.model.EndpointHitShort;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StatsMapperMapStruct {

    @Mapping(target = "id", ignore = true)
    EndpointHit inEndpointHit(StatFromConsoleDto statFromConsoleDto);

    @Mapping(target = "app", expression = "java(endpointHitShort.getApp())")
    @Mapping(target = "uri", expression = "java(endpointHitShort.getUri())")
    @Mapping(target = "hits", expression = "java(endpointHitShort.getHits())")
    StatInConsoleDto inStatInConsoleDtoFromEndpointHitShort(EndpointHitShort endpointHitShort);
}

