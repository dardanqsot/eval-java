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
import java.util.UUID;

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

    @GetMapping("/{uuid}")
    public ResponseEntity<ResponseDto<UserRequestDto>> findById(@PathVariable("uuid") UUID uuid){
        User obj = service.findByUuid(uuid);
        return new ResponseEntity<>(ResponseDto.successResponse(convertToDto(obj)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDto<UserResponseDto>> save(@Valid @RequestBody UserRequestDto dto){
        return new ResponseEntity<>(ResponseDto.successResponse(service.createUser(dto)), HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ResponseDto<UserRequestDto>> update(@Valid @PathVariable("uuid") UUID uuid, @RequestBody UserRequestDto dto) throws Exception {
        User obj = service.updateUser(dto, uuid);
        return new ResponseEntity<>(ResponseDto.successResponse(convertToDto(obj)), HttpStatus.OK);

    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ResponseDto<Void>> delete(@PathVariable("uuid") UUID uuid){
        service.deleteUser(uuid);
        return new ResponseEntity<>(ResponseDto.successResponse(null), HttpStatus.NO_CONTENT);
    }
    private UserRequestDto convertToDto(User obj){
        return mapper.map(obj, UserRequestDto.class);
    }

}
