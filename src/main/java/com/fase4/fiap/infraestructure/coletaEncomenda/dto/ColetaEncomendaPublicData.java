package com.fase4.fiap.infraestructure.coletaEncomenda.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.fase4.fiap.entity.coletaEncomenda.model.ColetaEncomenda;
import com.fase4.fiap.usecase.coletaEncomenda.dto.IColetaEncomendaPublicData;

public record ColetaEncomendaPublicData(
        UUID id,
        UUID recebimentoId,
        String cpfMoradorColeta,
        String nomeMoradorColeta,
        OffsetDateTime dataColeta
) implements IColetaEncomendaPublicData {

    public ColetaEncomendaPublicData(ColetaEncomenda coletaEncomenda) {
        this(
                coletaEncomenda.getId(),
                coletaEncomenda.getRecebimentoId(),
                coletaEncomenda.getCpfMoradorColeta(),
                coletaEncomenda.getNomeMoradorColeta(),
                coletaEncomenda.getDataColeta()
        );
    }

}
