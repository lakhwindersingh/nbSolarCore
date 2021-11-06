package com.neutronbinary.infectolabs.microservice.web.rest;

import com.neutronbinary.infectolabs.microservice.domain.NBMapAttributes;
import com.neutronbinary.infectolabs.microservice.repository.NBMapAttributesRepository;
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
 * REST controller for managing {@link com.neutronbinary.infectolabs.microservice.domain.NBMapAttributes}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NBMapAttributesResource {

    private final Logger log = LoggerFactory.getLogger(NBMapAttributesResource.class);

    private static final String ENTITY_NAME = "nbSolarCoreNbMapAttributes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NBMapAttributesRepository nBMapAttributesRepository;

    public NBMapAttributesResource(NBMapAttributesRepository nBMapAttributesRepository) {
        this.nBMapAttributesRepository = nBMapAttributesRepository;
    }

    /**
     * {@code POST  /nb-map-attributes} : Create a new nBMapAttributes.
     *
     * @param nBMapAttributes the nBMapAttributes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nBMapAttributes, or with status {@code 400 (Bad Request)} if the nBMapAttributes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nb-map-attributes")
    public ResponseEntity<NBMapAttributes> createNBMapAttributes(@RequestBody NBMapAttributes nBMapAttributes) throws URISyntaxException {
        log.debug("REST request to save NBMapAttributes : {}", nBMapAttributes);
        if (nBMapAttributes.getId() != null) {
            throw new BadRequestAlertException("A new nBMapAttributes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NBMapAttributes result = nBMapAttributesRepository.save(nBMapAttributes);
        return ResponseEntity
            .created(new URI("/api/nb-map-attributes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nb-map-attributes/:id} : Updates an existing nBMapAttributes.
     *
     * @param id the id of the nBMapAttributes to save.
     * @param nBMapAttributes the nBMapAttributes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nBMapAttributes,
     * or with status {@code 400 (Bad Request)} if the nBMapAttributes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nBMapAttributes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nb-map-attributes/{id}")
    public ResponseEntity<NBMapAttributes> updateNBMapAttributes(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NBMapAttributes nBMapAttributes
    ) throws URISyntaxException {
        log.debug("REST request to update NBMapAttributes : {}, {}", id, nBMapAttributes);
        if (nBMapAttributes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nBMapAttributes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nBMapAttributesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NBMapAttributes result = nBMapAttributesRepository.save(nBMapAttributes);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nBMapAttributes.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nb-map-attributes/:id} : Partial updates given fields of an existing nBMapAttributes, field will ignore if it is null
     *
     * @param id the id of the nBMapAttributes to save.
     * @param nBMapAttributes the nBMapAttributes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nBMapAttributes,
     * or with status {@code 400 (Bad Request)} if the nBMapAttributes is not valid,
     * or with status {@code 404 (Not Found)} if the nBMapAttributes is not found,
     * or with status {@code 500 (Internal Server Error)} if the nBMapAttributes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nb-map-attributes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NBMapAttributes> partialUpdateNBMapAttributes(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NBMapAttributes nBMapAttributes
    ) throws URISyntaxException {
        log.debug("REST request to partial update NBMapAttributes partially : {}, {}", id, nBMapAttributes);
        if (nBMapAttributes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nBMapAttributes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nBMapAttributesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NBMapAttributes> result = nBMapAttributesRepository
            .findById(nBMapAttributes.getId())
            .map(existingNBMapAttributes -> {
                if (nBMapAttributes.getNbIDFK() != null) {
                    existingNBMapAttributes.setNbIDFK(nBMapAttributes.getNbIDFK());
                }
                if (nBMapAttributes.getNbTitle() != null) {
                    existingNBMapAttributes.setNbTitle(nBMapAttributes.getNbTitle());
                }
                if (nBMapAttributes.getNbTitleLocation() != null) {
                    existingNBMapAttributes.setNbTitleLocation(nBMapAttributes.getNbTitleLocation());
                }
                if (nBMapAttributes.getNbPaletteIDFK() != null) {
                    existingNBMapAttributes.setNbPaletteIDFK(nBMapAttributes.getNbPaletteIDFK());
                }
                if (nBMapAttributes.getNbChartIDFK() != null) {
                    existingNBMapAttributes.setNbChartIDFK(nBMapAttributes.getNbChartIDFK());
                }
                if (nBMapAttributes.getNbChartType() != null) {
                    existingNBMapAttributes.setNbChartType(nBMapAttributes.getNbChartType());
                }
                if (nBMapAttributes.getNbLastUpdated() != null) {
                    existingNBMapAttributes.setNbLastUpdated(nBMapAttributes.getNbLastUpdated());
                }
                if (nBMapAttributes.getNbLastUpdatedBy() != null) {
                    existingNBMapAttributes.setNbLastUpdatedBy(nBMapAttributes.getNbLastUpdatedBy());
                }

                return existingNBMapAttributes;
            })
            .map(nBMapAttributesRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nBMapAttributes.getId().toString())
        );
    }

    /**
     * {@code GET  /nb-map-attributes} : get all the nBMapAttributes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nBMapAttributes in body.
     */
    @GetMapping("/nb-map-attributes")
    public List<NBMapAttributes> getAllNBMapAttributes() {
        log.debug("REST request to get all NBMapAttributes");
        return nBMapAttributesRepository.findAll();
    }

    /**
     * {@code GET  /nb-map-attributes/:id} : get the "id" nBMapAttributes.
     *
     * @param id the id of the nBMapAttributes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nBMapAttributes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nb-map-attributes/{id}")
    public ResponseEntity<NBMapAttributes> getNBMapAttributes(@PathVariable Long id) {
        log.debug("REST request to get NBMapAttributes : {}", id);
        Optional<NBMapAttributes> nBMapAttributes = nBMapAttributesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(nBMapAttributes);
    }

    /**
     * {@code DELETE  /nb-map-attributes/:id} : delete the "id" nBMapAttributes.
     *
     * @param id the id of the nBMapAttributes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nb-map-attributes/{id}")
    public ResponseEntity<Void> deleteNBMapAttributes(@PathVariable Long id) {
        log.debug("REST request to delete NBMapAttributes : {}", id);
        nBMapAttributesRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
