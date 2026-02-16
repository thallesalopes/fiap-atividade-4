package com.fase4.fiap.entity.message.notificacao.model;

import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notificacao {

    private UUID id;
    private UUID apartamentoId;
    private String mensagem;
    private OffsetDateTime dataEnvio;
    private boolean lido = false;
    private UUID moradorId;

    public Notificacao(UUID apartamentoId, String mensagem, OffsetDateTime dataEnvio) {
        this.apartamentoId = apartamentoId;
        this.mensagem = mensagem;
        this.dataEnvio = dataEnvio;
    }

    public void marcarComoLida() {
        this.lido = true;
    }
}