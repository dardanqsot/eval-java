package com.dardanqsot.eval.controller;

import com.dardanqsot.eval.dto.*;
import com.dardanqsot.eval.model.Phone;
import com.dardanqsot.eval.model.User;
import com.dardanqsot.eval.service.PhoneService;
import com.dardanqsot.eval.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@OpenAPIDefinition(
        info = @Info(
                title = "User Service Microservice",
                version = "0.0.1",
                description = "Módulo para la gestión de usuarios",
                contact = @Contact(
                        name = "Dardan",
                        url = "http://github.com/dardanqsot",
                        email = "quispesotodaniel@gmail.com"
                )
        ),
        servers = { @Server(url = "http:localhost:8080")},
        tags = @Tag(name = "User", description = "Microservicio para la gestión de Usuarios")
)
public class UserController {

    private final ModelMapper mapper;
    private final UserService service;
    private final PhoneService phoneService;

    @GetMapping
    public ResponseEntity<ResponseDto<List<UserResponseDto>>> findAll(){
        List<UserResponseDto> lst = service.findAll().stream().map(this::convertToDto).toList();
        return new ResponseEntity<>(ResponseDto.successResponse(lst), HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ResponseDto<UserResponseDto>> findById(@PathVariable("uuid") UUID uuid){
        User obj = service.findByUuid(uuid);
        List<Phone> lst = phoneService.findByUser(obj);
        obj.setPhones(lst);
        return new ResponseEntity<>(ResponseDto.successResponse(convertToDto(obj)), HttpStatus.OK);
    }

    @Operation(
            description = "Endpoint que registra usuarios",
            tags = {"saveProduct"},
            responses = {
                    @ApiResponse(
                            description = "Response Ok",
                            responseCode = "201"
                    ),
            }
    )
    @PostMapping
    public ResponseEntity<ResponseDto<UserSaveResponseDto>> save(@Valid @RequestBody UserRequestDto dto){
        return new ResponseEntity<>(ResponseDto.successResponse(service.createUser(dto)), HttpStatus.CREATED);
    }

    @PatchMapping("/{uuid}")
    public ResponseEntity<ResponseDto<UserSaveResponseDto>> update(@Valid @PathVariable("uuid") UUID uuid, @RequestBody UserDto dto) {
        return new ResponseEntity<>(ResponseDto.successResponse(service.updateUser(dto, uuid)), HttpStatus.OK);

    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ResponseDto<Void>> delete(@PathVariable("uuid") UUID uuid){
        service.deleteUser(uuid);
        return new ResponseEntity<>(ResponseDto.successResponse(null), HttpStatus.NO_CONTENT);
    }
    private UserResponseDto convertToDto(User obj){
        return mapper.map(obj, UserResponseDto.class);
    }

}
