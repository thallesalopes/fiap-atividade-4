package com.fase4.fiap.usecase.coletaEncomenda;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fase4.fiap.entity.coletaEncomenda.gateway.ColetaEncomendaGateway;
import com.fase4.fiap.entity.coletaEncomenda.model.ColetaEncomenda;
import com.fase4.fiap.entity.recebimento.exception.RecebimentoNotFoundException;
import com.fase4.fiap.entity.recebimento.gateway.RecebimentoGateway;
import com.fase4.fiap.entity.recebimento.model.Recebimento;
import com.fase4.fiap.usecase.coletaEncomenda.dto.IColetaEncomendaRegistrationData;

public class CreateColetaEncomendaUseCaseTest {

    private ColetaEncomendaGateway coletaEncomendaGateway;
    private RecebimentoGateway recebimentoGateway;
    private CreateColetaEncomendaUseCase useCase;

    @BeforeEach
    void setUp() {
        coletaEncomendaGateway = mock(ColetaEncomendaGateway.class);
        recebimentoGateway = mock(RecebimentoGateway.class);
        useCase = new CreateColetaEncomendaUseCase(coletaEncomendaGateway, recebimentoGateway);
    }

    @Test
    @DisplayName("Deve criar coletaEncomenda com dados válidos")
    void shouldCreateColetaEncomendaWithValidData() throws RecebimentoNotFoundException {
        IColetaEncomendaRegistrationData dados = mock(IColetaEncomendaRegistrationData.class);
        Recebimento recebimento = mock(Recebimento.class);
        ColetaEncomenda coletaEncomenda = mock(ColetaEncomenda.class);

        UUID recebimentoId = UUID.randomUUID();

        when(dados.recebimentoId()).thenReturn(recebimentoId);
        when(dados.cpfMoradorColeta()).thenReturn("12345678912");
        when(dados.nomeMoradorColeta()).thenReturn("Teste Nome");
        when(dados.dataColeta()).thenReturn(OffsetDateTime.now());

        when(recebimentoGateway.findById(recebimentoId)).thenReturn(Optional.of(recebimento));
        when(coletaEncomendaGateway.save(any(ColetaEncomenda.class))).thenReturn(coletaEncomenda);

        ColetaEncomenda resultado = useCase.execute(dados);

        assertNotNull(resultado);
        verify(recebimentoGateway, times(1)).findById(recebimentoId);
        verify(coletaEncomendaGateway, times(1)).save(any(ColetaEncomenda.class));
    }

    @Test
    @DisplayName("Deve lanÃ§ar recebimentoNotFoundException quando recebimento nÃ£o existe")
    void shouldThrowrecebimentoNotFoundExceptionWhenrecebimentoDoesNotExist() {
        IColetaEncomendaRegistrationData dados = mock(IColetaEncomendaRegistrationData.class);
        UUID recebimentoId = UUID.randomUUID();

        when(dados.recebimentoId()).thenReturn(recebimentoId);
        when(recebimentoGateway.findById(recebimentoId)).thenReturn(Optional.empty());
        when(dados.dataColeta()).thenReturn(OffsetDateTime.now());

        assertThrows(RecebimentoNotFoundException.class, () -> useCase.execute(dados));

        verify(recebimentoGateway, times(1)).findById(recebimentoId);
        verify(coletaEncomendaGateway, never()).save(any());
    }

}


