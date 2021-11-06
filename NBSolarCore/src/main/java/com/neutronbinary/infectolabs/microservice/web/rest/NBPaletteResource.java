package com.neutronbinary.infectolabs.microservice.web.rest;

import com.neutronbinary.infectolabs.microservice.domain.NBPalette;
import com.neutronbinary.infectolabs.microservice.repository.NBPaletteRepository;
import com.neutronbinary.infectolabs.microservice.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.neutronbinary.infectolabs.microservice.domain.NBPalette}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NBPaletteResource {

    private final Logger log = LoggerFactory.getLogger(NBPaletteResource.class);

    private static final String ENTITY_NAME = "nbSolarCoreNbPalette";

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
    public ResponseEntity<NBPalette> createNBPalette(@RequestBody NBPalette nBPalette) throws URISyntaxException {
        log.debug("REST request to save NBPalette : {}", nBPalette);
        if (nBPalette.getId() != null) {
            throw new BadRequestAlertException("A new nBPalette cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NBPalette result = nBPaletteRepository.save(nBPalette);
        return ResponseEntity
            .created(new URI("/api/nb-palettes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
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
    public ResponseEntity<NBPalette> updateNBPalette(
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

        if (!nBPaletteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NBPalette result = nBPaletteRepository.save(nBPalette);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nBPalette.getId().toString()))
            .body(result);
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
    public ResponseEntity<NBPalette> partialUpdateNBPalette(
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

        if (!nBPaletteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NBPalette> result = nBPaletteRepository
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
            .map(nBPaletteRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nBPalette.getId().toString())
        );
    }

    /**
     * {@code GET  /nb-palettes} : get all the nBPalettes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nBPalettes in body.
     */
    @GetMapping("/nb-palettes")
    public List<NBPalette> getAllNBPalettes() {
        log.debug("REST request to get all NBPalettes");
        return nBPaletteRepository.findAll();
    }

    /**
     * {@code GET  /nb-palettes/:id} : get the "id" nBPalette.
     *
     * @param id the id of the nBPalette to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nBPalette, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nb-palettes/{id}")
    public ResponseEntity<NBPalette> getNBPalette(@PathVariable Long id) {
        log.debug("REST request to get NBPalette : {}", id);
        Optional<NBPalette> nBPalette = nBPaletteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(nBPalette);
    }

    /**
     * {@code DELETE  /nb-palettes/:id} : delete the "id" nBPalette.
     *
     * @param id the id of the nBPalette to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nb-palettes/{id}")
    public ResponseEntity<Void> deleteNBPalette(@PathVariable Long id) {
        log.debug("REST request to delete NBPalette : {}", id);
        nBPaletteRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
