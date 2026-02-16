package com.fase4.fiap.usecase.dto;

import java.util.List;
import java.util.UUID;

public interface MoradorRequest {
    String cpf();
    String nome();
    List<String> telefone();
    String email();
    List<UUID> apartamentoId();
}
