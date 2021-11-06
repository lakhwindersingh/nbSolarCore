package com.neutronbinary.infectolabs.microservice.web.rest;

import com.neutronbinary.infectolabs.microservice.domain.NBMapComponents;
import com.neutronbinary.infectolabs.microservice.repository.NBMapComponentsRepository;
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
 * REST controller for managing {@link com.neutronbinary.infectolabs.microservice.domain.NBMapComponents}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NBMapComponentsResource {

    private final Logger log = LoggerFactory.getLogger(NBMapComponentsResource.class);

    private static final String ENTITY_NAME = "nbSolarCoreNbMapComponents";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NBMapComponentsRepository nBMapComponentsRepository;

    public NBMapComponentsResource(NBMapComponentsRepository nBMapComponentsRepository) {
        this.nBMapComponentsRepository = nBMapComponentsRepository;
    }

    /**
     * {@code POST  /nb-map-components} : Create a new nBMapComponents.
     *
     * @param nBMapComponents the nBMapComponents to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nBMapComponents, or with status {@code 400 (Bad Request)} if the nBMapComponents has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nb-map-components")
    public ResponseEntity<NBMapComponents> createNBMapComponents(@RequestBody NBMapComponents nBMapComponents) throws URISyntaxException {
        log.debug("REST request to save NBMapComponents : {}", nBMapComponents);
        if (nBMapComponents.getId() != null) {
            throw new BadRequestAlertException("A new nBMapComponents cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NBMapComponents result = nBMapComponentsRepository.save(nBMapComponents);
        return ResponseEntity
            .created(new URI("/api/nb-map-components/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nb-map-components/:id} : Updates an existing nBMapComponents.
     *
     * @param id the id of the nBMapComponents to save.
     * @param nBMapComponents the nBMapComponents to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nBMapComponents,
     * or with status {@code 400 (Bad Request)} if the nBMapComponents is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nBMapComponents couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nb-map-components/{id}")
    public ResponseEntity<NBMapComponents> updateNBMapComponents(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NBMapComponents nBMapComponents
    ) throws URISyntaxException {
        log.debug("REST request to update NBMapComponents : {}, {}", id, nBMapComponents);
        if (nBMapComponents.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nBMapComponents.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nBMapComponentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NBMapComponents result = nBMapComponentsRepository.save(nBMapComponents);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nBMapComponents.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nb-map-components/:id} : Partial updates given fields of an existing nBMapComponents, field will ignore if it is null
     *
     * @param id the id of the nBMapComponents to save.
     * @param nBMapComponents the nBMapComponents to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nBMapComponents,
     * or with status {@code 400 (Bad Request)} if the nBMapComponents is not valid,
     * or with status {@code 404 (Not Found)} if the nBMapComponents is not found,
     * or with status {@code 500 (Internal Server Error)} if the nBMapComponents couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nb-map-components/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NBMapComponents> partialUpdateNBMapComponents(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NBMapComponents nBMapComponents
    ) throws URISyntaxException {
        log.debug("REST request to partial update NBMapComponents partially : {}, {}", id, nBMapComponents);
        if (nBMapComponents.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nBMapComponents.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nBMapComponentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NBMapComponents> result = nBMapComponentsRepository
            .findById(nBMapComponents.getId())
            .map(existingNBMapComponents -> {
                if (nBMapComponents.getNbIDFK() != null) {
                    existingNBMapComponents.setNbIDFK(nBMapComponents.getNbIDFK());
                }
                if (nBMapComponents.getNbComponentID() != null) {
                    existingNBMapComponents.setNbComponentID(nBMapComponents.getNbComponentID());
                }
                if (nBMapComponents.getNbComponentType() != null) {
                    existingNBMapComponents.setNbComponentType(nBMapComponents.getNbComponentType());
                }
                if (nBMapComponents.getNbComponentValue() != null) {
                    existingNBMapComponents.setNbComponentValue(nBMapComponents.getNbComponentValue());
                }
                if (nBMapComponents.getNbDefault() != null) {
                    existingNBMapComponents.setNbDefault(nBMapComponents.getNbDefault());
                }
                if (nBMapComponents.getNbLastUpdated() != null) {
                    existingNBMapComponents.setNbLastUpdated(nBMapComponents.getNbLastUpdated());
                }
                if (nBMapComponents.getNbLastUpdatedBy() != null) {
                    existingNBMapComponents.setNbLastUpdatedBy(nBMapComponents.getNbLastUpdatedBy());
                }

                return existingNBMapComponents;
            })
            .map(nBMapComponentsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nBMapComponents.getId().toString())
        );
    }

    /**
     * {@code GET  /nb-map-components} : get all the nBMapComponents.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nBMapComponents in body.
     */
    @GetMapping("/nb-map-components")
    public List<NBMapComponents> getAllNBMapComponents() {
        log.debug("REST request to get all NBMapComponents");
        return nBMapComponentsRepository.findAll();
    }

    /**
     * {@code GET  /nb-map-components/:id} : get the "id" nBMapComponents.
     *
     * @param id the id of the nBMapComponents to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nBMapComponents, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nb-map-components/{id}")
    public ResponseEntity<NBMapComponents> getNBMapComponents(@PathVariable Long id) {
        log.debug("REST request to get NBMapComponents : {}", id);
        Optional<NBMapComponents> nBMapComponents = nBMapComponentsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(nBMapComponents);
    }

    /**
     * {@code DELETE  /nb-map-components/:id} : delete the "id" nBMapComponents.
     *
     * @param id the id of the nBMapComponents to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nb-map-components/{id}")
    public ResponseEntity<Void> deleteNBMapComponents(@PathVariable Long id) {
        log.debug("REST request to delete NBMapComponents : {}", id);
        nBMapComponentsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
