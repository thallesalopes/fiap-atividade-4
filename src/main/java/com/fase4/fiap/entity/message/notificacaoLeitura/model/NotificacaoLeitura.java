package com.fase4.fiap.entity.message.notificacaoLeitura.model;

import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"notificacaoId", "moradorId"})
public class NotificacaoLeitura {

    private UUID id;
    private UUID notificacaoId;
    private UUID moradorId;
    private OffsetDateTime lidoEm;
    private String ipAddress;
    private String userAgent;

    public NotificacaoLeitura(UUID notificacaoId, UUID moradorId, String ipAddress, String userAgent) {
        this.notificacaoId = notificacaoId;
        this.moradorId = moradorId;
        this.lidoEm = OffsetDateTime.now();
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
    }
}