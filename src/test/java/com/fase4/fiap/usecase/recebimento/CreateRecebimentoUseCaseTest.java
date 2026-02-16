package com.fase4.fiap.usecase.recebimento;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fase4.fiap.entity.apartamento.exception.ApartamentoNotFoundException;
import com.fase4.fiap.entity.apartamento.gateway.ApartamentoGateway;
import com.fase4.fiap.entity.apartamento.model.Apartamento;
import com.fase4.fiap.entity.recebimento.gateway.RecebimentoGateway;
import com.fase4.fiap.entity.recebimento.model.Recebimento;
import com.fase4.fiap.usecase.CasoDeUseTestBase;
import static com.fase4.fiap.usecase.fixtures.DadosDeTeste.apartamentoPadrao;
import static com.fase4.fiap.usecase.fixtures.DadosDeTeste.recebimentoPadrao;
import static com.fase4.fiap.usecase.fixtures.FabricaDeDtosMock.recebimentoDto;
import static com.fase4.fiap.usecase.fixtures.FabricaDeDtosMock.recebimentoDtoPadrao;
import com.fase4.fiap.usecase.message.publish.PublicarNotificacaoUseCase;
import com.fase4.fiap.usecase.recebimento.dto.IRecebimentoRegistrationData;

@DisplayName("Testes do CreateRecebimentoUseCase")
class CreateRecebimentoUseCaseTest extends CasoDeUseTestBase {

    private CreateRecebimentoUseCase useCase;
    private RecebimentoGateway recebimentoGateway;
    private ApartamentoGateway apartamentoGateway;
    private PublicarNotificacaoUseCase publicarNotificacaoUseCase;

    @Override
    protected void configurarMocks() {
        recebimentoGateway = criarMock(RecebimentoGateway.class);
        apartamentoGateway = criarMock(ApartamentoGateway.class);
        publicarNotificacaoUseCase = criarMock(PublicarNotificacaoUseCase.class);
    }

    @Override
    protected void configurarCasoDeUso() {
        useCase = new CreateRecebimentoUseCase(
            recebimentoGateway,
            apartamentoGateway,
            publicarNotificacaoUseCase
        );
    }

    @Test
    @DisplayName("Deve criar recebimento de encomenda e publicar notificação com dados válidos")
    void deveCriarRecebimentoEPublicarNotificacaoComSucesso() throws ApartamentoNotFoundException {
        UUID apartamentoId = UUID.randomUUID();
        Apartamento apartamento = apartamentoPadrao();
        IRecebimentoRegistrationData dadosRecebimento = recebimentoDtoPadrao(apartamentoId);
        Recebimento recebimentoSalvo = recebimentoPadrao(apartamentoId);

        when(apartamentoGateway.findById(apartamentoId)).thenReturn(Optional.of(apartamento));
        when(recebimentoGateway.save(any(Recebimento.class))).thenReturn(recebimentoSalvo);

        Recebimento resultado = useCase.execute(dadosRecebimento);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getApartamentoId()).isEqualTo(apartamentoId);
        assertThat(resultado.getDescricao()).isEqualTo("Pacote de livros");
        assertThat(resultado.getEstadoColeta()).isEqualTo(Recebimento.EstadoColeta.PENDENTE);

        verify(apartamentoGateway).findById(apartamentoId);
        verify(recebimentoGateway).save(any(Recebimento.class));
        verify(publicarNotificacaoUseCase).publish(argThat(notificacao ->
            notificacao.getApartamentoId().equals(apartamentoId)
        ));
    }

    @Test
    @DisplayName("Deve lançar exceção quando apartamento não existe")
    void deveLancarExcecaoQuandoApartamentoNaoExiste() {
        UUID apartamentoId = UUID.randomUUID();
        IRecebimentoRegistrationData dadosRecebimento = recebimentoDtoPadrao(apartamentoId);

        when(apartamentoGateway.findById(apartamentoId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(dadosRecebimento))
            .isInstanceOf(ApartamentoNotFoundException.class)
            .hasMessage("Apartamento not found: " + apartamentoId);

        verify(apartamentoGateway).findById(apartamentoId);
        verify(recebimentoGateway, never()).save(any());
        verify(publicarNotificacaoUseCase, never()).publish(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando descrição for nula")
    void deveLancarExcecaoQuandoDescricaoForNula() {
        UUID apartamentoId = UUID.randomUUID();
        IRecebimentoRegistrationData dadosRecebimento = recebimentoDto(
            apartamentoId, null, OffsetDateTime.now().minusHours(1)
        );
        
        when(apartamentoGateway.findById(apartamentoId)).thenReturn(Optional.of(apartamentoPadrao()));

        assertThatThrownBy(() -> useCase.execute(dadosRecebimento))
            .isInstanceOf(NullPointerException.class);

        verify(recebimentoGateway, never()).save(any());
        verify(publicarNotificacaoUseCase, never()).publish(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando data de entrega for futura")
    void deveLancarExcecaoQuandoDataEntregaForFutura() {
        UUID apartamentoId = UUID.randomUUID();
        OffsetDateTime dataFutura = OffsetDateTime.now().plusDays(3);
        IRecebimentoRegistrationData dadosRecebimento = recebimentoDto(
            apartamentoId, "Pacote", dataFutura
        );

        assertThatThrownBy(() -> useCase.execute(dadosRecebimento))
            .isInstanceOf(IllegalArgumentException.class);

        verify(apartamentoGateway, never()).findById(any());
        verify(recebimentoGateway, never()).save(any());
    }
}


