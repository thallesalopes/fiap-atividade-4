package com.fase4.fiap.usecase.apartamento;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fase4.fiap.entity.apartamento.gateway.ApartamentoGateway;
import com.fase4.fiap.entity.apartamento.model.Apartamento;
import com.fase4.fiap.usecase.UseCaseTestBase;
import com.fase4.fiap.usecase.apartamento.dto.IApartamentoRegistrationData;
import static com.fase4.fiap.usecase.fixtures.DtoMockFactory.apartamentoDtoPadrao;
import static com.fase4.fiap.usecase.fixtures.TestFixtures.apartamentoPadrao;

@DisplayName("Testes do CreateApartamentoUseCase")
class CreateApartamentoUseCaseTest extends UseCaseTestBase {

    private CreateApartamentoUseCase useCase;
    private ApartamentoGateway apartamentoGateway;

    @Override
    protected void setupMocks() {
        apartamentoGateway = createMock(ApartamentoGateway.class);
    }

    @Override
    protected void setupUseCase() {
        useCase = new CreateApartamentoUseCase(apartamentoGateway);
    }

    @Test
    @DisplayName("Deve criar apartamento com dados válidos")
    void deveCriarApartamentoComDadosValidos() {
        // Arrange
        IApartamentoRegistrationData dadosApartamento = apartamentoDtoPadrao();
        Apartamento apartamentoSalvo = apartamentoPadrao();
        when(apartamentoGateway.save(any(Apartamento.class))).thenReturn(apartamentoSalvo);

        // Act
        Apartamento resultado = useCase.execute(dadosApartamento);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getTorre()).isEqualTo('A');
        assertThat(resultado.getAndar()).isEqualTo((byte) 10);
        assertThat(resultado.getNumero()).isEqualTo((byte) 101);
        verify(apartamentoGateway).save(any(Apartamento.class));
    }
}
