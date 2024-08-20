package com.dardanqsot.eval.service;

import com.dardanqsot.eval.dto.UserDto;
import com.dardanqsot.eval.dto.UserRequestDto;
import com.dardanqsot.eval.dto.UserSaveResponseDto;
import com.dardanqsot.eval.model.User;

import java.util.UUID;

public interface UserService extends CRUD<User, Integer> {
    UserSaveResponseDto createUser(UserRequestDto userRequestDto);
    User findByUuid(UUID uuid);
    UserSaveResponseDto updateUser(UserDto userDto, UUID uuid);
    void deleteUser(UUID uuid);
}
