package com.neutronbinary.infectolabs.gateway.repository;

import com.neutronbinary.infectolabs.gateway.domain.NBUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive repository for the NBUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NBUserRepository extends R2dbcRepository<NBUser, Long>, NBUserRepositoryInternal {
    // just to avoid having unambigous methods
    @Override
    Flux<NBUser> findAll();

    @Override
    Mono<NBUser> findById(Long id);

    @Override
    <S extends NBUser> Mono<S> save(S entity);
}

interface NBUserRepositoryInternal {
    <S extends NBUser> Mono<S> insert(S entity);
    <S extends NBUser> Mono<S> save(S entity);
    Mono<Integer> update(NBUser entity);

    Flux<NBUser> findAll();
    Mono<NBUser> findById(Long id);
    Flux<NBUser> findAllBy(Pageable pageable);
    Flux<NBUser> findAllBy(Pageable pageable, Criteria criteria);
}
