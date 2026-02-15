package com.fase4.fiap.usecase.recebimento.dto;

import com.fase4.fiap.entity.recebimento.model.Recebimento;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface IRecebimentoPublicData {

    UUID id();

    UUID apartamentoId();

    String descricao();

    OffsetDateTime dataEntrega();

    Recebimento.EstadoColeta estadoColeta();

}


