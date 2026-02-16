package com.fase4.fiap.usecase.message.subscribe;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.fase4.fiap.entity.message.email.gateway.EmailGateway;
import com.fase4.fiap.entity.message.notificacao.gateway.NotificacaoGateway;
import com.fase4.fiap.entity.message.notificacao.model.Notificacao;
import com.fase4.fiap.entity.morador.gateway.MoradorGateway;
import com.fase4.fiap.entity.morador.model.Morador;

public class ProcessarNotificacaoUseCase {

    private final EmailGateway emailGateway;
    private final MoradorGateway moradorGateway;
    private final NotificacaoGateway notificacaoGateway;

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");

    public ProcessarNotificacaoUseCase(EmailGateway emailGateway, MoradorGateway moradorGateway, NotificacaoGateway notificacaoGateway) {
        this.emailGateway = emailGateway;
        this.moradorGateway = moradorGateway;
        this.notificacaoGateway = notificacaoGateway;
    }

    @Transactional(readOnly = true)
    public void execute(Notificacao notificacao) {
        List<Morador> moradores = buscarMoradoresDoApartamento(notificacao.getApartamentoId());
        
        if (moradores.isEmpty()) {
            registrarLogApartamentoSemMoradores(notificacao.getApartamentoId());
            return;
        }

        String assunto = construirAssuntoDoEmail(notificacao);
        enviarEmailsParaTodosOsMoradores(moradores, assunto, notificacao);
    }

    private List<Morador> buscarMoradoresDoApartamento(UUID apartamentoId) {
        return moradorGateway.findAllByApartamentoId(apartamentoId);
    }

    private void registrarLogApartamentoSemMoradores(UUID apartamentoId) {
        System.out.println("Nenhum morador encontrado para apartamento: " + apartamentoId);
    }

    private String construirAssuntoDoEmail(Notificacao notificacao) {
        return "Encomenda recebida em " + notificacao.getDataEnvio().format(DATE_FORMATTER);
    }

    private void enviarEmailsParaTodosOsMoradores(List<Morador> moradores, String assunto, Notificacao notificacao) {
        moradores.stream()
                .filter(morador -> !morador.getEmail().isBlank())
                .forEach(morador -> enviarEmailParaUmMorador(morador, assunto, notificacao));
    }

    private void enviarEmailParaUmMorador(Morador morador, String assunto, Notificacao notificacao) {
        try {
            emailGateway.send(
                    morador.getEmail(),
                    assunto,
                    notificacao.getMensagem(),
                    notificacao.getId(),
                    morador.getId()
            );
            notificacaoGateway.save(notificacao);
        } catch (Exception e) {
            registrarErroNoEnvioDeEmail(morador.getEmail(), e);
        }
    }

    private void registrarErroNoEnvioDeEmail(String email, Exception e) {
        System.err.println("Falha ao enviar e-mail para: " + email + " | Erro: " + e.getMessage());
    }
}