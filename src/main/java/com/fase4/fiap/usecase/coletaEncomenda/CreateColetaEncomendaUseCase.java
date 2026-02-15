package com.fase4.fiap.usecase.coletaEncomenda;

import org.springframework.transaction.annotation.Transactional;

import com.fase4.fiap.entity.coletaEncomenda.gateway.ColetaEncomendaGateway;
import com.fase4.fiap.entity.coletaEncomenda.model.ColetaEncomenda;
import static com.fase4.fiap.entity.coletaEncomenda.model.ColetaEncomenda.validacaoDataColeta;
import com.fase4.fiap.entity.recebimento.exception.RecebimentoNotFoundException;
import com.fase4.fiap.entity.recebimento.gateway.RecebimentoGateway;
import com.fase4.fiap.entity.recebimento.model.Recebimento;
import com.fase4.fiap.usecase.coletaEncomenda.dto.IColetaEncomendaRegistrationData;

public class CreateColetaEncomendaUseCase {

    private final ColetaEncomendaGateway coletaEncomendaGateway;
    private final RecebimentoGateway recebimentoGateway;

    public CreateColetaEncomendaUseCase(ColetaEncomendaGateway coletaEncomendaGateway, RecebimentoGateway recebimentoGateway) {
        this.coletaEncomendaGateway = coletaEncomendaGateway;
        this.recebimentoGateway = recebimentoGateway;
    }

    @Transactional
    public ColetaEncomenda execute(IColetaEncomendaRegistrationData dados) throws RecebimentoNotFoundException {

        validacaoDataColeta(dados.dataColeta());

        Recebimento recebimento = recebimentoGateway.findById(dados.recebimentoId())
                .orElseThrow(() -> new RecebimentoNotFoundException("recebimento not found: " + dados.recebimentoId()));

        ColetaEncomenda coletaEncomenda = new ColetaEncomenda(dados.recebimentoId(), dados.cpfMoradorColeta(), dados.nomeMoradorColeta(), dados.dataColeta());

        recebimento.atualizarEstadoColeta();

        return this.coletaEncomendaGateway.save(coletaEncomenda);
    }

}


