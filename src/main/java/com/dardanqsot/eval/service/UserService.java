package com.dardanqsot.eval.service;

import com.dardanqsot.eval.dto.UserRequestDto;
import com.dardanqsot.eval.dto.UserResponseDto;
import com.dardanqsot.eval.model.User;

import java.util.UUID;

public interface UserService extends CRUD<User, Integer> {
    UserResponseDto createUser(UserRequestDto userRequestDto);
    User findByUuid(UUID uuid);
    User updateUser(UserRequestDto userRequestDto, UUID uuid);
    void deleteUser(UUID uuid);
}
