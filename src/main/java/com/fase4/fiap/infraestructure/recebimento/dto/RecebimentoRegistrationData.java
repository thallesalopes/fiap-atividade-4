package com.fase4.fiap.infraestructure.recebimento.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.fase4.fiap.entity.recebimento.model.Recebimento;

public record RecebimentoRegistrationData(
        UUID apartamentoId,
        String descricao,
        OffsetDateTime dataEntrega,
        Recebimento.EstadoColeta estadoColeta
) implements com.fase4.fiap.usecase.dto.RecebimentoRequest {

    public RecebimentoRegistrationData(Recebimento recebimento) {
        this(
                recebimento.getApartamentoId(),
                recebimento.getDescricao(),
                recebimento.getDataEntrega(),
                recebimento.getEstadoColeta()
        );
    }
}



