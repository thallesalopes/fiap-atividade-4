package com.fase4.fiap.infraestructure.auxiliary.configuration.db.schema;

import java.io.Serializable;
import java.util.UUID;

import com.fase4.fiap.entity.apartamento.model.Apartamento;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "apartamento", uniqueConstraints = @UniqueConstraint(columnNames = {"torre", "andar", "numero"}))
public class ApartamentoSchema implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 1)
    @NotBlank @Size(min = 1, max = 1)
    private char torre;

    @Column(nullable = false)
    @Min(1) @Max(127)
    private byte numero;

    @Column(nullable = false)
    @Min(1) @Max(127)
    private byte andar;

    public ApartamentoSchema(Apartamento apartamento) {
        this.id = apartamento.getId();
        this.torre = apartamento.getTorre();
        this.andar = apartamento.getAndar();
        this.numero = apartamento.getNumero();
    }

    public Apartamento toEntity() {
        Apartamento apt = new Apartamento(torre, andar, numero);
        apt.setId(id);
        return apt;
    }
}