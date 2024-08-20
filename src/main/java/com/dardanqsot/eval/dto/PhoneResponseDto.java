package com.dardanqsot.eval.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneResponseDto {

    private String number;

    private String cityCode;

    private String countryCode;
}
