package com.fase4.fiap.infraestructure.auxiliary.configuration.db.repository;

import com.fase4.fiap.infraestructure.auxiliary.configuration.db.schema.RecebimentoSchema;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecebimentoRepository extends JpaRepository<RecebimentoSchema, UUID> {

    @Query(value = "SELECT * FROM recebimento WHERE apartamento_id = :apartamentoId", nativeQuery = true)
    List<RecebimentoSchema> findAllByApartamentoId(@Param("apartamentoId") UUID apartamentoId);

}
