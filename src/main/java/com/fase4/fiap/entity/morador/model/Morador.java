package com.fase4.fiap.entity.morador.model;

import java.util.List;
import java.util.UUID;

import com.fase4.fiap.entity.auxiliary.AbstractEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Morador extends AbstractEntity {
    
    @NonNull private String cpf;
    @NonNull private String nome;
    @NonNull private List<String> telefone;
    @NonNull private String email;
    @NonNull private List<UUID> apartamentoId;
}