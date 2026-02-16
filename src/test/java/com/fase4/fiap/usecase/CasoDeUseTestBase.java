package com.fase4.fiap.usecase;

import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.mock;

public abstract class CasoDeUseTestBase {

    @BeforeEach
    void configuracaoBase() {
        configurarMocks();
        configurarCasoDeUso();
    }

    protected abstract void configurarMocks();

    protected abstract void configurarCasoDeUso();

    protected <T> T criarMock(Class<T> clazz) {
        return mock(clazz);
    }
}
