package com.fase4.fiap.infraestructure.recebimento.controller;

import com.fase4.fiap.entity.recebimento.exception.RecebimentoNotFoundException;
import com.fase4.fiap.entity.recebimento.model.Recebimento;
import com.fase4.fiap.infraestructure.recebimento.dto.RecebimentoPublicData;
import com.fase4.fiap.usecase.recebimento.GetRecebimentoUseCase;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class GetRecebimentoController {

    private final GetRecebimentoUseCase getRecebimentoUseCase;

    public GetRecebimentoController(GetRecebimentoUseCase getRecebimentoUseCase) {
        this.getRecebimentoUseCase = getRecebimentoUseCase;
    }

    @GetMapping("/api/recebimentos/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RecebimentoPublicData getRecebimento(@PathVariable UUID id) throws RecebimentoNotFoundException {
        Recebimento recebimento = getRecebimentoUseCase.execute(id);
        return new RecebimentoPublicData(recebimento);
    }

}



