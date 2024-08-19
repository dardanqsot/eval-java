package com.dardanqsot.eval.dto;

import com.dardanqsot.eval.constraint.PasswordConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    private UUID idUser;

    @NotBlank
    private String name;

    @NotBlank
    @Email
    @Pattern(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.cl$", message = "El correo debe tener el formato aaaaaaa@dominio.cl")
    private String email;

    @NotBlank
    @PasswordConstraint
    private String password;

    @NotNull
    private List<PhoneDto> phones;
}
