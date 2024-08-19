package com.dardanqsot.eval.service;

import com.dardanqsot.eval.dto.UserRequestDto;
import com.dardanqsot.eval.dto.UserResponseDto;
import com.dardanqsot.eval.model.User;

public interface UserService extends CRUD<User, Integer> {
    UserResponseDto createUser(UserRequestDto userRequestDto);
}
