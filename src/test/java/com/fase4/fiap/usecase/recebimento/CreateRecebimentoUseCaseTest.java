package com.fase4.fiap.usecase.recebimento;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fase4.fiap.entity.apartamento.exception.ApartamentoNotFoundException;
import com.fase4.fiap.entity.apartamento.gateway.ApartamentoGateway;
import com.fase4.fiap.entity.apartamento.model.Apartamento;
import com.fase4.fiap.entity.recebimento.gateway.RecebimentoGateway;
import com.fase4.fiap.entity.recebimento.model.Recebimento;
import com.fase4.fiap.usecase.message.publish.PublicarNotificacaoUseCase;
import com.fase4.fiap.usecase.recebimento.dto.IRecebimentoRegistrationData;

public class CreateRecebimentoUseCaseTest {

    private RecebimentoGateway recebimentoGateway;
    private ApartamentoGateway apartamentoGateway;
    private PublicarNotificacaoUseCase publicarNotificacaoUseCase;
    private CreateRecebimentoUseCase useCase;

    private UUID apartamentoId;
    private IRecebimentoRegistrationData dados;

    @BeforeEach
    void setUp() {
        recebimentoGateway = mock(RecebimentoGateway.class);
        apartamentoGateway = mock(ApartamentoGateway.class);
        publicarNotificacaoUseCase = mock(PublicarNotificacaoUseCase.class);
        useCase = new CreateRecebimentoUseCase(recebimentoGateway, apartamentoGateway, publicarNotificacaoUseCase);

        apartamentoId = UUID.randomUUID();
        dados = mock(IRecebimentoRegistrationData.class);

        when(dados.apartamentoId()).thenReturn(apartamentoId);
        when(dados.descricao()).thenReturn("Pacote de livros");
        when(dados.dataEntrega()).thenReturn(OffsetDateTime.now());
        when(dados.estadoColeta()).thenReturn(Recebimento.EstadoColeta.PENDENTE);
    }

    @Test
    @DisplayName("Deve criar recebimento de encomenda e publicar notificação com dados válidos")
    void shouldCreateRecebimentoAndPublishNotificationWhenValidData() throws ApartamentoNotFoundException {
        Apartamento apartamento = mock(Apartamento.class);
        Recebimento savedRecebimento = new Recebimento(
                apartamentoId,
                dados.descricao(),
                dados.dataEntrega(),
                dados.estadoColeta()
        );

        when(apartamentoGateway.findById(apartamentoId)).thenReturn(Optional.of(apartamento));
        when(recebimentoGateway.save(any(Recebimento.class))).thenReturn(savedRecebimento);

        Recebimento resultado = useCase.execute(dados);

        assertNotNull(resultado);
        assertEquals(apartamentoId, resultado.getApartamentoId());
        assertEquals(dados.descricao(), resultado.getDescricao());
        assertEquals(dados.dataEntrega(), resultado.getDataEntrega());
        assertEquals(dados.estadoColeta(), resultado.getEstadoColeta());

        verify(publicarNotificacaoUseCase).publish(argThat(notificacao ->
                notificacao.getApartamentoId().equals(apartamentoId)
        ));

        verify(apartamentoGateway).findById(apartamentoId);
        verify(recebimentoGateway).save(any(Recebimento.class));
    }

    @Test
    @DisplayName("Deve lanÃ§ar ApartamentoNotFoundException quando apartamento nÃ£o existe")
    void shouldThrowApartamentoNotFoundExceptionWhenApartamentoNotFound() {
        when(apartamentoGateway.findById(apartamentoId)).thenReturn(Optional.empty());

        ApartamentoNotFoundException exception = assertThrows(
                ApartamentoNotFoundException.class,
                () -> useCase.execute(dados)
        );

        assertEquals("Apartamento not found: " + apartamentoId, exception.getMessage());

        verify(apartamentoGateway).findById(apartamentoId);
        verify(recebimentoGateway, never()).save(any());
        verify(publicarNotificacaoUseCase, never()).publish(any());
    }

    @Test
    @DisplayName("NÃ£o deve publicar notificaÃ§Ã£o se a descriÃ§Ã£o for nula (lanÃ§a exceÃ§Ã£o)")
    void shouldNotPublishNotificationWhenDescricaoIsNull() {
        when(dados.descricao()).thenReturn(null);
        when(apartamentoGateway.findById(apartamentoId)).thenReturn(Optional.of(mock(Apartamento.class)));

        assertThrows(NullPointerException.class, () -> useCase.execute(dados));

        verify(recebimentoGateway, never()).save(any());
        verify(publicarNotificacaoUseCase, never()).publish(any());
    }

    @Test
    @DisplayName("Não deve salvar com estado PENDENTE se dataEntrega for futura")
    void shouldNotSaveWithPendenteWhenDataEntregaIsFuture() throws ApartamentoNotFoundException {
        OffsetDateTime future = OffsetDateTime.now().plusDays(3);
        when(dados.dataEntrega()).thenReturn(future);
        when(dados.estadoColeta()).thenReturn(Recebimento.EstadoColeta.PENDENTE);

        assertThrows(IllegalArgumentException.class, () -> useCase.execute(dados));

        verify(apartamentoGateway, never()).findById(any());
        verify(recebimentoGateway, never()).save(any());
    }

}


