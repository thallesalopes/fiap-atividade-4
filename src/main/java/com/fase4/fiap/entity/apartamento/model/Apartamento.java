package com.fase4.fiap.entity.apartamento.model;

import com.fase4.fiap.entity.auxiliary.AbstractEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Apartamento extends AbstractEntity {
    
    private char torre;
    private byte andar;
    private byte numero;
}