package ru.practicum.admin.users.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.practicum.admin.users.dto.UserShortDto;
import ru.practicum.admin.users.dto.UserShortDtoFromConsole;
import ru.practicum.admin.users.model.UserDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapperMapStruct {
    @Mapping(target = "id", ignore = true)
    UserDto inUserDto(UserShortDtoFromConsole userShortDtoFromConsole);

    UserShortDto inUserShortDto(UserDto userDto);
}
