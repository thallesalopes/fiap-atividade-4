package com.fase4.fiap.infraestructure.recebimento.dto;

import com.fase4.fiap.entity.recebimento.model.Recebimento;
import com.fase4.fiap.usecase.recebimento.dto.IRecebimentoRegistrationData;

import java.time.OffsetDateTime;
import java.util.UUID;

public record RecebimentoRegistrationData(
        UUID apartamentoId,
        String descricao,
        OffsetDateTime dataEntrega,
        Recebimento.EstadoColeta estadoColeta
) implements IRecebimentoRegistrationData {

    public RecebimentoRegistrationData(Recebimento recebimento) {
        this(
                recebimento.getApartamentoId(),
                recebimento.getDescricao(),
                recebimento.getDataEntrega(),
                recebimento.getEstadoColeta()
        );
    }
}



