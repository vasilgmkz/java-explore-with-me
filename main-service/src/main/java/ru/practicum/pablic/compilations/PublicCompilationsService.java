package ru.practicum.pablic.compilations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.admin.compilations.dto.CompilationDto;
import ru.practicum.admin.compilations.mapper.CompilationsMapperMapStruct;
import ru.practicum.admin.compilations.model.Compilation;
import ru.practicum.converter.Converter;
import ru.practicum.exeption.NotFoundException;
import ru.practicum.pablic.PublicService;

import java.util.ArrayList;
import java.util.List;

@Service("publicCompilationsService")
@RequiredArgsConstructor
public class PublicCompilationsService implements PublicService {
    private final PublicCompilationsRepository publicCompilationsRepository;
    private final CompilationsMapperMapStruct compilationsMapperMapStruct;
    private final Converter converter;

    @Override
    public List<CompilationDto> getCompilations(Long from, Long size, Boolean pinned) {
        List<Compilation> compilationList = publicCompilationsRepository.getCompilations(from, size, pinned);
        if (compilationList.isEmpty()) {
            return new ArrayList<>();
        }
        List<CompilationDto> compilationDtoList = compilationList.stream().map(compilationsMapperMapStruct::inCompilationDtoFromCompilation).toList();
        return converter.addConfirmedRequestsAndViewsInCompilationDtoList(compilationDtoList);
    }

    @Override
    public CompilationDto getCompilationById(Long compId) {
        Compilation compilation = publicCompilationsRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException(String.format("Compilation with id=%d was not found", compId)));
        CompilationDto compilationDto = compilationsMapperMapStruct.inCompilationDtoFromCompilation(compilation);
        if (compilationDto.getEvents().isEmpty()) {
            return compilationDto;
        }
        return converter.addConfirmedRequestsAndViewsInCompilationDtoList(List.of(compilationDto)).getFirst();
    }
}
