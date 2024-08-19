package com.dardanqsot.eval.controller;

import com.dardanqsot.eval.dto.ResponseDto;
import com.dardanqsot.eval.dto.UserRequestDto;
import com.dardanqsot.eval.dto.UserResponseDto;
import com.dardanqsot.eval.model.User;
import com.dardanqsot.eval.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final ModelMapper mapper;
    private final UserService service;

    @GetMapping
    public ResponseEntity<ResponseDto<List<UserRequestDto>>> findAll(){
        List<UserRequestDto> lst = service.findAll().stream().map(this::convertToDto).toList();
        return new ResponseEntity<>(ResponseDto.successResponse(lst), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<UserRequestDto>> findById(@PathVariable("id") Integer id){
        User obj = service.findById(id);
        return new ResponseEntity<>(ResponseDto.successResponse(convertToDto(obj)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDto<UserResponseDto>> save(@Valid @RequestBody UserRequestDto dto){
        return new ResponseEntity<>(ResponseDto.successResponse(service.createUser(dto)), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserRequestDto> update(@Valid @PathVariable("id") Integer id, @RequestBody UserRequestDto dto) throws Exception {
        User obj = service.update(convertToEntity(dto), id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    private UserRequestDto convertToDto(User obj){
        return mapper.map(obj, UserRequestDto.class);
    }

    private User convertToEntity(UserRequestDto dto){
        return mapper.map(dto, User.class);
    }
}
