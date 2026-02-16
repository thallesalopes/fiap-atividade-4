package com.fase4.fiap.usecase.fixtures;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import com.fase4.fiap.entity.apartamento.model.Apartamento;
import com.fase4.fiap.entity.coletaEncomenda.model.ColetaEncomenda;
import com.fase4.fiap.entity.morador.model.Morador;
import com.fase4.fiap.entity.recebimento.model.Recebimento;

/**
 * Classe utilitária para criar objetos de teste (fixtures).
 * Reduz duplicação e melhora legibilidade dos testes.
 */
public class TestFixtures {

    // Constantes reutilizáveis
    public static final String CPF_VALIDO = "12345678901";
    public static final String NOME_PADRAO = "João Silva";
    public static final String EMAIL_PADRAO = "joao@email.com";
    public static final String TELEFONE_PADRAO = "11999999999";
    public static final String DESCRICAO_ENCOMENDA = "Pacote de livros";

    // Apartamento
    public static Apartamento apartamentoPadrao() {
        return new Apartamento('A', (byte) 10, (byte) 101);
    }

    public static Apartamento apartamento(char torre, byte andar, byte numero) {
        return new Apartamento(torre, andar, numero);
    }

    // Morador
    public static Morador moradorPadrao(UUID apartamentoId) {
        return new Morador(
            CPF_VALIDO,
            NOME_PADRAO,
            List.of(TELEFONE_PADRAO),
            EMAIL_PADRAO,
            List.of(apartamentoId)
        );
    }

    public static Morador morador(String cpf, String nome, String email, UUID apartamentoId) {
        return new Morador(cpf, nome, List.of(TELEFONE_PADRAO), email, List.of(apartamentoId));
    }

    // Recebimento
    public static Recebimento recebimentoPadrao(UUID apartamentoId) {
        return new Recebimento(
            apartamentoId,
            DESCRICAO_ENCOMENDA,
            OffsetDateTime.now().minusHours(1),
            Recebimento.EstadoColeta.PENDENTE
        );
    }

    public static Recebimento recebimento(UUID apartamentoId, String descricao, OffsetDateTime dataEntrega) {
        return new Recebimento(apartamentoId, descricao, dataEntrega, Recebimento.EstadoColeta.PENDENTE);
    }

    // Coleta Encomenda
    public static ColetaEncomenda coletaPadrao(UUID recebimentoId) {
        return new ColetaEncomenda(
            recebimentoId,
            CPF_VALIDO,
            NOME_PADRAO,
            OffsetDateTime.now().minusMinutes(30)
        );
    }

    public static ColetaEncomenda coleta(UUID recebimentoId, String cpf, String nome) {
        return new ColetaEncomenda(recebimentoId, cpf, nome, OffsetDateTime.now().minusMinutes(30));
    }

    // UUID Helper
    public static UUID novoId() {
        return UUID.randomUUID();
    }
}
