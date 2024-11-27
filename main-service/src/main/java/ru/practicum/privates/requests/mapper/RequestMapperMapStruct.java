package ru.practicum.privates.requests.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.practicum.privates.requests.dto.ParticipationRequestDto;
import ru.practicum.privates.requests.model.ParticipationRequest;
import ru.practicum.privates.requests.model.RequestStatus;

import java.time.LocalDateTime;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {RequestMapperMapStructUtil.class}, imports = {
        LocalDateTime.class, RequestStatus.class})
public interface RequestMapperMapStruct {

    @Mapping(target = "event", qualifiedByName = {"RequestMapperMapStructUtil", "getEventId"}, source = "participationRequest")
    @Mapping(target = "requester", qualifiedByName = {"RequestMapperMapStructUtil", "getRequesterId"}, source = "participationRequest")
    ParticipationRequestDto inParticipationRequestDtoFromParticipationRequest(ParticipationRequest participationRequest);
}
