package com.dardanqsot.eval.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneRequestDto {

    private Integer idPhone;

    private UUID idUser;

    private String number;

    private String cityCode;

    private String countryCode;
}
