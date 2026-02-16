package com.fase4.fiap.infraestructure.apartamento.dto;

import com.fase4.fiap.entity.apartamento.model.Apartamento;

public record ApartamentoRegistrationData(
        char torre,
        byte andar,
        byte numero
) implements com.fase4.fiap.usecase.apartamento.dto.ApartamentoRegistrationData {

    public ApartamentoRegistrationData(Apartamento apartamento) {
        this(
                apartamento.getTorre(),
                apartamento.getAndar(),
                apartamento.getNumero()
        );
    }

}