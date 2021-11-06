package com.neutronbinary.infectolabs.gateway.repository;

import com.neutronbinary.infectolabs.gateway.domain.NBChart;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive repository for the NBChart entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NBChartRepository extends R2dbcRepository<NBChart, Long>, NBChartRepositoryInternal {
    // just to avoid having unambigous methods
    @Override
    Flux<NBChart> findAll();

    @Override
    Mono<NBChart> findById(Long id);

    @Override
    <S extends NBChart> Mono<S> save(S entity);
}

interface NBChartRepositoryInternal {
    <S extends NBChart> Mono<S> insert(S entity);
    <S extends NBChart> Mono<S> save(S entity);
    Mono<Integer> update(NBChart entity);

    Flux<NBChart> findAll();
    Mono<NBChart> findById(Long id);
    Flux<NBChart> findAllBy(Pageable pageable);
    Flux<NBChart> findAllBy(Pageable pageable, Criteria criteria);
}
