package com.fase4.fiap.infraestructure.auxiliary.configuration.web;

import java.util.ResourceBundle;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fase4.fiap.entity.apartamento.gateway.ApartamentoGateway;
import com.fase4.fiap.entity.coletaEncomenda.gateway.ColetaEncomendaGateway;
import com.fase4.fiap.entity.message.email.gateway.EmailGateway;
import com.fase4.fiap.entity.message.notificacao.gateway.NotificacaoGateway;
import com.fase4.fiap.entity.message.notificacaoLeitura.gateway.NotificacaoLeituraGateway;
import com.fase4.fiap.entity.morador.gateway.MoradorGateway;
import com.fase4.fiap.entity.recebimento.gateway.RecebimentoGateway;
import com.fase4.fiap.infraestructure.apartamento.gateway.ApartamentoDatabaseGateway;
import com.fase4.fiap.infraestructure.auxiliary.configuration.db.repository.ApartamentoRepository;
import com.fase4.fiap.infraestructure.auxiliary.configuration.db.repository.ColetaEncomendaRepository;
import com.fase4.fiap.infraestructure.auxiliary.configuration.db.repository.MoradorRepository;
import com.fase4.fiap.infraestructure.auxiliary.configuration.db.repository.NotificacaoLeituraRepository;
import com.fase4.fiap.infraestructure.auxiliary.configuration.db.repository.NotificacaoRepository;
import com.fase4.fiap.infraestructure.auxiliary.configuration.db.repository.RecebimentoRepository;
import com.fase4.fiap.infraestructure.coletaEncomenda.gateway.ColetaEncomendaDatabaseGateway;
import com.fase4.fiap.infraestructure.message.gateway.NotificacaoDatabaseGateway;
import com.fase4.fiap.infraestructure.message.gateway.NotificacaoLeituraDatabaseGateway;
import com.fase4.fiap.infraestructure.message.producer.NotificacaoProducer;
import com.fase4.fiap.infraestructure.morador.gateway.MoradorDatabaseGateway;
import com.fase4.fiap.infraestructure.recebimento.gateway.RecebimentoDatabaseGateway;
import com.fase4.fiap.usecase.apartamento.CreateApartamentoUseCase;
import com.fase4.fiap.usecase.apartamento.DeleteApartamentoUseCase;
import com.fase4.fiap.usecase.apartamento.GetApartamentoUseCase;
import com.fase4.fiap.usecase.apartamento.SearchApartamentoUseCase;
import com.fase4.fiap.usecase.coletaEncomenda.CreateColetaEncomendaUseCase;
import com.fase4.fiap.usecase.coletaEncomenda.GetColetaEncomendaUseCase;
import com.fase4.fiap.usecase.coletaEncomenda.SearchColetaEncomendaUseCase;
import com.fase4.fiap.usecase.message.NotificarLeituraUseCase;
import com.fase4.fiap.usecase.message.publish.PublicarNotificacaoUseCase;
import com.fase4.fiap.usecase.message.subscribe.ProcessarNotificacaoUseCase;
import com.fase4.fiap.usecase.morador.CreateMoradorUseCase;
import com.fase4.fiap.usecase.morador.DeleteMoradorUseCase;
import com.fase4.fiap.usecase.morador.GetMoradorUseCase;
import com.fase4.fiap.usecase.morador.SearchByApartamentoMoradorUseCase;
import com.fase4.fiap.usecase.morador.SearchMoradorUseCase;
import com.fase4.fiap.usecase.morador.UpdateMoradorUseCase;
import com.fase4.fiap.usecase.recebimento.CreateRecebimentoUseCase;
import com.fase4.fiap.usecase.recebimento.GetRecebimentoUseCase;
import com.fase4.fiap.usecase.recebimento.SearchByApartamentoRecebimentoUseCase;
import com.fase4.fiap.usecase.recebimento.SearchRecebimentoUseCase;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
    }

    @Bean
    public ResourceBundle messageBundle() {
        return ResourceBundle.getBundle("ValidationMessages");
    }

    @Bean
    public LocaleResolver sessionLocaleResolver() {
        return new AcceptHeaderResolver();
    }

    @Bean
    public CreateApartamentoUseCase createApartamentoUseCase(ApartamentoRepository apartamentoRepository) {
        ApartamentoGateway apartamentoGateway = new ApartamentoDatabaseGateway(apartamentoRepository);
        return new CreateApartamentoUseCase(apartamentoGateway);
    }

    @Bean
    public DeleteApartamentoUseCase deleteApartamentoUseCase(ApartamentoRepository apartamentoRepository) {
        ApartamentoGateway apartamentoGateway = new ApartamentoDatabaseGateway(apartamentoRepository);
        return new DeleteApartamentoUseCase(apartamentoGateway);
    }

    @Bean
    public GetApartamentoUseCase getApartamentoUseCase(ApartamentoRepository apartamentoRepository) {
        ApartamentoGateway apartamentoGateway = new ApartamentoDatabaseGateway(apartamentoRepository);
        return new GetApartamentoUseCase(apartamentoGateway);
    }

    @Bean
    public SearchApartamentoUseCase searchApartamentoUseCase(ApartamentoRepository apartamentoRepository) {
        ApartamentoGateway apartamentoGateway = new ApartamentoDatabaseGateway(apartamentoRepository);
        return new SearchApartamentoUseCase(apartamentoGateway);
    }

    @Bean
    public CreateColetaEncomendaUseCase createColetaEncomendaUseCase(ColetaEncomendaRepository coletaEncomendaRepository,
                                                                     RecebimentoRepository recebimentoRepository) {
        ColetaEncomendaGateway coletaEncomendaGateway = new ColetaEncomendaDatabaseGateway(coletaEncomendaRepository);
        RecebimentoGateway recebimentoGateway = new RecebimentoDatabaseGateway(recebimentoRepository);
        return new CreateColetaEncomendaUseCase(coletaEncomendaGateway, recebimentoGateway);
    }

    @Bean
    public GetColetaEncomendaUseCase getColetaEncomendaUseCase(ColetaEncomendaRepository coletaEncomendaRepository) {
        ColetaEncomendaGateway coletaEncomendaGateway = new ColetaEncomendaDatabaseGateway(coletaEncomendaRepository);
        return new GetColetaEncomendaUseCase(coletaEncomendaGateway);
    }

    @Bean
    public SearchColetaEncomendaUseCase searchColetaEncomendaUseCase(ColetaEncomendaRepository coletaEncomendaRepository) {
        ColetaEncomendaGateway coletaEncomendaGateway = new ColetaEncomendaDatabaseGateway(coletaEncomendaRepository);
        return new SearchColetaEncomendaUseCase(coletaEncomendaGateway);
    }

    @Bean
    public CreateMoradorUseCase createMoradorUseCase(MoradorRepository moradorRepository,
                                                     ApartamentoRepository apartamentoRepository) {
        MoradorGateway moradorGateway = new MoradorDatabaseGateway(moradorRepository);
        ApartamentoGateway apartamentoGateway = new ApartamentoDatabaseGateway(apartamentoRepository);
        return new CreateMoradorUseCase(moradorGateway, apartamentoGateway);
    }

    @Bean
    public DeleteMoradorUseCase deleteMoradorUseCase(MoradorRepository moradorRepository) {
        MoradorGateway moradorGateway = new MoradorDatabaseGateway(moradorRepository);
        return new DeleteMoradorUseCase(moradorGateway);
    }

    @Bean
    public GetMoradorUseCase getMoradorUseCase(MoradorRepository moradorRepository) {
        MoradorGateway moradorGateway = new MoradorDatabaseGateway(moradorRepository);
        return new GetMoradorUseCase(moradorGateway);
    }

    @Bean
    public SearchByApartamentoMoradorUseCase searchMoradorByApartamentoIdUseCase(MoradorRepository moradorRepository,
                                                                                 ApartamentoRepository apartamentoRepository) {
        MoradorGateway moradorGateway = new MoradorDatabaseGateway(moradorRepository);
        ApartamentoGateway apartamentoGateway = new ApartamentoDatabaseGateway(apartamentoRepository);
        return new SearchByApartamentoMoradorUseCase(moradorGateway, apartamentoGateway);
    }

    @Bean
    public SearchMoradorUseCase searchMoradorUseCase(MoradorRepository moradorRepository) {
        MoradorGateway moradorGateway = new MoradorDatabaseGateway(moradorRepository);
        return new SearchMoradorUseCase(moradorGateway);
    }

    @Bean
    public UpdateMoradorUseCase updateMoradorUseCase(MoradorRepository moradorRepository,
                                                     ApartamentoRepository apartamentoRepository) {
        MoradorGateway moradorGateway = new MoradorDatabaseGateway(moradorRepository);
        ApartamentoGateway apartamentoGateway = new ApartamentoDatabaseGateway(apartamentoRepository);
        return new UpdateMoradorUseCase(moradorGateway, apartamentoGateway);
    }

    @Bean
    public MoradorGateway moradorGateway(MoradorRepository moradorRepository) {
        return new MoradorDatabaseGateway(moradorRepository);
    }

    @Bean
    public CreateRecebimentoUseCase createRecebimentoUseCase(RecebimentoRepository recebimentoRepository,
                                                                               ApartamentoRepository apartamentoRepository,
                                                                               PublicarNotificacaoUseCase publicarNotificacaoUseCase) {
        RecebimentoGateway recebimentoGateway = new RecebimentoDatabaseGateway(recebimentoRepository);
        ApartamentoGateway apartamentoGateway = new ApartamentoDatabaseGateway(apartamentoRepository);
        return new CreateRecebimentoUseCase(recebimentoGateway, apartamentoGateway, publicarNotificacaoUseCase);
    }

    @Bean
    public GetRecebimentoUseCase getRecebimentoUseCase(RecebimentoRepository recebimentoRepository) {
        RecebimentoGateway recebimentoGateway = new RecebimentoDatabaseGateway(recebimentoRepository);
        return new GetRecebimentoUseCase(recebimentoGateway);
    }

    @Bean
    public SearchByApartamentoRecebimentoUseCase searchByApartamentoRecebimentoUseCase(RecebimentoRepository recebimentoRepository,
                                                                                                        ApartamentoRepository apartamentoRepository) {
        RecebimentoGateway recebimentoGateway = new RecebimentoDatabaseGateway(recebimentoRepository);
        ApartamentoGateway apartamentoGateway = new ApartamentoDatabaseGateway(apartamentoRepository);
        return new SearchByApartamentoRecebimentoUseCase(recebimentoGateway, apartamentoGateway);
    }

    @Bean
    public SearchRecebimentoUseCase searchRecebimentoUseCase(RecebimentoRepository recebimentoRepository) {
        RecebimentoGateway recebimentoGateway = new RecebimentoDatabaseGateway(recebimentoRepository);
        return new SearchRecebimentoUseCase(recebimentoGateway);
    }

    @Bean
    public PublicarNotificacaoUseCase publicarNotificacaoUseCase(NotificacaoRepository notificacaoRepository,
                                                                 NotificacaoProducer notificacaoProducer) {
        NotificacaoGateway notificacaoGateway = new NotificacaoDatabaseGateway(
                notificacaoRepository,
                notificacaoProducer
        );
        return new PublicarNotificacaoUseCase(notificacaoGateway);
    }

    @Bean
    public NotificarLeituraUseCase notificarLeituraUseCase(NotificacaoLeituraGateway notificacaoLeituraGateway,
                                                           NotificacaoGateway notificacaoGateway) {
        return new NotificarLeituraUseCase(notificacaoLeituraGateway, notificacaoGateway);
    }

    @Bean
    public NotificacaoLeituraGateway notificacaoLeituraGateway(NotificacaoLeituraRepository repository) {
        return new NotificacaoLeituraDatabaseGateway(repository);
    }

    @Bean
    public NotificacaoGateway notificacaoGateway(
            NotificacaoRepository notificacaoRepository,
            NotificacaoProducer notificacaoProducer) {
        return new NotificacaoDatabaseGateway(notificacaoRepository, notificacaoProducer);
    }

    @Bean
    public ProcessarNotificacaoUseCase processarNotificacaoUseCase(EmailGateway emailGateway,
                                                                   MoradorGateway moradorGateway,
                                                                   NotificacaoGateway notificacaoGateway) {
        return new ProcessarNotificacaoUseCase(emailGateway, moradorGateway, notificacaoGateway);
    }

}


