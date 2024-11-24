package ru.practicum.admin.compilations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.admin.AdminService;
import ru.practicum.admin.compilations.dto.CompilationDto;
import ru.practicum.admin.compilations.dto.CompilationDtoFromConsole;
import ru.practicum.admin.compilations.model.Compilation;
import ru.practicum.admin.events.AdminEventsRepository;
import ru.practicum.converter.Converter;
import ru.practicum.exeption.NotFoundException;
import ru.practicum.privates.events.dto.EventShortDto;
import ru.practicum.privates.events.mapper.EventMapperMapStruct;

import java.util.ArrayList;
import java.util.List;

@Service("adminCompilationsService")
@RequiredArgsConstructor
public class AdminCompilationsService implements AdminService {
    private final AdminEventsRepository adminEventsRepository;
    private final AdminCompilationsRepository adminCompilationsRepository;
    private final Converter converter;
    private final EventMapperMapStruct eventMapperMapStruct;

    @Override
    public CompilationDto addCompilation(CompilationDtoFromConsole compilationDtoFromConsole) {
        Compilation compilation = new Compilation();
        compilation.setPinned(compilationDtoFromConsole.getPinned());
        compilation.setTitle(compilationDtoFromConsole.getTitle());
        compilation.setEvents(adminEventsRepository.getEventsForCompilation(compilationDtoFromConsole.getEvents()));
        Integer id = adminCompilationsRepository.save(compilation).getId();
        CompilationDto compilationDto = new CompilationDto();
        compilationDto.setId(id);
        compilationDto.setPinned(compilation.getPinned());
        compilationDto.setTitle(compilation.getTitle());
        if (compilation.getEvents().isEmpty()) {
            compilationDto.setEvents(new ArrayList<>());
        } else {
            List<EventShortDto> eventShortDtos = compilation.getEvents().stream().map(eventMapperMapStruct::inEventShortDtoFromEventDto).toList();
            compilationDto.setEvents(converter.addConfirmedRequestsAndViewsInEventShortDto(eventShortDtos));
        }
        return compilationDto;
    }

    @Override
    public void deleteCompilation(Long compId) {
        adminCompilationsRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException(String.format("Compilation with id=%d was not found", compId)));
        adminCompilationsRepository.deleteById(compId);
    }


    @Override
    public CompilationDto updateCompilation(CompilationDtoFromConsole compilationDtoFromConsole, Long compId) {
        Compilation compilation = adminCompilationsRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException(String.format("Compilation with id=%d was not found", compId)));
        if (compilationDtoFromConsole.getPinned() != null) {
            compilation.setPinned(compilationDtoFromConsole.getPinned());
        }
        if (compilationDtoFromConsole.getTitle() != null) {
            compilation.setTitle(compilationDtoFromConsole.getTitle());
        }
        if (compilationDtoFromConsole.getEvents() != null) {
            compilation.setEvents(adminEventsRepository.getEventsForCompilation(compilationDtoFromConsole.getEvents()));
        }
        adminCompilationsRepository.save(compilation);
        CompilationDto compilationDto = new CompilationDto();
        compilationDto.setId(compilation.getId());
        compilationDto.setPinned(compilation.getPinned());
        compilationDto.setTitle(compilation.getTitle());
        if (compilation.getEvents().isEmpty()) {
            compilationDto.setEvents(new ArrayList<>());
        } else {
            List<EventShortDto> eventShortDtos = compilation.getEvents().stream().map(eventMapperMapStruct::inEventShortDtoFromEventDto).toList();
            compilationDto.setEvents(converter.addConfirmedRequestsAndViewsInEventShortDto(eventShortDtos));
        }
        return compilationDto;
    }
}
