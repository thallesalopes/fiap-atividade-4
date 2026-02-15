package com.fase4.fiap.entity.recebimento.gateway;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fase4.fiap.entity.recebimento.model.Recebimento;

public interface RecebimentoGateway {

    Recebimento save(Recebimento recebimento);
    Optional<Recebimento> findById(UUID id);
    List<Recebimento> findAllByApartamentoId(UUID ApartamentoId);
    List<Recebimento> findAll();
    Recebimento update(Recebimento recebimento);

}


