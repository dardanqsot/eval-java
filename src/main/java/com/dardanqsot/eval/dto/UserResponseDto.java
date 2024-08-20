package com.dardanqsot.eval.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    private UUID idUser;

    private String name;

    private String email;

    private LocalDate created;

    private LocalDate modified;

    private LocalDate lastLogin;

    private String token;

    private boolean isActive;

    private List<PhoneResponseDto> phones;
}
