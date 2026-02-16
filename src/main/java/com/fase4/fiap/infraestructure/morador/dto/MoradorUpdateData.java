package com.fase4.fiap.infraestructure.morador.dto;

import java.util.List;
import java.util.UUID;

import com.fase4.fiap.entity.morador.model.Morador;

public record MoradorUpdateData(
        String nome,
        List<String> telefone,
        List<UUID> apartamentoId
) implements com.fase4.fiap.usecase.morador.dto.MoradorUpdateData {

    public MoradorUpdateData(Morador morador) {
        this(
                morador.getNome(),
                morador.getTelefone(),
                morador.getApartamentoId()
        );
    }
}
