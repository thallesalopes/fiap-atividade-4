package com.fase4.fiap.usecase.recebimento;

import com.fase4.fiap.entity.recebimento.exception.RecebimentoNotFoundException;
import com.fase4.fiap.entity.recebimento.gateway.RecebimentoGateway;
import com.fase4.fiap.entity.recebimento.model.Recebimento;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public class GetRecebimentoUseCase {

    private final RecebimentoGateway recebimentoGateway;

    public GetRecebimentoUseCase(RecebimentoGateway recebimentoGateway) {
        this.recebimentoGateway = recebimentoGateway;
    }

    @Transactional(readOnly = true)
    public Recebimento execute(UUID id) throws RecebimentoNotFoundException {
        return this.recebimentoGateway.findById(id).
                orElseThrow(() -> new RecebimentoNotFoundException("Encomenda not found: " + id));
    }

}



