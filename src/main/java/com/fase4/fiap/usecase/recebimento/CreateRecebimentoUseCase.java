package com.fase4.fiap.usecase.recebimento;

import com.fase4.fiap.entity.apartamento.exception.ApartamentoNotFoundException;
import com.fase4.fiap.entity.apartamento.gateway.ApartamentoGateway;
import com.fase4.fiap.entity.message.notificacao.model.Notificacao;
import com.fase4.fiap.entity.recebimento.gateway.RecebimentoGateway;
import com.fase4.fiap.entity.recebimento.model.Recebimento;
import com.fase4.fiap.usecase.message.publish.PublicarNotificacaoUseCase;
import com.fase4.fiap.usecase.recebimento.dto.IRecebimentoRegistrationData;
import org.springframework.transaction.annotation.Transactional;

import static com.fase4.fiap.entity.recebimento.model.Recebimento.validacaoDataEntrega;

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
    public Recebimento execute(IRecebimentoRegistrationData dados) throws ApartamentoNotFoundException {

        validacaoDataEntrega(dados.dataEntrega());

        apartamentoGateway.findById(dados.apartamentoId())
                .orElseThrow(() -> new ApartamentoNotFoundException("Apartamento not found: " + dados.apartamentoId()));

        Recebimento recebimento = new Recebimento(dados.apartamentoId(), dados.descricao(), dados.dataEntrega(), dados.estadoColeta());

        String mensagem = "Nova encomenda recebida: " +
                "\nDescriÃ§Ã£o: " +
                dados.descricao() +
                "\nData de Entrega: " +
                dados.dataEntrega().toString();
        // Send notification to the apartment user
        Notificacao notificacao = new Notificacao(
                dados.apartamentoId(),
                mensagem,
                dados.dataEntrega()
        );
        publicarNotificacaoUseCase.publish(notificacao);

        return this.recebimentoGateway.save(recebimento);
    }

}


