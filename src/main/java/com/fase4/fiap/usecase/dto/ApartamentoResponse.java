package com.fase4.fiap.usecase.dto;

import java.util.UUID;

public interface ApartamentoResponse {
    UUID id();
    char torre();
    byte andar();
    byte numero();
}
