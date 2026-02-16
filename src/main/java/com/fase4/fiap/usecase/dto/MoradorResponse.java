package com.fase4.fiap.usecase.dto;

import java.util.List;
import java.util.UUID;

public interface MoradorResponse {
    UUID id();
    String cpf();
    String nome();
    List<String> telefone();
    List<UUID> apartamentoId();
}
