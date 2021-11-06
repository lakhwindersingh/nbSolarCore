package com.neutronbinary.infectolabs.gateway.web.rest;

import com.neutronbinary.infectolabs.gateway.domain.NBMap;
import com.neutronbinary.infectolabs.gateway.repository.NBMapRepository;
import com.neutronbinary.infectolabs.gateway.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link com.neutronbinary.infectolabs.gateway.domain.NBMap}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NBMapResource {

    private final Logger log = LoggerFactory.getLogger(NBMapResource.class);

    private static final String ENTITY_NAME = "nBMap";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NBMapRepository nBMapRepository;

    public NBMapResource(NBMapRepository nBMapRepository) {
        this.nBMapRepository = nBMapRepository;
    }

    /**
     * {@code POST  /nb-maps} : Create a new nBMap.
     *
     * @param nBMap the nBMap to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nBMap, or with status {@code 400 (Bad Request)} if the nBMap has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nb-maps")
    public Mono<ResponseEntity<NBMap>> createNBMap(@RequestBody NBMap nBMap) throws URISyntaxException {
        log.debug("REST request to save NBMap : {}", nBMap);
        if (nBMap.getId() != null) {
            throw new BadRequestAlertException("A new nBMap cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return nBMapRepository
            .save(nBMap)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/nb-maps/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /nb-maps/:id} : Updates an existing nBMap.
     *
     * @param id the id of the nBMap to save.
     * @param nBMap the nBMap to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nBMap,
     * or with status {@code 400 (Bad Request)} if the nBMap is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nBMap couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nb-maps/{id}")
    public Mono<ResponseEntity<NBMap>> updateNBMap(@PathVariable(value = "id", required = false) final Long id, @RequestBody NBMap nBMap)
        throws URISyntaxException {
        log.debug("REST request to update NBMap : {}, {}", id, nBMap);
        if (nBMap.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nBMap.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return nBMapRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return nBMapRepository
                    .save(nBMap)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                            .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /nb-maps/:id} : Partial updates given fields of an existing nBMap, field will ignore if it is null
     *
     * @param id the id of the nBMap to save.
     * @param nBMap the nBMap to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nBMap,
     * or with status {@code 400 (Bad Request)} if the nBMap is not valid,
     * or with status {@code 404 (Not Found)} if the nBMap is not found,
     * or with status {@code 500 (Internal Server Error)} if the nBMap couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nb-maps/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<NBMap>> partialUpdateNBMap(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NBMap nBMap
    ) throws URISyntaxException {
        log.debug("REST request to partial update NBMap partially : {}, {}", id, nBMap);
        if (nBMap.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nBMap.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return nBMapRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<NBMap> result = nBMapRepository
                    .findById(nBMap.getId())
                    .map(existingNBMap -> {
                        if (nBMap.getNbID() != null) {
                            existingNBMap.setNbID(nBMap.getNbID());
                        }
                        if (nBMap.getNbName() != null) {
                            existingNBMap.setNbName(nBMap.getNbName());
                        }
                        if (nBMap.getNbOwner() != null) {
                            existingNBMap.setNbOwner(nBMap.getNbOwner());
                        }
                        if (nBMap.getNbOwnerPrivateKey() != null) {
                            existingNBMap.setNbOwnerPrivateKey(nBMap.getNbOwnerPrivateKey());
                        }
                        if (nBMap.getNbOwnerPublicKey() != null) {
                            existingNBMap.setNbOwnerPublicKey(nBMap.getNbOwnerPublicKey());
                        }
                        if (nBMap.getNbMapPublishMethod() != null) {
                            existingNBMap.setNbMapPublishMethod(nBMap.getNbMapPublishMethod());
                        }
                        if (nBMap.getNbSubscriptionDate() != null) {
                            existingNBMap.setNbSubscriptionDate(nBMap.getNbSubscriptionDate());
                        }
                        if (nBMap.getNbSubscriptionLastDate() != null) {
                            existingNBMap.setNbSubscriptionLastDate(nBMap.getNbSubscriptionLastDate());
                        }
                        if (nBMap.getNbLastUpdated() != null) {
                            existingNBMap.setNbLastUpdated(nBMap.getNbLastUpdated());
                        }
                        if (nBMap.getNbLastUpdatedBy() != null) {
                            existingNBMap.setNbLastUpdatedBy(nBMap.getNbLastUpdatedBy());
                        }

                        return existingNBMap;
                    })
                    .flatMap(nBMapRepository::save);

                return result
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(res ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, res.getId().toString()))
                            .body(res)
                    );
            });
    }

    /**
     * {@code GET  /nb-maps} : get all the nBMaps.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nBMaps in body.
     */
    @GetMapping("/nb-maps")
    public Mono<List<NBMap>> getAllNBMaps() {
        log.debug("REST request to get all NBMaps");
        return nBMapRepository.findAll().collectList();
    }

    /**
     * {@code GET  /nb-maps} : get all the nBMaps as a stream.
     * @return the {@link Flux} of nBMaps.
     */
    @GetMapping(value = "/nb-maps", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<NBMap> getAllNBMapsAsStream() {
        log.debug("REST request to get all NBMaps as a stream");
        return nBMapRepository.findAll();
    }

    /**
     * {@code GET  /nb-maps/:id} : get the "id" nBMap.
     *
     * @param id the id of the nBMap to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nBMap, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nb-maps/{id}")
    public Mono<ResponseEntity<NBMap>> getNBMap(@PathVariable Long id) {
        log.debug("REST request to get NBMap : {}", id);
        Mono<NBMap> nBMap = nBMapRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(nBMap);
    }

    /**
     * {@code DELETE  /nb-maps/:id} : delete the "id" nBMap.
     *
     * @param id the id of the nBMap to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nb-maps/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> deleteNBMap(@PathVariable Long id) {
        log.debug("REST request to delete NBMap : {}", id);
        return nBMapRepository
            .deleteById(id)
            .map(result ->
                ResponseEntity
                    .noContent()
                    .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                    .build()
            );
    }
}
