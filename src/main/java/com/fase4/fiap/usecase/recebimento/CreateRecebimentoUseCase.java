package com.fase4.fiap.usecase.recebimento;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.fase4.fiap.entity.apartamento.exception.ApartamentoNotFoundException;
import com.fase4.fiap.entity.apartamento.gateway.ApartamentoGateway;
import com.fase4.fiap.entity.message.notificacao.model.Notificacao;
import com.fase4.fiap.entity.recebimento.gateway.RecebimentoGateway;
import com.fase4.fiap.entity.recebimento.model.Recebimento;
import static com.fase4.fiap.entity.recebimento.model.Recebimento.validacaoDataEntrega;
import com.fase4.fiap.usecase.dto.RecebimentoRequest;
import com.fase4.fiap.usecase.message.publish.PublicarNotificacaoUseCase;

public class CreateRecebimentoUseCase {

    private final RecebimentoGateway recebimentoGateway;
    private final ApartamentoGateway apartamentoGateway;
    private final PublicarNotificacaoUseCase publicarNotificacaoUseCase;

    public CreateRecebimentoUseCase(RecebimentoGateway recebimentoGateway, ApartamentoGateway apartamentoGateway, PublicarNotificacaoUseCase publicarNotificacaoUseCase) {
        this.recebimentoGateway = recebimentoGateway;
        this.apartamentoGateway = apartamentoGateway;
        this.publicarNotificacaoUseCase = publicarNotificacaoUseCase;
    }

    @Transactional
    public Recebimento execute(RecebimentoRequest dados) throws ApartamentoNotFoundException {
        validacaoDataEntrega(dados.dataEntrega());
        validarApartamento(dados.apartamentoId());

        Recebimento recebimento = criarRecebimento(dados);
        enviarNotificacao(dados);

        return this.recebimentoGateway.save(recebimento);
    }

    private void validarApartamento(UUID apartamentoId) throws ApartamentoNotFoundException {
        apartamentoGateway.findById(apartamentoId)
                .orElseThrow(() -> new ApartamentoNotFoundException("Apartamento not found: " + apartamentoId));
    }

    private Recebimento criarRecebimento(RecebimentoRequest dados) {
        return new Recebimento(
                dados.apartamentoId(),
                dados.descricao(),
                dados.dataEntrega(),
                dados.estadoColeta()
        );
    }

    private void enviarNotificacao(RecebimentoRequest dados) {
        String mensagem = construirMensagemNotificacao(dados);
        Notificacao notificacao = new Notificacao(
                dados.apartamentoId(),
                mensagem,
                dados.dataEntrega()
        );
        publicarNotificacaoUseCase.publish(notificacao);
    }

    private String construirMensagemNotificacao(RecebimentoRequest dados) {
        return "Nova encomenda recebida: " +
                "\nDescrição: " + dados.descricao() +
                "\nData de Entrega: " + dados.dataEntrega().toString();
    }

}


