package com.fase4.fiap.usecase.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface ColetaEncomendaRequest {
    UUID recebimentoId();
    String cpfMoradorColeta();
    String nomeMoradorColeta();
    OffsetDateTime dataColeta();
}
