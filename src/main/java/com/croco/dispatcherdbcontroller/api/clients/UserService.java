package com.croco.dispatcherdbcontroller.api.clients;

import com.croco.dispatcherdbcontroller.dto.UserDto;

import java.io.IOException;
import java.util.List;

public interface UserService {
    List<UserDto> getList();
    UserDto getOne(Long id);
    List<UserDto> getMany(List<Long> ids);
    UserDto create(UserDto user);
    UserDto patch(Long id, UserDto userDto) throws IOException;
    UserDto update(Long id, UserDto userDto);
    UserDto delete(Long id);
    void deleteMany(List<Long> ids);
}