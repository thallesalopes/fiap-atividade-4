package com.fase4.fiap.entity.message.notificacao.gateway;

import java.util.Optional;
import java.util.UUID;

import com.fase4.fiap.entity.message.notificacao.model.Notificacao;

public interface NotificacaoGateway {
    void publish(Notificacao notificacao);
    Optional<Notificacao> findById(UUID id);
    void save(Notificacao notificacao);
}