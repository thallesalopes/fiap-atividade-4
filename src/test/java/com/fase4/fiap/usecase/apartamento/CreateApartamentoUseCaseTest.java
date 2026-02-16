package com.fase4.fiap.usecase.apartamento;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fase4.fiap.entity.apartamento.gateway.ApartamentoGateway;
import com.fase4.fiap.entity.apartamento.model.Apartamento;
import com.fase4.fiap.usecase.CasoDeUseTestBase;
import com.fase4.fiap.usecase.dto.ApartamentoRequest;
import static com.fase4.fiap.usecase.fixtures.DadosDeTeste.apartamentoPadrao;
import static com.fase4.fiap.usecase.fixtures.FabricaDeDtosMock.apartamentoDtoPadrao;

@DisplayName("Testes do CreateApartamentoUseCase")
class CreateApartamentoUseCaseTest extends CasoDeUseTestBase {

    private CreateApartamentoUseCase useCase;
    private ApartamentoGateway apartamentoGateway;

    @Override
    protected void configurarMocks() {
        apartamentoGateway = criarMock(ApartamentoGateway.class);
    }

    @Override
    protected void configurarCasoDeUso() {
        useCase = new CreateApartamentoUseCase(apartamentoGateway);
    }

    @Test
    @DisplayName("Deve criar apartamento com dados válidos")
    void deveCriarApartamentoComDadosValidos() {
        ApartamentoRequest dadosApartamento = apartamentoDtoPadrao();
        Apartamento apartamentoSalvo = apartamentoPadrao();
        when(apartamentoGateway.save(any(Apartamento.class))).thenReturn(apartamentoSalvo);

        Apartamento resultado = useCase.execute(dadosApartamento);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getTorre()).isEqualTo('A');
        assertThat(resultado.getAndar()).isEqualTo((byte) 10);
        assertThat(resultado.getNumero()).isEqualTo((byte) 101);
        verify(apartamentoGateway).save(any(Apartamento.class));
    }
}
