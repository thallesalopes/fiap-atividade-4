package com.fase4.fiap.usecase.morador;

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

import com.fase4.fiap.entity.apartamento.exception.ApartamentoNotFoundException;
import com.fase4.fiap.entity.apartamento.gateway.ApartamentoGateway;
import com.fase4.fiap.entity.apartamento.model.Apartamento;
import com.fase4.fiap.entity.morador.gateway.MoradorGateway;
import com.fase4.fiap.entity.morador.model.Morador;
import com.fase4.fiap.usecase.CasoDeUseTestBase;
import static com.fase4.fiap.usecase.fixtures.DadosDeTeste.apartamentoPadrao;
import static com.fase4.fiap.usecase.fixtures.DadosDeTeste.moradorPadrao;
import static com.fase4.fiap.usecase.fixtures.FabricaDeDtosMock.moradorDtoPadrao;
import com.fase4.fiap.usecase.morador.dto.IMoradorRegistrationData;

@DisplayName("Use Case: Criar Morador")
class CreateMoradorUseCaseTest extends CasoDeUseTestBase {

    private MoradorGateway moradorGateway;
    private ApartamentoGateway apartamentoGateway;
    private CreateMoradorUseCase useCase;

    @Override
    protected void configurarMocks() {
        moradorGateway = criarMock(MoradorGateway.class);
        apartamentoGateway = criarMock(ApartamentoGateway.class);
    }

    @Override
    protected void configurarCasoDeUso() {
        useCase = new CreateMoradorUseCase(moradorGateway, apartamentoGateway);
    }

    @Test
    @DisplayName("Deve criar morador com sucesso quando dados válidos")
    void deveCriarMoradorComSucesso() throws ApartamentoNotFoundException {
        UUID apartamentoId = UUID.randomUUID();
        IMoradorRegistrationData dados = moradorDtoPadrao(apartamentoId);
        Apartamento apartamento = apartamentoPadrao();
        Morador moradorEsperado = moradorPadrao(apartamentoId);

        when(apartamentoGateway.findById(apartamentoId)).thenReturn(Optional.of(apartamento));
        when(moradorGateway.save(any(Morador.class))).thenReturn(moradorEsperado);

        Morador resultado = useCase.execute(dados);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getCpf()).isEqualTo("12345678901");
        verify(apartamentoGateway).findById(apartamentoId);
        verify(moradorGateway).save(any(Morador.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando apartamento não existe")
    void deveLancarExcecaoQuandoApartamentoNaoExiste() {
        UUID apartamentoId = UUID.randomUUID();
        IMoradorRegistrationData dados = moradorDtoPadrao(apartamentoId);

        when(apartamentoGateway.findById(apartamentoId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(dados))
            .isInstanceOf(ApartamentoNotFoundException.class);
        
        verify(apartamentoGateway).findById(apartamentoId);
        verify(moradorGateway, never()).save(any());
    }
}