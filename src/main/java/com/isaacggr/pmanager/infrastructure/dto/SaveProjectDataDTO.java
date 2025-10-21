package com.isaacggr.pmanager.infrastructure.dto;

import java.time.LocalDate;
import java.util.Set;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SaveProjectDataDTO {

    @NotNull(message = "Nome não pode esta vazio")
    @Size(min = 1, max = 50, message = "nome invalido")
    private final String name;

    @NotNull(message = "Descrição não pode esta vazia")
    @Size(min = 1, max = 150, message = "Descrição invalida")
    private final String description;

    @NotNull(message = "Data inicial não pode esta vazia")
    private final LocalDate initialDate;

    @NotNull(message = "Data final não pode esta vazia")
    private final LocalDate finalDate;


    private final String status;

    private final Set<String> memberIds;

    @AssertTrue(message = "A data final não pode ser anterior à data inicial")
    private boolean isInitialDateBeforeFinalDate() {
        return initialDate.isBefore(finalDate);
    }
}
