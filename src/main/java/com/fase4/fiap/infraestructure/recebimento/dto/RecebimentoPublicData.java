package com.fase4.fiap.infraestructure.recebimento.dto;

import com.fase4.fiap.entity.recebimento.model.Recebimento;
import com.fase4.fiap.usecase.recebimento.dto.IRecebimentoPublicData;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.OffsetDateTime;
import java.util.UUID;

public record RecebimentoPublicData(
        UUID id,
        UUID apartamentoId,
        String descricao,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        OffsetDateTime dataEntrega,
        Recebimento.EstadoColeta estadoColeta
) implements IRecebimentoPublicData {

    public RecebimentoPublicData(Recebimento recebimento) {
        this(
                recebimento.getId(),
                recebimento.getApartamentoId(),
                recebimento.getDescricao(),
                recebimento.getDataEntrega(),
                recebimento.getEstadoColeta()
        );
    }

}



