package com.fase4.fiap.infraestructure.auxiliary.configuration.db.schema;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.fase4.fiap.entity.coletaEncomenda.model.ColetaEncomenda;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "coleta_encomenda",
        indexes = {
                @Index(name = "idx_cpf_morador_coleta", columnList = "cpf_morador_coleta")
        }
)
public class ColetaEncomendaSchema implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "recebimento_encomenda_id", nullable = false)
    @NotNull(message = "Recebimento Encomenda ID nÃ£o pode ser nulo")
    private UUID recebimentoId;

    @Column(name = "cpf_morador_coleta", nullable = false, length = 11)
    @NotBlank(message = "CPF nÃ£o pode estar vazio")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter exatamente 11 dÃ­gitos")
    private String cpfMoradorColeta;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Nome nÃ£o pode estar vazio")
    @Size(max = 100, message = "Nome deve ter no mÃ¡ximo 100 caracteres")
    private String nomeMoradorColeta;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @NotNull(message = "Data coleta nÃ£o pode ser nula")
    @PastOrPresent(message = "Data coleta deve ser passada ou presente")
    private OffsetDateTime dataColeta;

    public ColetaEncomendaSchema(ColetaEncomenda coletaEncomenda) {
        this.id = coletaEncomenda.getId();
        this.recebimentoId = coletaEncomenda.getRecebimentoId();
        this.cpfMoradorColeta = coletaEncomenda.getCpfMoradorColeta();
        this.nomeMoradorColeta = coletaEncomenda.getNomeMoradorColeta();
        this.dataColeta = coletaEncomenda.getDataColeta();
    }

    public ColetaEncomenda toEntity() {
        ColetaEncomenda coletaEncomenda = new ColetaEncomenda(
                this.recebimentoId,
                this.cpfMoradorColeta,
                this.nomeMoradorColeta,
                this.dataColeta
        );
        coletaEncomenda.setId(this.id);
        return coletaEncomenda;
    }
}
