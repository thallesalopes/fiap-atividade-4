package com.fase4.fiap.usecase.coletaEncomenda;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fase4.fiap.entity.coletaEncomenda.gateway.ColetaEncomendaGateway;
import com.fase4.fiap.entity.coletaEncomenda.model.ColetaEncomenda;
import com.fase4.fiap.entity.recebimento.exception.RecebimentoNotFoundException;
import com.fase4.fiap.entity.recebimento.gateway.RecebimentoGateway;
import com.fase4.fiap.entity.recebimento.model.Recebimento;
import com.fase4.fiap.usecase.UseCaseTestBase;
import com.fase4.fiap.usecase.coletaEncomenda.dto.IColetaEncomendaRegistrationData;
import static com.fase4.fiap.usecase.fixtures.DtoMockFactory.coletaDtoPadrao;
import static com.fase4.fiap.usecase.fixtures.TestFixtures.coletaPadrao;
import static com.fase4.fiap.usecase.fixtures.TestFixtures.novoId;
import static com.fase4.fiap.usecase.fixtures.TestFixtures.recebimentoPadrao;

@DisplayName("Testes do CreateColetaEncomendaUseCase")
class CreateColetaEncomendaUseCaseTest extends UseCaseTestBase {

    private CreateColetaEncomendaUseCase useCase;
    private ColetaEncomendaGateway coletaEncomendaGateway;
    private RecebimentoGateway recebimentoGateway;

    @Override
    protected void setupMocks() {
        coletaEncomendaGateway = createMock(ColetaEncomendaGateway.class);
        recebimentoGateway = createMock(RecebimentoGateway.class);
    }

    @Override
    protected void setupUseCase() {
        useCase = new CreateColetaEncomendaUseCase(coletaEncomendaGateway, recebimentoGateway);
    }

    @Test
    @DisplayName("Deve criar coleta de encomenda com dados válidos")
    void deveCriarColetaEncomendaComDadosValidos() throws RecebimentoNotFoundException {
        // Arrange
        UUID recebimentoId = novoId();
        UUID apartamentoId = novoId();
        Recebimento recebimento = recebimentoPadrao(apartamentoId);
        IColetaEncomendaRegistrationData dadosColeta = coletaDtoPadrao(recebimentoId);
        ColetaEncomenda coletaSalva = coletaPadrao(recebimentoId);

        when(recebimentoGateway.findById(recebimentoId)).thenReturn(Optional.of(recebimento));
        when(coletaEncomendaGateway.save(any(ColetaEncomenda.class))).thenReturn(coletaSalva);

        // Act
        ColetaEncomenda resultado = useCase.execute(dadosColeta);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getRecebimentoId()).isEqualTo(recebimentoId);
        verify(recebimentoGateway).findById(recebimentoId);
        verify(coletaEncomendaGateway).save(any(ColetaEncomenda.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando recebimento não existe")
    void deveLancarExcecaoQuandoRecebimentoNaoExiste() {
        // Arrange
        UUID recebimentoId = novoId();
        IColetaEncomendaRegistrationData dadosColeta = coletaDtoPadrao(recebimentoId);

        when(recebimentoGateway.findById(recebimentoId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> useCase.execute(dadosColeta))
            .isInstanceOf(RecebimentoNotFoundException.class);

        verify(recebimentoGateway).findById(recebimentoId);
        verify(coletaEncomendaGateway, never()).save(any());
    }
}


