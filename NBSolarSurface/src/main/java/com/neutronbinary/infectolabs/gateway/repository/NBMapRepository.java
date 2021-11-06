package com.neutronbinary.infectolabs.gateway.repository;

import com.neutronbinary.infectolabs.gateway.domain.NBMap;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive repository for the NBMap entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NBMapRepository extends R2dbcRepository<NBMap, Long>, NBMapRepositoryInternal {
    // just to avoid having unambigous methods
    @Override
    Flux<NBMap> findAll();

    @Override
    Mono<NBMap> findById(Long id);

    @Override
    <S extends NBMap> Mono<S> save(S entity);
}

interface NBMapRepositoryInternal {
    <S extends NBMap> Mono<S> insert(S entity);
    <S extends NBMap> Mono<S> save(S entity);
    Mono<Integer> update(NBMap entity);

    Flux<NBMap> findAll();
    Mono<NBMap> findById(Long id);
    Flux<NBMap> findAllBy(Pageable pageable);
    Flux<NBMap> findAllBy(Pageable pageable, Criteria criteria);
}
