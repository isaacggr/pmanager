package com.isaacggr.pmanager.infrastructure.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SaveTaskDataDTO {
    
    @NotNull(message = "O titulo não pode esta vazio")
    private final String title;

    @NotNull(message = "A descrição não pode esta vazio")
    @Size(min = 1, max = 150, message = "Descrção invalida")
    private final String description;

    @NotNull
    @Positive(message = "O numero de dias deve ser positivo")
    private final Integer numberOfDays;

    private final String status;

    private final String projectId;

    private final String memberId;
}
