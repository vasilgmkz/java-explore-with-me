package ru.practicum.privates.requests.mapper;


import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import ru.practicum.privates.requests.model.ParticipationRequest;

@Component
@Named("RequestMapperMapStructUtil")
@RequiredArgsConstructor
public class RequestMapperMapStructUtil {

    @Named("getEventId")
    Integer getEventId(ParticipationRequest participationRequest) {
        return participationRequest.getEvent().getId();
    }

    @Named("getRequesterId")
    Integer getRequesterId(ParticipationRequest participationRequest) {
        return participationRequest.getRequester().getId();
    }
}

