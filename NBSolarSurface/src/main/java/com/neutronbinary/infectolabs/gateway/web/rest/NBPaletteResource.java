package com.neutronbinary.infectolabs.gateway.web.rest;

import com.neutronbinary.infectolabs.gateway.domain.NBPalette;
import com.neutronbinary.infectolabs.gateway.repository.NBPaletteRepository;
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
 * REST controller for managing {@link com.neutronbinary.infectolabs.gateway.domain.NBPalette}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NBPaletteResource {

    private final Logger log = LoggerFactory.getLogger(NBPaletteResource.class);

    private static final String ENTITY_NAME = "nBPalette";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NBPaletteRepository nBPaletteRepository;

    public NBPaletteResource(NBPaletteRepository nBPaletteRepository) {
        this.nBPaletteRepository = nBPaletteRepository;
    }

    /**
     * {@code POST  /nb-palettes} : Create a new nBPalette.
     *
     * @param nBPalette the nBPalette to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nBPalette, or with status {@code 400 (Bad Request)} if the nBPalette has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nb-palettes")
    public Mono<ResponseEntity<NBPalette>> createNBPalette(@RequestBody NBPalette nBPalette) throws URISyntaxException {
        log.debug("REST request to save NBPalette : {}", nBPalette);
        if (nBPalette.getId() != null) {
            throw new BadRequestAlertException("A new nBPalette cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return nBPaletteRepository
            .save(nBPalette)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/nb-palettes/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /nb-palettes/:id} : Updates an existing nBPalette.
     *
     * @param id the id of the nBPalette to save.
     * @param nBPalette the nBPalette to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nBPalette,
     * or with status {@code 400 (Bad Request)} if the nBPalette is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nBPalette couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nb-palettes/{id}")
    public Mono<ResponseEntity<NBPalette>> updateNBPalette(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NBPalette nBPalette
    ) throws URISyntaxException {
        log.debug("REST request to update NBPalette : {}, {}", id, nBPalette);
        if (nBPalette.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nBPalette.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return nBPaletteRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return nBPaletteRepository
                    .save(nBPalette)
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
     * {@code PATCH  /nb-palettes/:id} : Partial updates given fields of an existing nBPalette, field will ignore if it is null
     *
     * @param id the id of the nBPalette to save.
     * @param nBPalette the nBPalette to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nBPalette,
     * or with status {@code 400 (Bad Request)} if the nBPalette is not valid,
     * or with status {@code 404 (Not Found)} if the nBPalette is not found,
     * or with status {@code 500 (Internal Server Error)} if the nBPalette couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nb-palettes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<NBPalette>> partialUpdateNBPalette(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NBPalette nBPalette
    ) throws URISyntaxException {
        log.debug("REST request to partial update NBPalette partially : {}, {}", id, nBPalette);
        if (nBPalette.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nBPalette.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return nBPaletteRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<NBPalette> result = nBPaletteRepository
                    .findById(nBPalette.getId())
                    .map(existingNBPalette -> {
                        if (nBPalette.getNbPaletteID() != null) {
                            existingNBPalette.setNbPaletteID(nBPalette.getNbPaletteID());
                        }
                        if (nBPalette.getNbPaletteTitle() != null) {
                            existingNBPalette.setNbPaletteTitle(nBPalette.getNbPaletteTitle());
                        }
                        if (nBPalette.getNbPaletteType() != null) {
                            existingNBPalette.setNbPaletteType(nBPalette.getNbPaletteType());
                        }
                        if (nBPalette.getNbPaletteColors() != null) {
                            existingNBPalette.setNbPaletteColors(nBPalette.getNbPaletteColors());
                        }
                        if (nBPalette.getNbLastUpdated() != null) {
                            existingNBPalette.setNbLastUpdated(nBPalette.getNbLastUpdated());
                        }
                        if (nBPalette.getNbLastUpdatedBy() != null) {
                            existingNBPalette.setNbLastUpdatedBy(nBPalette.getNbLastUpdatedBy());
                        }

                        return existingNBPalette;
                    })
                    .flatMap(nBPaletteRepository::save);

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
     * {@code GET  /nb-palettes} : get all the nBPalettes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nBPalettes in body.
     */
    @GetMapping("/nb-palettes")
    public Mono<List<NBPalette>> getAllNBPalettes() {
        log.debug("REST request to get all NBPalettes");
        return nBPaletteRepository.findAll().collectList();
    }

    /**
     * {@code GET  /nb-palettes} : get all the nBPalettes as a stream.
     * @return the {@link Flux} of nBPalettes.
     */
    @GetMapping(value = "/nb-palettes", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<NBPalette> getAllNBPalettesAsStream() {
        log.debug("REST request to get all NBPalettes as a stream");
        return nBPaletteRepository.findAll();
    }

    /**
     * {@code GET  /nb-palettes/:id} : get the "id" nBPalette.
     *
     * @param id the id of the nBPalette to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nBPalette, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nb-palettes/{id}")
    public Mono<ResponseEntity<NBPalette>> getNBPalette(@PathVariable Long id) {
        log.debug("REST request to get NBPalette : {}", id);
        Mono<NBPalette> nBPalette = nBPaletteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(nBPalette);
    }

    /**
     * {@code DELETE  /nb-palettes/:id} : delete the "id" nBPalette.
     *
     * @param id the id of the nBPalette to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nb-palettes/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> deleteNBPalette(@PathVariable Long id) {
        log.debug("REST request to delete NBPalette : {}", id);
        return nBPaletteRepository
            .deleteById(id)
            .map(result ->
                ResponseEntity
                    .noContent()
                    .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                    .build()
            );
    }
}
