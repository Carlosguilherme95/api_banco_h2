package com.example.demo.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CustomerRecordDto(@NotBlank String name, @NotNull BigDecimal cpf) {

}
