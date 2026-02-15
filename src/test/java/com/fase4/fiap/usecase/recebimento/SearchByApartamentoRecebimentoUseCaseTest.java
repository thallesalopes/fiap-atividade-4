package com.fase4.fiap.usecase.recebimento;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fase4.fiap.entity.apartamento.exception.ApartamentoNotFoundException;
import com.fase4.fiap.entity.apartamento.gateway.ApartamentoGateway;
import com.fase4.fiap.entity.apartamento.model.Apartamento;
import com.fase4.fiap.entity.recebimento.gateway.RecebimentoGateway;
import com.fase4.fiap.entity.recebimento.model.Recebimento;

public class SearchByApartamentoRecebimentoUseCaseTest {

    private RecebimentoGateway recebimentoGateway;
    private ApartamentoGateway apartamentoGateway;
    private SearchByApartamentoRecebimentoUseCase useCase;

    @BeforeEach
    void setUp() {
        recebimentoGateway = mock(RecebimentoGateway.class);
        apartamentoGateway = mock(ApartamentoGateway.class);
        useCase = new SearchByApartamentoRecebimentoUseCase(recebimentoGateway, apartamentoGateway);
    }

    @Test
    @DisplayName("Deve retornar lista de recebimentos quando existirem registros")
    void shouldReturnListOfRecebimentosWhenRecordsExist() throws ApartamentoNotFoundException {
        Recebimento recebimento = mock(Recebimento.class);
        Apartamento apartamento = mock(Apartamento.class);
        when(apartamentoGateway.findById(apartamento.getId())).thenReturn(Optional.of(apartamento));
        when(recebimentoGateway.findAllByApartamentoId(apartamento.getId())).thenReturn(List.of(recebimento));

        List<Recebimento> resultado = useCase.execute(apartamento.getId());

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(recebimento, resultado.getFirst());
        verify(recebimentoGateway, times(1)).findAllByApartamentoId(apartamento.getId());
    }

    @Test
    @DisplayName("Deve lançar ApartamentoNotFoundException quando apartamento não existe")
    void shouldReturnEmptyListWhenNoRecebimentosExist() {
        UUID apartamentoId = UUID.randomUUID();
        when(apartamentoGateway.findById(apartamentoId)).thenReturn(Optional.empty());

        assertThrows(ApartamentoNotFoundException.class, () -> useCase.execute(apartamentoId));
        verify(apartamentoGateway, times(1)).findById(apartamentoId);
        verify(recebimentoGateway, times(0)).findAllByApartamentoId(apartamentoId);
    }

}



