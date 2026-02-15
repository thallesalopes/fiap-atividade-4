package com.fase4.fiap.infraestructure.auxiliary.configuration.db.schema;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.fase4.fiap.entity.recebimento.model.Recebimento;
import com.fase4.fiap.entity.recebimento.model.Recebimento.EstadoColeta;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "recebimento",
        indexes = {
                @Index(name = "idx_apartamento_id", columnList = "apartamento_id"),
                @Index(name = "idx_data_entrega", columnList = "data_entrega DESC"),
                @Index(name = "idx_estado_coleta", columnList = "estado_coleta")
        }
)
public class RecebimentoSchema implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "apartamento_id", nullable = false)
    @NotNull(message = "Apartamento ID nÃ£o pode ser nulo")
    private UUID apartamentoId;

    @Column(nullable = false)
    @NotBlank(message = "DescriÃ§Ã£o nÃ£o pode estar vazio")
    @Size(max = 255, message = "DescriÃ§Ã£o deve ter no mÃ¡ximo 255 caracteres")
    private String descricao;

    @Column(name = "data_entrega", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @NotNull(message = "Data entrega nÃ£o pode ser nula")
    @PastOrPresent(message = "Data entrega deve ser passada ou presente")
    private OffsetDateTime dataEntrega;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_coleta", nullable = false)
    @NotNull(message = "Estado de coleta nÃ£o pode ser nulo")
    private EstadoColeta estadoColeta;

    public RecebimentoSchema(Recebimento recebimento) {
        this.id = recebimento.getId();
        this.apartamentoId = recebimento.getApartamentoId();
        this.descricao = recebimento.getDescricao();
        this.dataEntrega = recebimento.getDataEntrega();
        this.estadoColeta = recebimento.getEstadoColeta();
    }

    public Recebimento toEntity() {
        Recebimento recebimento = new Recebimento(
                this.apartamentoId,
                this.descricao,
                this.dataEntrega,
                this.estadoColeta
        );
        recebimento.setId(this.id);
        return recebimento;
    }
}


