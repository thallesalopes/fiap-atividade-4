package com.fase4.fiap.usecase.recebimento;

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

import com.fase4.fiap.entity.recebimento.exception.RecebimentoNotFoundException;
import com.fase4.fiap.entity.recebimento.gateway.RecebimentoGateway;
import com.fase4.fiap.entity.recebimento.model.Recebimento;

public class GetRecebimentoUseCaseTest {

    private RecebimentoGateway recebimentoGateway;
    private GetRecebimentoUseCase useCase;

    @BeforeEach
    void setUp() {
        recebimentoGateway = mock(RecebimentoGateway.class);
        useCase = new GetRecebimentoUseCase(recebimentoGateway);
    }

    @Test
    @DisplayName("Deve retornar recebimento quando encontrado pelo id")
    void shouldReturnRecebimentoWhenFoundById() throws RecebimentoNotFoundException {
        UUID id = UUID.randomUUID();
        Recebimento recebimento = mock(Recebimento.class);

        when(recebimentoGateway.findById(id)).thenReturn(Optional.of(recebimento));

        Recebimento resultado = useCase.execute(id);

        assertNotNull(resultado);
        assertEquals(recebimento, resultado);
        verify(recebimentoGateway, times(1)).findById(id);
    }

    @Test
    @DisplayName("Deve lançar RecebimentoNotFoundException quando recebimento não existe")
    void shouldThrowRecebimentoNotFoundExceptionWhenRecebimentoDoesNotExist() {
        UUID id = UUID.randomUUID();
        when(recebimentoGateway.findById(id)).thenReturn(Optional.empty());

        assertThrows(RecebimentoNotFoundException.class, () -> useCase.execute(id));
        verify(recebimentoGateway, times(1)).findById(id);
    }

}



