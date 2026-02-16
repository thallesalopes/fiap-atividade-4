package com.fase4.fiap.usecase.apartamento;

import org.springframework.transaction.annotation.Transactional;

import com.fase4.fiap.entity.apartamento.gateway.ApartamentoGateway;
import com.fase4.fiap.entity.apartamento.model.Apartamento;
import com.fase4.fiap.usecase.apartamento.dto.ApartamentoRegistrationData;

public class CreateApartamentoUseCase {

    private final ApartamentoGateway apartamentoGateway;

    public CreateApartamentoUseCase(ApartamentoGateway apartamentoGateway) {
        this.apartamentoGateway = apartamentoGateway;
    }

    @Transactional
    public Apartamento execute(ApartamentoRegistrationData dados) {

        Apartamento apartamento = new Apartamento(dados.torre(), dados.andar(), dados.numero());

        return this.apartamentoGateway.save(apartamento);
    }

}
