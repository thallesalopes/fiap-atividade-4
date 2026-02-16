package com.fase4.fiap.entity.recebimento.model;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.fase4.fiap.entity.auxiliary.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Recebimento extends AbstractEntity {

    @NonNull private UUID apartamentoId;
    @NonNull private String descricao;
    @NonNull private OffsetDateTime dataEntrega;
    
    @Enumerated(EnumType.STRING)
    @NonNull private EstadoColeta estadoColeta;

    public enum EstadoColeta {
        PENDENTE, COLETADA;

        @JsonValue
        public String getValue() {
            return this.name();
        }
    }

    public static void validacaoDataEntrega(OffsetDateTime dataEntrega) {
        if (dataEntrega.isAfter(OffsetDateTime.now())) {
            throw new IllegalArgumentException("Data de entrega não pode ser no futuro.");
        }
    }

    public void atualizarEstadoColeta() {
        this.estadoColeta = EstadoColeta.COLETADA;
    }
}


