package com.fase4.fiap.infraestructure.morador.dto;

import java.util.List;
import java.util.UUID;

import com.fase4.fiap.entity.morador.model.Morador;

public record MoradorRegistrationData(
        String cpf,
        String nome,
        List<String> telefone,
        String email,
        List<UUID> apartamentoId
) implements com.fase4.fiap.usecase.dto.MoradorRequest {

    public MoradorRegistrationData(Morador morador) {
        this(
                morador.getCpf(),
                morador.getNome(),
                morador.getTelefone(),
                morador.getEmail(),
                morador.getApartamentoId()
        );
    }
}