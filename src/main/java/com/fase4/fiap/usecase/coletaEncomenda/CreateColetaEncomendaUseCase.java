package com.fase4.fiap.usecase.coletaEncomenda;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.fase4.fiap.entity.coletaEncomenda.gateway.ColetaEncomendaGateway;
import com.fase4.fiap.entity.coletaEncomenda.model.ColetaEncomenda;
import static com.fase4.fiap.entity.coletaEncomenda.model.ColetaEncomenda.validacaoDataColeta;
import com.fase4.fiap.entity.recebimento.exception.RecebimentoNotFoundException;
import com.fase4.fiap.entity.recebimento.gateway.RecebimentoGateway;
import com.fase4.fiap.entity.recebimento.model.Recebimento;
import com.fase4.fiap.usecase.coletaEncomenda.dto.ColetaEncomendaRegistrationData;

public class CreateColetaEncomendaUseCase {

    private final ColetaEncomendaGateway coletaEncomendaGateway;
    private final RecebimentoGateway recebimentoGateway;

    public CreateColetaEncomendaUseCase(ColetaEncomendaGateway coletaEncomendaGateway, RecebimentoGateway recebimentoGateway) {
        this.coletaEncomendaGateway = coletaEncomendaGateway;
        this.recebimentoGateway = recebimentoGateway;
    }

    @Transactional
    public ColetaEncomenda execute(ColetaEncomendaRegistrationData dados) throws RecebimentoNotFoundException {
        validacaoDataColeta(dados.dataColeta());

        Recebimento recebimento = buscarRecebimento(dados.recebimentoId());
        recebimento.atualizarEstadoColeta();

        ColetaEncomenda coletaEncomenda = criarColetaEncomenda(dados);
        return this.coletaEncomendaGateway.save(coletaEncomenda);
    }

    private Recebimento buscarRecebimento(UUID recebimentoId) throws RecebimentoNotFoundException {
        return recebimentoGateway.findById(recebimentoId)
                .orElseThrow(() -> new RecebimentoNotFoundException("recebimento not found: " + recebimentoId));
    }

    private ColetaEncomenda criarColetaEncomenda(ColetaEncomendaRegistrationData dados) {
        return new ColetaEncomenda(
                dados.recebimentoId(),
                dados.cpfMoradorColeta(),
                dados.nomeMoradorColeta(),
                dados.dataColeta()
        );
    }

}


