package com.neutronbinary.infectolabs.gateway.repository;

import com.neutronbinary.infectolabs.gateway.domain.NBPalette;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive repository for the NBPalette entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NBPaletteRepository extends R2dbcRepository<NBPalette, Long>, NBPaletteRepositoryInternal {
    // just to avoid having unambigous methods
    @Override
    Flux<NBPalette> findAll();

    @Override
    Mono<NBPalette> findById(Long id);

    @Override
    <S extends NBPalette> Mono<S> save(S entity);
}

interface NBPaletteRepositoryInternal {
    <S extends NBPalette> Mono<S> insert(S entity);
    <S extends NBPalette> Mono<S> save(S entity);
    Mono<Integer> update(NBPalette entity);

    Flux<NBPalette> findAll();
    Mono<NBPalette> findById(Long id);
    Flux<NBPalette> findAllBy(Pageable pageable);
    Flux<NBPalette> findAllBy(Pageable pageable, Criteria criteria);
}
