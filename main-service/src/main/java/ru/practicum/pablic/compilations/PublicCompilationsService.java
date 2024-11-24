package ru.practicum.pablic.compilations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.admin.compilations.dto.CompilationDto;
import ru.practicum.admin.compilations.mapper.CompilationsMapperMapStruct;
import ru.practicum.admin.compilations.model.Compilation;
import ru.practicum.converter.Converter;
import ru.practicum.pablic.PublicService;

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
        List<CompilationDto> compilationDtoList = compilationList.stream().map(compilationsMapperMapStruct::inCompilationDtoFromCompilation).toList();
        return converter.addConfirmedRequestsAndViewsInCompilationDtoList(compilationDtoList);
    }
}
