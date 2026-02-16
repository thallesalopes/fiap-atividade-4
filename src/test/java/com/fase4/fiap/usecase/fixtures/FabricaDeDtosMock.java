package com.fase4.fiap.usecase.fixtures;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fase4.fiap.entity.recebimento.model.Recebimento;
import com.fase4.fiap.usecase.apartamento.dto.ApartamentoRegistrationData;
import com.fase4.fiap.usecase.coletaEncomenda.dto.ColetaEncomendaRegistrationData;
import com.fase4.fiap.usecase.morador.dto.IMoradorRegistrationData;
import com.fase4.fiap.usecase.morador.dto.MoradorUpdateData;
import com.fase4.fiap.usecase.recebimento.dto.RecebimentoRegistrationData;

public class FabricaDeDtosMock {

    public static ApartamentoRegistrationData apartamentoDto(char torre, byte andar, byte numero) {
        ApartamentoRegistrationData dto = mock(ApartamentoRegistrationData.class);
        when(dto.torre()).thenReturn(torre);
        when(dto.andar()).thenReturn(andar);
        when(dto.numero()).thenReturn(numero);
        return dto;
    }

    public static ApartamentoRegistrationData apartamentoDtoPadrao() {
        return apartamentoDto('A', (byte) 10, (byte) 101);
    }

    public static IMoradorRegistrationData moradorDto(String cpf, String nome, String email, UUID apartamentoId) {
        IMoradorRegistrationData dto = mock(IMoradorRegistrationData.class);
        when(dto.cpf()).thenReturn(cpf);
        when(dto.nome()).thenReturn(nome);
        when(dto.email()).thenReturn(email);
        when(dto.telefone()).thenReturn(List.of("11999999999"));
        when(dto.apartamentoId()).thenReturn(List.of(apartamentoId));
        return dto;
    }

    public static IMoradorRegistrationData moradorDtoPadrao(UUID apartamentoId) {
        return moradorDto("12345678901", "João Silva", "joao@email.com", apartamentoId);
    }

    public static MoradorUpdateData moradorUpdateDto(String nome, List<String> telefones, UUID apartamentoId) {
        MoradorUpdateData dto = mock(MoradorUpdateData.class);
        when(dto.nome()).thenReturn(nome);
        when(dto.telefone()).thenReturn(telefones);
        when(dto.apartamentoId()).thenReturn(List.of(apartamentoId));
        return dto;
    }

    public static RecebimentoRegistrationData recebimentoDto(
            UUID apartamentoId, 
            String descricao, 
            OffsetDateTime dataEntrega) {
        RecebimentoRegistrationData dto = mock(RecebimentoRegistrationData.class);
        when(dto.apartamentoId()).thenReturn(apartamentoId);
        when(dto.descricao()).thenReturn(descricao);
        when(dto.dataEntrega()).thenReturn(dataEntrega);
        when(dto.estadoColeta()).thenReturn(Recebimento.EstadoColeta.PENDENTE);
        return dto;
    }

    public static RecebimentoRegistrationData recebimentoDtoPadrao(UUID apartamentoId) {
        return recebimentoDto(apartamentoId, "Pacote de livros", OffsetDateTime.now().minusHours(1));
    }

    public static ColetaEncomendaRegistrationData coletaDto(
            UUID recebimentoId, 
            String cpf, 
            String nome, 
            OffsetDateTime dataColeta) {
        ColetaEncomendaRegistrationData dto = mock(ColetaEncomendaRegistrationData.class);
        when(dto.recebimentoId()).thenReturn(recebimentoId);
        when(dto.cpfMoradorColeta()).thenReturn(cpf);
        when(dto.nomeMoradorColeta()).thenReturn(nome);
        when(dto.dataColeta()).thenReturn(dataColeta);
        return dto;
    }

    public static ColetaEncomendaRegistrationData coletaDtoPadrao(UUID recebimentoId) {
        return coletaDto(recebimentoId, "12345678901", "João Silva", OffsetDateTime.now().minusMinutes(30));
    }
}
