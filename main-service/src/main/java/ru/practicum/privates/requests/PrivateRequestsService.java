package ru.practicum.privates.requests;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.admin.users.AdminUsersRepository;
import ru.practicum.admin.users.model.UserDto;
import ru.practicum.exeption.EditingConditionsException;
import ru.practicum.exeption.NotFoundException;
import ru.practicum.exeption.Status;
import ru.practicum.privates.PrivateService;
import ru.practicum.privates.events.PrivateEventsRepository;
import ru.practicum.privates.events.model.EventDto;
import ru.practicum.privates.events.model.State;
import ru.practicum.privates.requests.dto.ParticipationRequestDto;
import ru.practicum.privates.requests.mapper.RequestMapperMapStruct;
import ru.practicum.privates.requests.model.ParticipationRequest;
import ru.practicum.privates.requests.model.RequestStatus;

import java.time.LocalDateTime;
import java.util.List;

@Service("privateRequestsService")
@RequiredArgsConstructor
public class PrivateRequestsService implements PrivateService {
    private final PrivateRequestsRepository privateRequestsRepository;
    private final PrivateEventsRepository privateEventsRepository;
    private final AdminUsersRepository adminUsersRepository;
    private final RequestMapperMapStruct requestMapperMapStruct;

    @Override
    public ParticipationRequestDto addRequest(Long userId, Long eventId) {
        EventDto eventDto = privateEventsRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("Event with id=%d was not found", eventId)));
        UserDto requester = adminUsersRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id=%d was not found", userId)));
        if (eventDto.getInitiator().getId().equals(userId.intValue())) {
            throw new EditingConditionsException("The event initiator cannot add a request to participate in their event.", Status.FORBIDDEN);
        }
        if (!eventDto.getState().equals(State.PUBLISHED)) {
            throw new EditingConditionsException("You cannot participate in an unpublished event.", Status.FORBIDDEN);
        }
        Long countByEventId = privateRequestsRepository.countByEventId(eventId);
        if (!eventDto.getParticipantLimit().equals(0) && countByEventId >= eventDto.getParticipantLimit()) {
            throw new EditingConditionsException("The participation request limit has been reached.", Status.FORBIDDEN);
        }
        ParticipationRequest participationRequest = new ParticipationRequest();
        participationRequest.setRequester(requester);
        participationRequest.setEvent(eventDto);
        participationRequest.setCreated(LocalDateTime.now());
        if (eventDto.getRequestModeration()) {
            participationRequest.setStatus(RequestStatus.PENDING);
        } else {
            participationRequest.setStatus(RequestStatus.CONFIRMED);
        }
        Integer id = privateRequestsRepository.save(participationRequest).getId();
        participationRequest.setId(id);
        return requestMapperMapStruct.inParticipationRequestDtoFromParticipationRequest(participationRequest);
    }

    @Override
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        UserDto requester = adminUsersRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id=%d was not found", userId)));
        ParticipationRequest participationRequest = privateRequestsRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException(String.format("Request with id=%d was not found", requestId)));
        if (!participationRequest.getRequester().getId().equals(userId.intValue())) {
            throw new EditingConditionsException("Only the request owner can cansel it.", Status.FORBIDDEN);
        }
        privateRequestsRepository.deleteById(requestId);
        return requestMapperMapStruct.inParticipationRequestDtoFromParticipationRequest(participationRequest);
    }

    @Override
    public List<ParticipationRequestDto> getRequests(Long userId) {
        adminUsersRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id=%d was not found", userId)));
        List<ParticipationRequest> participationRequests = privateRequestsRepository.getRequests(userId);
        return participationRequests.stream().map(requestMapperMapStruct::inParticipationRequestDtoFromParticipationRequest).toList();
    }

    @Override
    public List<ParticipationRequestDto> getRequestsByUserIdAndEventId(Long userId, Long eventId) {
        EventDto eventDto = privateEventsRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("Event with id=%d was not found", eventId)));
        if (!eventDto.getInitiator().getId().equals(userId.intValue())) {
            throw new EditingConditionsException("Only the event owner can view it", Status.FORBIDDEN);
        }
        List<ParticipationRequest> participationRequests = privateRequestsRepository.getRequestsByUserIdAndEventId(eventId);
        return participationRequests.stream().map(requestMapperMapStruct::inParticipationRequestDtoFromParticipationRequest).toList();
    }
}
