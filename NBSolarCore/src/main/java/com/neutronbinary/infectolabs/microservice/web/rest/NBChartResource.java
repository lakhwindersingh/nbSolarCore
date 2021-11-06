package com.neutronbinary.infectolabs.microservice.web.rest;

import com.neutronbinary.infectolabs.microservice.domain.NBChart;
import com.neutronbinary.infectolabs.microservice.repository.NBChartRepository;
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
 * REST controller for managing {@link com.neutronbinary.infectolabs.microservice.domain.NBChart}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NBChartResource {

    private final Logger log = LoggerFactory.getLogger(NBChartResource.class);

    private static final String ENTITY_NAME = "nbSolarCoreNbChart";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NBChartRepository nBChartRepository;

    public NBChartResource(NBChartRepository nBChartRepository) {
        this.nBChartRepository = nBChartRepository;
    }

    /**
     * {@code POST  /nb-charts} : Create a new nBChart.
     *
     * @param nBChart the nBChart to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nBChart, or with status {@code 400 (Bad Request)} if the nBChart has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nb-charts")
    public ResponseEntity<NBChart> createNBChart(@RequestBody NBChart nBChart) throws URISyntaxException {
        log.debug("REST request to save NBChart : {}", nBChart);
        if (nBChart.getId() != null) {
            throw new BadRequestAlertException("A new nBChart cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NBChart result = nBChartRepository.save(nBChart);
        return ResponseEntity
            .created(new URI("/api/nb-charts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nb-charts/:id} : Updates an existing nBChart.
     *
     * @param id the id of the nBChart to save.
     * @param nBChart the nBChart to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nBChart,
     * or with status {@code 400 (Bad Request)} if the nBChart is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nBChart couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nb-charts/{id}")
    public ResponseEntity<NBChart> updateNBChart(@PathVariable(value = "id", required = false) final Long id, @RequestBody NBChart nBChart)
        throws URISyntaxException {
        log.debug("REST request to update NBChart : {}, {}", id, nBChart);
        if (nBChart.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nBChart.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nBChartRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NBChart result = nBChartRepository.save(nBChart);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nBChart.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nb-charts/:id} : Partial updates given fields of an existing nBChart, field will ignore if it is null
     *
     * @param id the id of the nBChart to save.
     * @param nBChart the nBChart to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nBChart,
     * or with status {@code 400 (Bad Request)} if the nBChart is not valid,
     * or with status {@code 404 (Not Found)} if the nBChart is not found,
     * or with status {@code 500 (Internal Server Error)} if the nBChart couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nb-charts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NBChart> partialUpdateNBChart(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NBChart nBChart
    ) throws URISyntaxException {
        log.debug("REST request to partial update NBChart partially : {}, {}", id, nBChart);
        if (nBChart.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nBChart.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nBChartRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NBChart> result = nBChartRepository
            .findById(nBChart.getId())
            .map(existingNBChart -> {
                if (nBChart.getNbChartID() != null) {
                    existingNBChart.setNbChartID(nBChart.getNbChartID());
                }
                if (nBChart.getNbChartTitle() != null) {
                    existingNBChart.setNbChartTitle(nBChart.getNbChartTitle());
                }
                if (nBChart.getNbChartType() != null) {
                    existingNBChart.setNbChartType(nBChart.getNbChartType());
                }
                if (nBChart.getNbChartParams() != null) {
                    existingNBChart.setNbChartParams(nBChart.getNbChartParams());
                }
                if (nBChart.getNbLastUpdated() != null) {
                    existingNBChart.setNbLastUpdated(nBChart.getNbLastUpdated());
                }
                if (nBChart.getNbLastUpdatedBy() != null) {
                    existingNBChart.setNbLastUpdatedBy(nBChart.getNbLastUpdatedBy());
                }

                return existingNBChart;
            })
            .map(nBChartRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nBChart.getId().toString())
        );
    }

    /**
     * {@code GET  /nb-charts} : get all the nBCharts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nBCharts in body.
     */
    @GetMapping("/nb-charts")
    public List<NBChart> getAllNBCharts() {
        log.debug("REST request to get all NBCharts");
        return nBChartRepository.findAll();
    }

    /**
     * {@code GET  /nb-charts/:id} : get the "id" nBChart.
     *
     * @param id the id of the nBChart to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nBChart, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nb-charts/{id}")
    public ResponseEntity<NBChart> getNBChart(@PathVariable Long id) {
        log.debug("REST request to get NBChart : {}", id);
        Optional<NBChart> nBChart = nBChartRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(nBChart);
    }

    /**
     * {@code DELETE  /nb-charts/:id} : delete the "id" nBChart.
     *
     * @param id the id of the nBChart to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nb-charts/{id}")
    public ResponseEntity<Void> deleteNBChart(@PathVariable Long id) {
        log.debug("REST request to delete NBChart : {}", id);
        nBChartRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
