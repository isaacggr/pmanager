package com.isaacggr.pmanager.infrastructure.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SaveMemberDataDTO {
    

    @NotNull(message = "O nome não pode esta vazio")
    @Size(min = 1, max = 50, message = "Nome inválido")
    private final String name;

    @NotNull(message = "O E-mail não pode esta vazio")
    @Email(message = "Email inválido")
    private final String email;
}
