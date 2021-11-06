package com.neutronbinary.infectolabs.microservice.web.rest;

import com.neutronbinary.infectolabs.microservice.domain.NBMapComponentAttributes;
import com.neutronbinary.infectolabs.microservice.repository.NBMapComponentAttributesRepository;
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
 * REST controller for managing {@link com.neutronbinary.infectolabs.microservice.domain.NBMapComponentAttributes}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NBMapComponentAttributesResource {

    private final Logger log = LoggerFactory.getLogger(NBMapComponentAttributesResource.class);

    private static final String ENTITY_NAME = "nbSolarCoreNbMapComponentAttributes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NBMapComponentAttributesRepository nBMapComponentAttributesRepository;

    public NBMapComponentAttributesResource(NBMapComponentAttributesRepository nBMapComponentAttributesRepository) {
        this.nBMapComponentAttributesRepository = nBMapComponentAttributesRepository;
    }

    /**
     * {@code POST  /nb-map-component-attributes} : Create a new nBMapComponentAttributes.
     *
     * @param nBMapComponentAttributes the nBMapComponentAttributes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nBMapComponentAttributes, or with status {@code 400 (Bad Request)} if the nBMapComponentAttributes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nb-map-component-attributes")
    public ResponseEntity<NBMapComponentAttributes> createNBMapComponentAttributes(
        @RequestBody NBMapComponentAttributes nBMapComponentAttributes
    ) throws URISyntaxException {
        log.debug("REST request to save NBMapComponentAttributes : {}", nBMapComponentAttributes);
        if (nBMapComponentAttributes.getId() != null) {
            throw new BadRequestAlertException("A new nBMapComponentAttributes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NBMapComponentAttributes result = nBMapComponentAttributesRepository.save(nBMapComponentAttributes);
        return ResponseEntity
            .created(new URI("/api/nb-map-component-attributes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nb-map-component-attributes/:id} : Updates an existing nBMapComponentAttributes.
     *
     * @param id the id of the nBMapComponentAttributes to save.
     * @param nBMapComponentAttributes the nBMapComponentAttributes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nBMapComponentAttributes,
     * or with status {@code 400 (Bad Request)} if the nBMapComponentAttributes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nBMapComponentAttributes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nb-map-component-attributes/{id}")
    public ResponseEntity<NBMapComponentAttributes> updateNBMapComponentAttributes(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NBMapComponentAttributes nBMapComponentAttributes
    ) throws URISyntaxException {
        log.debug("REST request to update NBMapComponentAttributes : {}, {}", id, nBMapComponentAttributes);
        if (nBMapComponentAttributes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nBMapComponentAttributes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nBMapComponentAttributesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NBMapComponentAttributes result = nBMapComponentAttributesRepository.save(nBMapComponentAttributes);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nBMapComponentAttributes.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nb-map-component-attributes/:id} : Partial updates given fields of an existing nBMapComponentAttributes, field will ignore if it is null
     *
     * @param id the id of the nBMapComponentAttributes to save.
     * @param nBMapComponentAttributes the nBMapComponentAttributes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nBMapComponentAttributes,
     * or with status {@code 400 (Bad Request)} if the nBMapComponentAttributes is not valid,
     * or with status {@code 404 (Not Found)} if the nBMapComponentAttributes is not found,
     * or with status {@code 500 (Internal Server Error)} if the nBMapComponentAttributes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nb-map-component-attributes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NBMapComponentAttributes> partialUpdateNBMapComponentAttributes(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NBMapComponentAttributes nBMapComponentAttributes
    ) throws URISyntaxException {
        log.debug("REST request to partial update NBMapComponentAttributes partially : {}, {}", id, nBMapComponentAttributes);
        if (nBMapComponentAttributes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nBMapComponentAttributes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nBMapComponentAttributesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NBMapComponentAttributes> result = nBMapComponentAttributesRepository
            .findById(nBMapComponentAttributes.getId())
            .map(existingNBMapComponentAttributes -> {
                if (nBMapComponentAttributes.getNbComponentIDFK() != null) {
                    existingNBMapComponentAttributes.setNbComponentIDFK(nBMapComponentAttributes.getNbComponentIDFK());
                }
                if (nBMapComponentAttributes.getNbComponentName() != null) {
                    existingNBMapComponentAttributes.setNbComponentName(nBMapComponentAttributes.getNbComponentName());
                }
                if (nBMapComponentAttributes.getNbLastUpdated() != null) {
                    existingNBMapComponentAttributes.setNbLastUpdated(nBMapComponentAttributes.getNbLastUpdated());
                }
                if (nBMapComponentAttributes.getNbLastUpdatedBy() != null) {
                    existingNBMapComponentAttributes.setNbLastUpdatedBy(nBMapComponentAttributes.getNbLastUpdatedBy());
                }

                return existingNBMapComponentAttributes;
            })
            .map(nBMapComponentAttributesRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nBMapComponentAttributes.getId().toString())
        );
    }

    /**
     * {@code GET  /nb-map-component-attributes} : get all the nBMapComponentAttributes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nBMapComponentAttributes in body.
     */
    @GetMapping("/nb-map-component-attributes")
    public List<NBMapComponentAttributes> getAllNBMapComponentAttributes() {
        log.debug("REST request to get all NBMapComponentAttributes");
        return nBMapComponentAttributesRepository.findAll();
    }

    /**
     * {@code GET  /nb-map-component-attributes/:id} : get the "id" nBMapComponentAttributes.
     *
     * @param id the id of the nBMapComponentAttributes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nBMapComponentAttributes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nb-map-component-attributes/{id}")
    public ResponseEntity<NBMapComponentAttributes> getNBMapComponentAttributes(@PathVariable Long id) {
        log.debug("REST request to get NBMapComponentAttributes : {}", id);
        Optional<NBMapComponentAttributes> nBMapComponentAttributes = nBMapComponentAttributesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(nBMapComponentAttributes);
    }

    /**
     * {@code DELETE  /nb-map-component-attributes/:id} : delete the "id" nBMapComponentAttributes.
     *
     * @param id the id of the nBMapComponentAttributes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nb-map-component-attributes/{id}")
    public ResponseEntity<Void> deleteNBMapComponentAttributes(@PathVariable Long id) {
        log.debug("REST request to delete NBMapComponentAttributes : {}", id);
        nBMapComponentAttributesRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
