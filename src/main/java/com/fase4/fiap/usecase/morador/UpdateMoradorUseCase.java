package com.fase4.fiap.usecase.morador;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.fase4.fiap.entity.apartamento.exception.ApartamentoNotFoundException;
import com.fase4.fiap.entity.apartamento.gateway.ApartamentoGateway;
import com.fase4.fiap.entity.morador.exception.MoradorNotFoundException;
import com.fase4.fiap.entity.morador.gateway.MoradorGateway;
import com.fase4.fiap.entity.morador.model.Morador;
import com.fase4.fiap.usecase.dto.MoradorUpdateRequest;

public class UpdateMoradorUseCase {

    private final MoradorGateway moradorGateway;
    private final ApartamentoGateway apartamentoGateway;

    public UpdateMoradorUseCase(MoradorGateway moradorGateway, ApartamentoGateway apartamentoGateway) {
        this.moradorGateway = moradorGateway;
        this.apartamentoGateway = apartamentoGateway;
    }

    @Transactional
    public Morador execute(UUID id, MoradorUpdateRequest dados) throws MoradorNotFoundException, ApartamentoNotFoundException {
        Morador morador = buscarMorador(id);
        validarApartamento(dados.apartamentoId().getFirst());
        atualizarDadosMorador(morador, dados);
        return this.moradorGateway.update(morador);
    }

    private Morador buscarMorador(UUID id) throws MoradorNotFoundException {
        return this.moradorGateway.findById(id)
                .orElseThrow(() -> new MoradorNotFoundException("Morador not found: " + id));
    }

    private void validarApartamento(UUID apartamentoId) throws ApartamentoNotFoundException {
        apartamentoGateway.findById(apartamentoId)
                .orElseThrow(() -> new ApartamentoNotFoundException("Apartamento not found: " + apartamentoId));
    }

    private void atualizarDadosMorador(Morador morador, MoradorUpdateRequest dados) {
        if (dados.nome() != null && !dados.nome().isBlank()) {
            morador.setNome(dados.nome());
        }

        if (!dados.telefone().isEmpty()) {
            morador.setTelefone(dados.telefone());
        }

        if (dados.apartamentoId() != null) {
            morador.setApartamentoId(dados.apartamentoId());
        }
    }

}