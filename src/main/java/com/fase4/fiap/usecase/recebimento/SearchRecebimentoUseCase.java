package com.fase4.fiap.usecase.recebimento;

import com.fase4.fiap.entity.recebimento.gateway.RecebimentoGateway;
import com.fase4.fiap.entity.recebimento.model.Recebimento;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class SearchRecebimentoUseCase {

    private final RecebimentoGateway recebimentoGateway;

    public SearchRecebimentoUseCase(RecebimentoGateway recebimentoGateway) {
        this.recebimentoGateway = recebimentoGateway;
    }

    @Transactional(readOnly = true)
    public List<Recebimento> execute() {
        return this.recebimentoGateway.findAll();
    }

}


