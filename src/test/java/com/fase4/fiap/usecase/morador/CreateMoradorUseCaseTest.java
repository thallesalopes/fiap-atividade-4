package com.fase4.fiap.usecase.morador;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fase4.fiap.entity.apartamento.exception.ApartamentoNotFoundException;
import com.fase4.fiap.entity.apartamento.gateway.ApartamentoGateway;
import com.fase4.fiap.entity.apartamento.model.Apartamento;
import com.fase4.fiap.entity.morador.gateway.MoradorGateway;
import com.fase4.fiap.entity.morador.model.Morador;
import com.fase4.fiap.usecase.UseCaseTestBase;
import com.fase4.fiap.usecase.morador.dto.IMoradorRegistrationData;

@DisplayName("Use Case: Criar Morador")
class CreateMoradorUseCaseTest extends UseCaseTestBase {

    private MoradorGateway moradorGateway;
    private ApartamentoGateway apartamentoGateway;
    private CreateMoradorUseCase useCase;

    @Override
    protected void setupMocks() {
        moradorGateway = createMock(MoradorGateway.class);
        apartamentoGateway = createMock(ApartamentoGateway.class);
    }

    @Override
    protected void setupUseCase() {
        useCase = new CreateMoradorUseCase(moradorGateway, apartamentoGateway);
    }

    @Test
    @DisplayName("Deve criar morador com sucesso quando dados válidos")
    void deveCriarMoradorComSucesso() throws ApartamentoNotFoundException {
        // Arrange
        UUID apartamentoId = UUID.randomUUID();
        IMoradorRegistrationData dados = criarDadosMorador(apartamentoId);
        Apartamento apartamento = new Apartamento('A', (byte) 10, (byte) 101);
        Morador moradorEsperado = new Morador("12345678901", "João Silva", 
            List.of("11999999999"), "joao@email.com", List.of(apartamentoId));

        when(apartamentoGateway.findById(apartamentoId)).thenReturn(Optional.of(apartamento));
        when(moradorGateway.save(any(Morador.class))).thenReturn(moradorEsperado);

        // Act
        Morador resultado = useCase.execute(dados);

        // Assert
        assertNotNull(resultado);
        assertEquals("12345678901", resultado.getCpf());
        verify(apartamentoGateway).findById(apartamentoId);
        verify(moradorGateway).save(any(Morador.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando apartamento não existe")
    void deveLancarExcecaoQuandoApartamentoNaoExiste() {
        // Arrange
        UUID apartamentoId = UUID.randomUUID();
        IMoradorRegistrationData dados = criarDadosMorador(apartamentoId);

        when(apartamentoGateway.findById(apartamentoId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ApartamentoNotFoundException.class, () -> useCase.execute(dados));
        verify(apartamentoGateway).findById(apartamentoId);
        verify(moradorGateway, never()).save(any());
    }

    private IMoradorRegistrationData criarDadosMorador(UUID apartamentoId) {
        IMoradorRegistrationData dados = mock(IMoradorRegistrationData.class);
        when(dados.apartamentoId()).thenReturn(List.of(apartamentoId));
        when(dados.cpf()).thenReturn("12345678901");
        when(dados.nome()).thenReturn("João Silva");
        when(dados.telefone()).thenReturn(List.of("11999999999"));
        when(dados.email()).thenReturn("joao@email.com");
        return dados;
    }
}