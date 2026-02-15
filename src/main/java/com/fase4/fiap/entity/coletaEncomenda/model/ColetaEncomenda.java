package com.fase4.fiap.entity.coletaEncomenda.model;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.fase4.fiap.entity.auxiliary.AbstractEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ColetaEncomenda extends AbstractEntity implements Serializable {

    @NonNull
    private UUID recebimentoId;

    @NonNull
    private String cpfMoradorColeta;

    // Signature of the resident collecting the package
    @NonNull
    private String nomeMoradorColeta;

    @NonNull
    private OffsetDateTime dataColeta;

    public ColetaEncomenda(@NonNull UUID recebimentoId, @NonNull String cpfMoradorColeta, @NonNull String nomeMoradorColeta, @NonNull OffsetDateTime dataColeta) {
        this.recebimentoId = recebimentoId;
        this.cpfMoradorColeta = cpfMoradorColeta;
        this.nomeMoradorColeta = nomeMoradorColeta;
        this.dataColeta = dataColeta;
    }

    public static void validacaoDataColeta(OffsetDateTime dataColeta) {
        if (dataColeta.isAfter(OffsetDateTime.now())) {
            throw new IllegalArgumentException("Data de coleta nÃ£o pode ser no futuro.");
        }
    }

}
