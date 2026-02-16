package com.fase4.fiap.usecase.dto;

import java.util.List;
import java.util.UUID;

public interface MoradorUpdateRequest {
    String nome();
    List<String> telefone();
    List<UUID> apartamentoId();
}
