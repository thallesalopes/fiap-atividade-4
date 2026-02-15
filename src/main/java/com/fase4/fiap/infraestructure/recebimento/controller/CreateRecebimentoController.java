package com.fase4.fiap.infraestructure.recebimento.controller;

import com.fase4.fiap.entity.apartamento.exception.ApartamentoNotFoundException;
import com.fase4.fiap.infraestructure.recebimento.dto.RecebimentoPublicData;
import com.fase4.fiap.infraestructure.recebimento.dto.RecebimentoRegistrationData;
import com.fase4.fiap.usecase.recebimento.CreateRecebimentoUseCase;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateRecebimentoController {

    private final CreateRecebimentoUseCase createRecebimentoUseCase;

    public CreateRecebimentoController(CreateRecebimentoUseCase createRecebimentoUseCase) {
        this.createRecebimentoUseCase = createRecebimentoUseCase;
    }

    @PostMapping("/api/recebimentos")
    @ResponseStatus(HttpStatus.CREATED)
    public RecebimentoPublicData createRecebimento(@RequestBody RecebimentoRegistrationData dados) throws ApartamentoNotFoundException {
        return new RecebimentoPublicData(createRecebimentoUseCase.execute(dados));
    }

}


