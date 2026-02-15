package com.fase4.fiap.infraestructure.recebimento.controller;

import com.fase4.fiap.entity.recebimento.model.Recebimento;
import com.fase4.fiap.infraestructure.recebimento.dto.RecebimentoPublicData;
import com.fase4.fiap.usecase.recebimento.SearchRecebimentoUseCase;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchRecebimentoController {

    private final SearchRecebimentoUseCase searchRecebimentoUseCase;

    public SearchRecebimentoController(SearchRecebimentoUseCase searchRecebimentoUseCase) {
        this.searchRecebimentoUseCase = searchRecebimentoUseCase;
    }

    @GetMapping("/api/recebimentos")
    @ResponseStatus(HttpStatus.OK)
    public List<RecebimentoPublicData> searchRecebimento() {
        List<Recebimento> recebimento = this.searchRecebimentoUseCase.execute();
        return recebimento.stream().map(RecebimentoPublicData::new).toList();
    }

}



