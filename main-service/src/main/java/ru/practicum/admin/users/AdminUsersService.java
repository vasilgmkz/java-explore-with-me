package ru.practicum.admin.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.admin.AdminService;
import ru.practicum.admin.users.dto.UserShortDtoFromConsole;
import ru.practicum.admin.users.mapper.UserMapperMapStruct;
import ru.practicum.admin.users.model.UserDto;
import ru.practicum.exeption.NotFoundException;

import java.util.List;

@Service("adminUsersService")
@RequiredArgsConstructor
public class AdminUsersService implements AdminService {
    private final UserMapperMapStruct userMapperMapStruct;
    private final AdminUsersRepository adminUsersRepository;

    @Override
    public UserDto addUser(UserShortDtoFromConsole userShortDtoFromConsole) {
        UserDto userDto = userMapperMapStruct.inUserDto(userShortDtoFromConsole);
        Integer id = adminUsersRepository.save(userDto).getId();
        userDto.setId(id);
        return userDto;
    }

    @Override
    public List<UserDto> getUsers(List<Integer> ids, Long from, Long size) {
        if (ids == null || ids.isEmpty()) {
            return adminUsersRepository.getUsers(from, size);
        } else {
            return adminUsersRepository.getUsersWithIds(from, size, ids);
        }
    }

    @Override
    public void deleteUser(Long userId) {
        adminUsersRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id=%d was not found", userId)));
        adminUsersRepository.deleteById(userId);
    }
}
