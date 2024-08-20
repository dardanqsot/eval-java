package com.dardanqsot.eval.dto;

import com.dardanqsot.eval.constraint.PasswordConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String name;

    @Email
    @Pattern(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.cl$", message = "El correo debe tener el formato aaaaaaa@dominio.cl")
    private String email;

    @PasswordConstraint
    private String password;

    private Boolean isActive;
}
