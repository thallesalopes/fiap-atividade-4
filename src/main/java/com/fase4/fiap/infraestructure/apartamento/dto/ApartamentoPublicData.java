package com.fase4.fiap.infraestructure.apartamento.dto;

import java.util.UUID;

import com.fase4.fiap.entity.apartamento.model.Apartamento;

public record ApartamentoPublicData(
        UUID id,
        char torre,
        byte andar,
        byte numero
) implements com.fase4.fiap.usecase.dto.ApartamentoResponse {

    public ApartamentoPublicData(Apartamento apartamento) {
        this(
                apartamento.getId(),
                apartamento.getTorre(),
                apartamento.getAndar(),
                apartamento.getNumero()
        );
    }

}