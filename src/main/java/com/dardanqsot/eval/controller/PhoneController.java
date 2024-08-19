package com.dardanqsot.eval.controller;

import com.dardanqsot.eval.dto.PhoneDto;
import com.dardanqsot.eval.dto.ResponseDto;
import com.dardanqsot.eval.model.Phone;
import com.dardanqsot.eval.service.PhoneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/v1/phone")
@RequiredArgsConstructor
public class PhoneController {
    private final ModelMapper mapper;
    private final PhoneService service;

    @GetMapping
    public ResponseEntity<ResponseDto<List<PhoneDto>>> findAll(){
        List<PhoneDto> lst = service.findAll().stream().map(this::convertToDto).toList();
        return new ResponseEntity<>(ResponseDto.successResponse(lst), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhoneDto> findById(@PathVariable("id") Integer id){
        Phone obj = service.findById(id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDto<PhoneDto>> save(@Valid @RequestBody PhoneDto dto){
        Phone obj = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdPhone()).toUri();
        return ResponseEntity.created(location).body(ResponseDto.successResponse(dto));
    }
/*
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<PhoneDto>> update(@Valid @PathVariable("id") Integer id, @RequestBody PhoneDto dto) throws Exception {
        Phone obj = service.update(convertToEntity(dto), id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }
*/
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> delete(@PathVariable("id") Integer id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    private PhoneDto convertToDto(Phone obj){
        return mapper.map(obj, PhoneDto.class);
    }

    private Phone convertToEntity(PhoneDto dto){
        return mapper.map(dto, Phone.class);
    }
}
