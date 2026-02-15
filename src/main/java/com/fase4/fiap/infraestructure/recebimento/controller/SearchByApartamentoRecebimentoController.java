package com.fase4.fiap.infraestructure.recebimento.controller;

import com.fase4.fiap.entity.apartamento.exception.ApartamentoNotFoundException;
import com.fase4.fiap.entity.recebimento.model.Recebimento;
import com.fase4.fiap.infraestructure.recebimento.dto.RecebimentoPublicData;
import com.fase4.fiap.usecase.recebimento.SearchByApartamentoRecebimentoUseCase;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class SearchByApartamentoRecebimentoController {

    private final SearchByApartamentoRecebimentoUseCase searchByApartamentoRecebimentoUseCase;

    public SearchByApartamentoRecebimentoController(SearchByApartamentoRecebimentoUseCase searchByApartamentoRecebimentoUseCase) {
        this.searchByApartamentoRecebimentoUseCase = searchByApartamentoRecebimentoUseCase;
    }

    @GetMapping("/api/recebimentos/apartamento/{apartamentoId}")
    @ResponseStatus(HttpStatus.OK)
    public List<RecebimentoPublicData> searchRecebimento(@PathVariable UUID apartamentoId) throws ApartamentoNotFoundException {
        List<Recebimento> recebimento = searchByApartamentoRecebimentoUseCase.execute(apartamentoId);
        return recebimento.stream().map(RecebimentoPublicData::new).toList();
    }

}


