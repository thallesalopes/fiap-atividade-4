package com.fase4.fiap.entity.coletaEncomenda.model;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.fase4.fiap.entity.auxiliary.AbstractEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ColetaEncomenda extends AbstractEntity {

    @NonNull private UUID recebimentoId;
    @NonNull private String cpfMoradorColeta;
    @NonNull private String nomeMoradorColeta;
    @NonNull private OffsetDateTime dataColeta;

    public static void validacaoDataColeta(OffsetDateTime dataColeta) {
        if (dataColeta.isAfter(OffsetDateTime.now())) {
            throw new IllegalArgumentException("Data de coleta não pode ser no futuro.");
        }
    }
}
