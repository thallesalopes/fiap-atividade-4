package com.fase4.fiap.infraestructure.coletaEncomenda.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.fase4.fiap.entity.coletaEncomenda.model.ColetaEncomenda;

public record ColetaEncomendaRegistrationData(
        UUID recebimentoId,
        String cpfMoradorColeta,
        String nomeMoradorColeta,
        OffsetDateTime dataColeta
) implements com.fase4.fiap.usecase.coletaEncomenda.dto.ColetaEncomendaRegistrationData {

    public ColetaEncomendaRegistrationData(ColetaEncomenda coletaEncomenda) {
        this(
                coletaEncomenda.getRecebimentoId(),
                coletaEncomenda.getCpfMoradorColeta(),
                coletaEncomenda.getNomeMoradorColeta(),
                coletaEncomenda.getDataColeta()
        );
    }

}
