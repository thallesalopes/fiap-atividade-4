package com.fase4.fiap.usecase.recebimento;

import com.fase4.fiap.entity.apartamento.exception.ApartamentoNotFoundException;
import com.fase4.fiap.entity.apartamento.gateway.ApartamentoGateway;
import com.fase4.fiap.entity.recebimento.gateway.RecebimentoGateway;
import com.fase4.fiap.entity.recebimento.model.Recebimento;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public class SearchByApartamentoRecebimentoUseCase {

    private final RecebimentoGateway recebimentoGateway;
    private final ApartamentoGateway apartamentoGateway;

    public SearchByApartamentoRecebimentoUseCase(RecebimentoGateway recebimentoGateway, ApartamentoGateway apartamentoGateway) {
        this.recebimentoGateway = recebimentoGateway;
        this.apartamentoGateway = apartamentoGateway;
    }

    @Transactional(readOnly = true)
    public List<Recebimento> execute(UUID apartamentoId) throws ApartamentoNotFoundException {
        apartamentoGateway.findById(apartamentoId)
                .orElseThrow(() -> new ApartamentoNotFoundException("Apartamento not found: " + apartamentoId));

        return this.recebimentoGateway.findAllByApartamentoId(apartamentoId);
    }

}


