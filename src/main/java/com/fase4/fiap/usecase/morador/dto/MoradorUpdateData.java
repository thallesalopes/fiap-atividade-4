package com.fase4.fiap.usecase.morador.dto;

import java.util.List;
import java.util.UUID;

public interface MoradorUpdateData {

    String nome();

    List<String> telefone();

    List<UUID> apartamentoId();

}
