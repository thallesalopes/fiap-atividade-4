package com.fase4.fiap.infraestructure.recebimento.gateway;

import com.fase4.fiap.entity.recebimento.gateway.RecebimentoGateway;
import com.fase4.fiap.entity.recebimento.model.Recebimento;
import com.fase4.fiap.infraestructure.auxiliary.configuration.db.repository.RecebimentoRepository;
import com.fase4.fiap.infraestructure.auxiliary.configuration.db.schema.RecebimentoSchema;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RecebimentoDatabaseGateway implements RecebimentoGateway {

    private final RecebimentoRepository recebimentoRepository;

    public RecebimentoDatabaseGateway(RecebimentoRepository recebimentoRepository) {
        this.recebimentoRepository = recebimentoRepository;
    }

    @Override
    @Caching(
            evict = {@CacheEvict(cacheNames = "recebimentos", allEntries = true, beforeInvocation = true)},
            put = {@CachePut(cacheNames = "recebimento", key = "#recebimento.id")}
    )
    public Recebimento save(Recebimento recebimento) {
        return recebimentoRepository.save(new RecebimentoSchema(recebimento)).toEntity();
    }

    @Override
    @Cacheable(value = "recebimento", key = "#id")
    public Optional<Recebimento> findById(UUID id) {
        return recebimentoRepository.findById(id).map(RecebimentoSchema::toEntity);
    }

    @Override
    @Cacheable(value = "recebimentosByApartamentoId", key = "#id")
    public List<Recebimento> findAllByApartamentoId(UUID id) {
        return recebimentoRepository.findAllByApartamentoId(id).stream().map(RecebimentoSchema::toEntity).toList();
    }

    @Override
    @Cacheable(value = "recebimentos", key = "'recebimentos'")
    public List<Recebimento> findAll() {
        return recebimentoRepository.findAll().stream().map(RecebimentoSchema::toEntity).toList();
    }

    @Override
    @Caching(
            evict = {@CacheEvict(cacheNames = "recebimentos", allEntries = true, beforeInvocation = true)},
            put = {@CachePut(cacheNames = "recebimento", key = "#recebimento.id")}
    )
    public Recebimento update(Recebimento recebimento) {
        return recebimentoRepository.save(new RecebimentoSchema(recebimento)).toEntity();
    }

}



