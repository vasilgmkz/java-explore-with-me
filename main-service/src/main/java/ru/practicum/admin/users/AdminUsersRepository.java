package ru.practicum.admin.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.admin.users.model.UserDto;

import java.util.List;

public interface AdminUsersRepository extends JpaRepository<UserDto, Long> {
    @Query(value = "select * from users order by user_id OFFSET :from LIMIT :size", nativeQuery = true)
    List<UserDto> getUsers(@Param("from") Long from, @Param("size") Long size);

    @Query(value = "select * from users where user_id in (:ids) order by user_id OFFSET :from LIMIT :size", nativeQuery = true)
    List<UserDto> getUsersWithIds(@Param("from") Long from, @Param("size") Long size, @Param("ids") List<Integer> ids);
}