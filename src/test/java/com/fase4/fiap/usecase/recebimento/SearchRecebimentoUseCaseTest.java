package com.fase4.fiap.usecase.recebimento;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fase4.fiap.entity.recebimento.gateway.RecebimentoGateway;
import com.fase4.fiap.entity.recebimento.model.Recebimento;

public class SearchRecebimentoUseCaseTest {

    private RecebimentoGateway recebimentoGateway;
    private SearchRecebimentoUseCase useCase;

    @BeforeEach
    void setUp() {
        recebimentoGateway = mock(RecebimentoGateway.class);
        useCase = new SearchRecebimentoUseCase(recebimentoGateway);
    }

    @Test
    @DisplayName("Deve retornar lista de recebimentos quando existirem registros")
    void shouldReturnListOfRecebimentosWhenRecordsExist() {
        Recebimento recebimento = mock(Recebimento.class);
        when(recebimentoGateway.findAll()).thenReturn(List.of(recebimento));

        List<Recebimento> resultado = useCase.execute();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(recebimento, resultado.getFirst());
        verify(recebimentoGateway, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não existirem registros de recebimentos")
    void shouldReturnEmptyListWhenNoRecebimentosExist() {
        when(recebimentoGateway.findAll()).thenReturn(Collections.emptyList());

        List<Recebimento> resultado = useCase.execute();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(recebimentoGateway, times(1)).findAll();
    }

}


