package com.fase4.fiap.usecase.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.fase4.fiap.entity.recebimento.model.Recebimento;

public interface RecebimentoResponse {
    UUID id();
    UUID apartamentoId();
    String descricao();
    OffsetDateTime dataEntrega();
    Recebimento.EstadoColeta estadoColeta();
}
