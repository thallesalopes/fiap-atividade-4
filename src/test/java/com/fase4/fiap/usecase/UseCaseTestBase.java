package com.fase4.fiap.usecase;

import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.mock;

/**
 * Classe base para testes de Use Cases.
 * Fornece setup comum e utilitários para reduzir duplicação.
 */
public abstract class UseCaseTestBase {

    @BeforeEach
    void baseSetUp() {
        setupMocks();
        setupUseCase();
    }

    /**
     * Criar os mocks necessários para o teste.
     * Implementar nas classes filhas.
     */
    protected abstract void setupMocks();

    /**
     * Inicializar o use case com os mocks.
     * Implementar nas classes filhas.
     */
    protected abstract void setupUseCase();

    /**
     * Utilitário para criar mock genérico.
     */
    protected <T> T createMock(Class<T> clazz) {
        return mock(clazz);
    }
}
