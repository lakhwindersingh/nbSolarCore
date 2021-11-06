package com.neutronbinary.infectolabs.microservice.web.rest;

import com.neutronbinary.infectolabs.microservice.domain.NBUser;
import com.neutronbinary.infectolabs.microservice.repository.NBUserRepository;
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
 * REST controller for managing {@link com.neutronbinary.infectolabs.microservice.domain.NBUser}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NBUserResource {

    private final Logger log = LoggerFactory.getLogger(NBUserResource.class);

    private static final String ENTITY_NAME = "nbSolarCoreNbUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NBUserRepository nBUserRepository;

    public NBUserResource(NBUserRepository nBUserRepository) {
        this.nBUserRepository = nBUserRepository;
    }

    /**
     * {@code POST  /nb-users} : Create a new nBUser.
     *
     * @param nBUser the nBUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nBUser, or with status {@code 400 (Bad Request)} if the nBUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nb-users")
    public ResponseEntity<NBUser> createNBUser(@RequestBody NBUser nBUser) throws URISyntaxException {
        log.debug("REST request to save NBUser : {}", nBUser);
        if (nBUser.getId() != null) {
            throw new BadRequestAlertException("A new nBUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NBUser result = nBUserRepository.save(nBUser);
        return ResponseEntity
            .created(new URI("/api/nb-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nb-users/:id} : Updates an existing nBUser.
     *
     * @param id the id of the nBUser to save.
     * @param nBUser the nBUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nBUser,
     * or with status {@code 400 (Bad Request)} if the nBUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nBUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nb-users/{id}")
    public ResponseEntity<NBUser> updateNBUser(@PathVariable(value = "id", required = false) final Long id, @RequestBody NBUser nBUser)
        throws URISyntaxException {
        log.debug("REST request to update NBUser : {}, {}", id, nBUser);
        if (nBUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nBUser.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nBUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NBUser result = nBUserRepository.save(nBUser);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nBUser.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nb-users/:id} : Partial updates given fields of an existing nBUser, field will ignore if it is null
     *
     * @param id the id of the nBUser to save.
     * @param nBUser the nBUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nBUser,
     * or with status {@code 400 (Bad Request)} if the nBUser is not valid,
     * or with status {@code 404 (Not Found)} if the nBUser is not found,
     * or with status {@code 500 (Internal Server Error)} if the nBUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nb-users/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NBUser> partialUpdateNBUser(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NBUser nBUser
    ) throws URISyntaxException {
        log.debug("REST request to partial update NBUser partially : {}, {}", id, nBUser);
        if (nBUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nBUser.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nBUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NBUser> result = nBUserRepository
            .findById(nBUser.getId())
            .map(existingNBUser -> {
                if (nBUser.getNbUserID() != null) {
                    existingNBUser.setNbUserID(nBUser.getNbUserID());
                }
                if (nBUser.getNbAuthType() != null) {
                    existingNBUser.setNbAuthType(nBUser.getNbAuthType());
                }
                if (nBUser.getNbPasswordHash() != null) {
                    existingNBUser.setNbPasswordHash(nBUser.getNbPasswordHash());
                }
                if (nBUser.getNbFirstName() != null) {
                    existingNBUser.setNbFirstName(nBUser.getNbFirstName());
                }
                if (nBUser.getNbLastName() != null) {
                    existingNBUser.setNbLastName(nBUser.getNbLastName());
                }
                if (nBUser.getNbAddress() != null) {
                    existingNBUser.setNbAddress(nBUser.getNbAddress());
                }
                if (nBUser.getNbEmailId() != null) {
                    existingNBUser.setNbEmailId(nBUser.getNbEmailId());
                }
                if (nBUser.getNbPhone() != null) {
                    existingNBUser.setNbPhone(nBUser.getNbPhone());
                }
                if (nBUser.getNbIsActive() != null) {
                    existingNBUser.setNbIsActive(nBUser.getNbIsActive());
                }
                if (nBUser.getNbIsSuspended() != null) {
                    existingNBUser.setNbIsSuspended(nBUser.getNbIsSuspended());
                }
                if (nBUser.getNbIsBanished() != null) {
                    existingNBUser.setNbIsBanished(nBUser.getNbIsBanished());
                }
                if (nBUser.getNbLastUpdated() != null) {
                    existingNBUser.setNbLastUpdated(nBUser.getNbLastUpdated());
                }
                if (nBUser.getNbLastUpdatedBy() != null) {
                    existingNBUser.setNbLastUpdatedBy(nBUser.getNbLastUpdatedBy());
                }

                return existingNBUser;
            })
            .map(nBUserRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nBUser.getId().toString())
        );
    }

    /**
     * {@code GET  /nb-users} : get all the nBUsers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nBUsers in body.
     */
    @GetMapping("/nb-users")
    public List<NBUser> getAllNBUsers() {
        log.debug("REST request to get all NBUsers");
        return nBUserRepository.findAll();
    }

    /**
     * {@code GET  /nb-users/:id} : get the "id" nBUser.
     *
     * @param id the id of the nBUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nBUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nb-users/{id}")
    public ResponseEntity<NBUser> getNBUser(@PathVariable Long id) {
        log.debug("REST request to get NBUser : {}", id);
        Optional<NBUser> nBUser = nBUserRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(nBUser);
    }

    /**
     * {@code DELETE  /nb-users/:id} : delete the "id" nBUser.
     *
     * @param id the id of the nBUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nb-users/{id}")
    public ResponseEntity<Void> deleteNBUser(@PathVariable Long id) {
        log.debug("REST request to delete NBUser : {}", id);
        nBUserRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
