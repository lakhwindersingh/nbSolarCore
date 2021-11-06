package com.neutronbinary.infectolabs.gateway.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.neutronbinary.infectolabs.gateway.IntegrationTest;
import com.neutronbinary.infectolabs.gateway.domain.NBChart;
import com.neutronbinary.infectolabs.gateway.repository.NBChartRepository;
import com.neutronbinary.infectolabs.gateway.service.EntityManager;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link NBChartResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient
@WithMockUser
class NBChartResourceIT {

    private static final String DEFAULT_NB_CHART_ID = "AAAAAAAAAA";
    private static final String UPDATED_NB_CHART_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NB_CHART_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_NB_CHART_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_NB_CHART_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_NB_CHART_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_NB_CHART_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_NB_CHART_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_NB_LAST_UPDATED = "AAAAAAAAAA";
    private static final String UPDATED_NB_LAST_UPDATED = "BBBBBBBBBB";

    private static final String DEFAULT_NB_LAST_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_NB_LAST_UPDATED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/nb-charts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NBChartRepository nBChartRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private NBChart nBChart;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NBChart createEntity(EntityManager em) {
        NBChart nBChart = new NBChart()
            .nbChartID(DEFAULT_NB_CHART_ID)
            .nbChartTitle(DEFAULT_NB_CHART_TITLE)
            .nbChartType(DEFAULT_NB_CHART_TYPE)
            .nbChartParams(DEFAULT_NB_CHART_PARAMS)
            .nbLastUpdated(DEFAULT_NB_LAST_UPDATED)
            .nbLastUpdatedBy(DEFAULT_NB_LAST_UPDATED_BY);
        return nBChart;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NBChart createUpdatedEntity(EntityManager em) {
        NBChart nBChart = new NBChart()
            .nbChartID(UPDATED_NB_CHART_ID)
            .nbChartTitle(UPDATED_NB_CHART_TITLE)
            .nbChartType(UPDATED_NB_CHART_TYPE)
            .nbChartParams(UPDATED_NB_CHART_PARAMS)
            .nbLastUpdated(UPDATED_NB_LAST_UPDATED)
            .nbLastUpdatedBy(UPDATED_NB_LAST_UPDATED_BY);
        return nBChart;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(NBChart.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
    }

    @AfterEach
    public void cleanup() {
        deleteEntities(em);
    }

    @BeforeEach
    public void initTest() {
        deleteEntities(em);
        nBChart = createEntity(em);
    }

    @Test
    void createNBChart() throws Exception {
        int databaseSizeBeforeCreate = nBChartRepository.findAll().collectList().block().size();
        // Create the NBChart
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(nBChart))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the NBChart in the database
        List<NBChart> nBChartList = nBChartRepository.findAll().collectList().block();
        assertThat(nBChartList).hasSize(databaseSizeBeforeCreate + 1);
        NBChart testNBChart = nBChartList.get(nBChartList.size() - 1);
        assertThat(testNBChart.getNbChartID()).isEqualTo(DEFAULT_NB_CHART_ID);
        assertThat(testNBChart.getNbChartTitle()).isEqualTo(DEFAULT_NB_CHART_TITLE);
        assertThat(testNBChart.getNbChartType()).isEqualTo(DEFAULT_NB_CHART_TYPE);
        assertThat(testNBChart.getNbChartParams()).isEqualTo(DEFAULT_NB_CHART_PARAMS);
        assertThat(testNBChart.getNbLastUpdated()).isEqualTo(DEFAULT_NB_LAST_UPDATED);
        assertThat(testNBChart.getNbLastUpdatedBy()).isEqualTo(DEFAULT_NB_LAST_UPDATED_BY);
    }

    @Test
    void createNBChartWithExistingId() throws Exception {
        // Create the NBChart with an existing ID
        nBChart.setId(1L);

        int databaseSizeBeforeCreate = nBChartRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(nBChart))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the NBChart in the database
        List<NBChart> nBChartList = nBChartRepository.findAll().collectList().block();
        assertThat(nBChartList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllNBChartsAsStream() {
        // Initialize the database
        nBChartRepository.save(nBChart).block();

        List<NBChart> nBChartList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(NBChart.class)
            .getResponseBody()
            .filter(nBChart::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(nBChartList).isNotNull();
        assertThat(nBChartList).hasSize(1);
        NBChart testNBChart = nBChartList.get(0);
        assertThat(testNBChart.getNbChartID()).isEqualTo(DEFAULT_NB_CHART_ID);
        assertThat(testNBChart.getNbChartTitle()).isEqualTo(DEFAULT_NB_CHART_TITLE);
        assertThat(testNBChart.getNbChartType()).isEqualTo(DEFAULT_NB_CHART_TYPE);
        assertThat(testNBChart.getNbChartParams()).isEqualTo(DEFAULT_NB_CHART_PARAMS);
        assertThat(testNBChart.getNbLastUpdated()).isEqualTo(DEFAULT_NB_LAST_UPDATED);
        assertThat(testNBChart.getNbLastUpdatedBy()).isEqualTo(DEFAULT_NB_LAST_UPDATED_BY);
    }

    @Test
    void getAllNBCharts() {
        // Initialize the database
        nBChartRepository.save(nBChart).block();

        // Get all the nBChartList
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id")
            .value(hasItem(nBChart.getId().intValue()))
            .jsonPath("$.[*].nbChartID")
            .value(hasItem(DEFAULT_NB_CHART_ID))
            .jsonPath("$.[*].nbChartTitle")
            .value(hasItem(DEFAULT_NB_CHART_TITLE))
            .jsonPath("$.[*].nbChartType")
            .value(hasItem(DEFAULT_NB_CHART_TYPE))
            .jsonPath("$.[*].nbChartParams")
            .value(hasItem(DEFAULT_NB_CHART_PARAMS))
            .jsonPath("$.[*].nbLastUpdated")
            .value(hasItem(DEFAULT_NB_LAST_UPDATED))
            .jsonPath("$.[*].nbLastUpdatedBy")
            .value(hasItem(DEFAULT_NB_LAST_UPDATED_BY));
    }

    @Test
    void getNBChart() {
        // Initialize the database
        nBChartRepository.save(nBChart).block();

        // Get the nBChart
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, nBChart.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(nBChart.getId().intValue()))
            .jsonPath("$.nbChartID")
            .value(is(DEFAULT_NB_CHART_ID))
            .jsonPath("$.nbChartTitle")
            .value(is(DEFAULT_NB_CHART_TITLE))
            .jsonPath("$.nbChartType")
            .value(is(DEFAULT_NB_CHART_TYPE))
            .jsonPath("$.nbChartParams")
            .value(is(DEFAULT_NB_CHART_PARAMS))
            .jsonPath("$.nbLastUpdated")
            .value(is(DEFAULT_NB_LAST_UPDATED))
            .jsonPath("$.nbLastUpdatedBy")
            .value(is(DEFAULT_NB_LAST_UPDATED_BY));
    }

    @Test
    void getNonExistingNBChart() {
        // Get the nBChart
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putNewNBChart() throws Exception {
        // Initialize the database
        nBChartRepository.save(nBChart).block();

        int databaseSizeBeforeUpdate = nBChartRepository.findAll().collectList().block().size();

        // Update the nBChart
        NBChart updatedNBChart = nBChartRepository.findById(nBChart.getId()).block();
        updatedNBChart
            .nbChartID(UPDATED_NB_CHART_ID)
            .nbChartTitle(UPDATED_NB_CHART_TITLE)
            .nbChartType(UPDATED_NB_CHART_TYPE)
            .nbChartParams(UPDATED_NB_CHART_PARAMS)
            .nbLastUpdated(UPDATED_NB_LAST_UPDATED)
            .nbLastUpdatedBy(UPDATED_NB_LAST_UPDATED_BY);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedNBChart.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedNBChart))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the NBChart in the database
        List<NBChart> nBChartList = nBChartRepository.findAll().collectList().block();
        assertThat(nBChartList).hasSize(databaseSizeBeforeUpdate);
        NBChart testNBChart = nBChartList.get(nBChartList.size() - 1);
        assertThat(testNBChart.getNbChartID()).isEqualTo(UPDATED_NB_CHART_ID);
        assertThat(testNBChart.getNbChartTitle()).isEqualTo(UPDATED_NB_CHART_TITLE);
        assertThat(testNBChart.getNbChartType()).isEqualTo(UPDATED_NB_CHART_TYPE);
        assertThat(testNBChart.getNbChartParams()).isEqualTo(UPDATED_NB_CHART_PARAMS);
        assertThat(testNBChart.getNbLastUpdated()).isEqualTo(UPDATED_NB_LAST_UPDATED);
        assertThat(testNBChart.getNbLastUpdatedBy()).isEqualTo(UPDATED_NB_LAST_UPDATED_BY);
    }

    @Test
    void putNonExistingNBChart() throws Exception {
        int databaseSizeBeforeUpdate = nBChartRepository.findAll().collectList().block().size();
        nBChart.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, nBChart.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(nBChart))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the NBChart in the database
        List<NBChart> nBChartList = nBChartRepository.findAll().collectList().block();
        assertThat(nBChartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchNBChart() throws Exception {
        int databaseSizeBeforeUpdate = nBChartRepository.findAll().collectList().block().size();
        nBChart.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(nBChart))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the NBChart in the database
        List<NBChart> nBChartList = nBChartRepository.findAll().collectList().block();
        assertThat(nBChartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamNBChart() throws Exception {
        int databaseSizeBeforeUpdate = nBChartRepository.findAll().collectList().block().size();
        nBChart.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(nBChart))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the NBChart in the database
        List<NBChart> nBChartList = nBChartRepository.findAll().collectList().block();
        assertThat(nBChartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateNBChartWithPatch() throws Exception {
        // Initialize the database
        nBChartRepository.save(nBChart).block();

        int databaseSizeBeforeUpdate = nBChartRepository.findAll().collectList().block().size();

        // Update the nBChart using partial update
        NBChart partialUpdatedNBChart = new NBChart();
        partialUpdatedNBChart.setId(nBChart.getId());

        partialUpdatedNBChart.nbChartID(UPDATED_NB_CHART_ID).nbChartType(UPDATED_NB_CHART_TYPE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedNBChart.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedNBChart))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the NBChart in the database
        List<NBChart> nBChartList = nBChartRepository.findAll().collectList().block();
        assertThat(nBChartList).hasSize(databaseSizeBeforeUpdate);
        NBChart testNBChart = nBChartList.get(nBChartList.size() - 1);
        assertThat(testNBChart.getNbChartID()).isEqualTo(UPDATED_NB_CHART_ID);
        assertThat(testNBChart.getNbChartTitle()).isEqualTo(DEFAULT_NB_CHART_TITLE);
        assertThat(testNBChart.getNbChartType()).isEqualTo(UPDATED_NB_CHART_TYPE);
        assertThat(testNBChart.getNbChartParams()).isEqualTo(DEFAULT_NB_CHART_PARAMS);
        assertThat(testNBChart.getNbLastUpdated()).isEqualTo(DEFAULT_NB_LAST_UPDATED);
        assertThat(testNBChart.getNbLastUpdatedBy()).isEqualTo(DEFAULT_NB_LAST_UPDATED_BY);
    }

    @Test
    void fullUpdateNBChartWithPatch() throws Exception {
        // Initialize the database
        nBChartRepository.save(nBChart).block();

        int databaseSizeBeforeUpdate = nBChartRepository.findAll().collectList().block().size();

        // Update the nBChart using partial update
        NBChart partialUpdatedNBChart = new NBChart();
        partialUpdatedNBChart.setId(nBChart.getId());

        partialUpdatedNBChart
            .nbChartID(UPDATED_NB_CHART_ID)
            .nbChartTitle(UPDATED_NB_CHART_TITLE)
            .nbChartType(UPDATED_NB_CHART_TYPE)
            .nbChartParams(UPDATED_NB_CHART_PARAMS)
            .nbLastUpdated(UPDATED_NB_LAST_UPDATED)
            .nbLastUpdatedBy(UPDATED_NB_LAST_UPDATED_BY);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedNBChart.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedNBChart))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the NBChart in the database
        List<NBChart> nBChartList = nBChartRepository.findAll().collectList().block();
        assertThat(nBChartList).hasSize(databaseSizeBeforeUpdate);
        NBChart testNBChart = nBChartList.get(nBChartList.size() - 1);
        assertThat(testNBChart.getNbChartID()).isEqualTo(UPDATED_NB_CHART_ID);
        assertThat(testNBChart.getNbChartTitle()).isEqualTo(UPDATED_NB_CHART_TITLE);
        assertThat(testNBChart.getNbChartType()).isEqualTo(UPDATED_NB_CHART_TYPE);
        assertThat(testNBChart.getNbChartParams()).isEqualTo(UPDATED_NB_CHART_PARAMS);
        assertThat(testNBChart.getNbLastUpdated()).isEqualTo(UPDATED_NB_LAST_UPDATED);
        assertThat(testNBChart.getNbLastUpdatedBy()).isEqualTo(UPDATED_NB_LAST_UPDATED_BY);
    }

    @Test
    void patchNonExistingNBChart() throws Exception {
        int databaseSizeBeforeUpdate = nBChartRepository.findAll().collectList().block().size();
        nBChart.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, nBChart.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(nBChart))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the NBChart in the database
        List<NBChart> nBChartList = nBChartRepository.findAll().collectList().block();
        assertThat(nBChartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchNBChart() throws Exception {
        int databaseSizeBeforeUpdate = nBChartRepository.findAll().collectList().block().size();
        nBChart.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(nBChart))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the NBChart in the database
        List<NBChart> nBChartList = nBChartRepository.findAll().collectList().block();
        assertThat(nBChartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamNBChart() throws Exception {
        int databaseSizeBeforeUpdate = nBChartRepository.findAll().collectList().block().size();
        nBChart.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(nBChart))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the NBChart in the database
        List<NBChart> nBChartList = nBChartRepository.findAll().collectList().block();
        assertThat(nBChartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteNBChart() {
        // Initialize the database
        nBChartRepository.save(nBChart).block();

        int databaseSizeBeforeDelete = nBChartRepository.findAll().collectList().block().size();

        // Delete the nBChart
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, nBChart.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<NBChart> nBChartList = nBChartRepository.findAll().collectList().block();
        assertThat(nBChartList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
