package com.neutronbinary.infectolabs.microservice.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.neutronbinary.infectolabs.microservice.IntegrationTest;
import com.neutronbinary.infectolabs.microservice.domain.NBChart;
import com.neutronbinary.infectolabs.microservice.repository.NBChartRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link NBChartResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
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
    private MockMvc restNBChartMockMvc;

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

    @BeforeEach
    public void initTest() {
        nBChart = createEntity(em);
    }

    @Test
    @Transactional
    void createNBChart() throws Exception {
        int databaseSizeBeforeCreate = nBChartRepository.findAll().size();
        // Create the NBChart
        restNBChartMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nBChart)))
            .andExpect(status().isCreated());

        // Validate the NBChart in the database
        List<NBChart> nBChartList = nBChartRepository.findAll();
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
    @Transactional
    void createNBChartWithExistingId() throws Exception {
        // Create the NBChart with an existing ID
        nBChart.setId(1L);

        int databaseSizeBeforeCreate = nBChartRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNBChartMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nBChart)))
            .andExpect(status().isBadRequest());

        // Validate the NBChart in the database
        List<NBChart> nBChartList = nBChartRepository.findAll();
        assertThat(nBChartList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNBCharts() throws Exception {
        // Initialize the database
        nBChartRepository.saveAndFlush(nBChart);

        // Get all the nBChartList
        restNBChartMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nBChart.getId().intValue())))
            .andExpect(jsonPath("$.[*].nbChartID").value(hasItem(DEFAULT_NB_CHART_ID)))
            .andExpect(jsonPath("$.[*].nbChartTitle").value(hasItem(DEFAULT_NB_CHART_TITLE)))
            .andExpect(jsonPath("$.[*].nbChartType").value(hasItem(DEFAULT_NB_CHART_TYPE)))
            .andExpect(jsonPath("$.[*].nbChartParams").value(hasItem(DEFAULT_NB_CHART_PARAMS)))
            .andExpect(jsonPath("$.[*].nbLastUpdated").value(hasItem(DEFAULT_NB_LAST_UPDATED)))
            .andExpect(jsonPath("$.[*].nbLastUpdatedBy").value(hasItem(DEFAULT_NB_LAST_UPDATED_BY)));
    }

    @Test
    @Transactional
    void getNBChart() throws Exception {
        // Initialize the database
        nBChartRepository.saveAndFlush(nBChart);

        // Get the nBChart
        restNBChartMockMvc
            .perform(get(ENTITY_API_URL_ID, nBChart.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nBChart.getId().intValue()))
            .andExpect(jsonPath("$.nbChartID").value(DEFAULT_NB_CHART_ID))
            .andExpect(jsonPath("$.nbChartTitle").value(DEFAULT_NB_CHART_TITLE))
            .andExpect(jsonPath("$.nbChartType").value(DEFAULT_NB_CHART_TYPE))
            .andExpect(jsonPath("$.nbChartParams").value(DEFAULT_NB_CHART_PARAMS))
            .andExpect(jsonPath("$.nbLastUpdated").value(DEFAULT_NB_LAST_UPDATED))
            .andExpect(jsonPath("$.nbLastUpdatedBy").value(DEFAULT_NB_LAST_UPDATED_BY));
    }

    @Test
    @Transactional
    void getNonExistingNBChart() throws Exception {
        // Get the nBChart
        restNBChartMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNBChart() throws Exception {
        // Initialize the database
        nBChartRepository.saveAndFlush(nBChart);

        int databaseSizeBeforeUpdate = nBChartRepository.findAll().size();

        // Update the nBChart
        NBChart updatedNBChart = nBChartRepository.findById(nBChart.getId()).get();
        // Disconnect from session so that the updates on updatedNBChart are not directly saved in db
        em.detach(updatedNBChart);
        updatedNBChart
            .nbChartID(UPDATED_NB_CHART_ID)
            .nbChartTitle(UPDATED_NB_CHART_TITLE)
            .nbChartType(UPDATED_NB_CHART_TYPE)
            .nbChartParams(UPDATED_NB_CHART_PARAMS)
            .nbLastUpdated(UPDATED_NB_LAST_UPDATED)
            .nbLastUpdatedBy(UPDATED_NB_LAST_UPDATED_BY);

        restNBChartMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNBChart.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNBChart))
            )
            .andExpect(status().isOk());

        // Validate the NBChart in the database
        List<NBChart> nBChartList = nBChartRepository.findAll();
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
    @Transactional
    void putNonExistingNBChart() throws Exception {
        int databaseSizeBeforeUpdate = nBChartRepository.findAll().size();
        nBChart.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNBChartMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nBChart.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nBChart))
            )
            .andExpect(status().isBadRequest());

        // Validate the NBChart in the database
        List<NBChart> nBChartList = nBChartRepository.findAll();
        assertThat(nBChartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNBChart() throws Exception {
        int databaseSizeBeforeUpdate = nBChartRepository.findAll().size();
        nBChart.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNBChartMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nBChart))
            )
            .andExpect(status().isBadRequest());

        // Validate the NBChart in the database
        List<NBChart> nBChartList = nBChartRepository.findAll();
        assertThat(nBChartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNBChart() throws Exception {
        int databaseSizeBeforeUpdate = nBChartRepository.findAll().size();
        nBChart.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNBChartMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nBChart)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NBChart in the database
        List<NBChart> nBChartList = nBChartRepository.findAll();
        assertThat(nBChartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNBChartWithPatch() throws Exception {
        // Initialize the database
        nBChartRepository.saveAndFlush(nBChart);

        int databaseSizeBeforeUpdate = nBChartRepository.findAll().size();

        // Update the nBChart using partial update
        NBChart partialUpdatedNBChart = new NBChart();
        partialUpdatedNBChart.setId(nBChart.getId());

        partialUpdatedNBChart.nbChartID(UPDATED_NB_CHART_ID).nbChartType(UPDATED_NB_CHART_TYPE);

        restNBChartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNBChart.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNBChart))
            )
            .andExpect(status().isOk());

        // Validate the NBChart in the database
        List<NBChart> nBChartList = nBChartRepository.findAll();
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
    @Transactional
    void fullUpdateNBChartWithPatch() throws Exception {
        // Initialize the database
        nBChartRepository.saveAndFlush(nBChart);

        int databaseSizeBeforeUpdate = nBChartRepository.findAll().size();

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

        restNBChartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNBChart.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNBChart))
            )
            .andExpect(status().isOk());

        // Validate the NBChart in the database
        List<NBChart> nBChartList = nBChartRepository.findAll();
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
    @Transactional
    void patchNonExistingNBChart() throws Exception {
        int databaseSizeBeforeUpdate = nBChartRepository.findAll().size();
        nBChart.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNBChartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nBChart.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nBChart))
            )
            .andExpect(status().isBadRequest());

        // Validate the NBChart in the database
        List<NBChart> nBChartList = nBChartRepository.findAll();
        assertThat(nBChartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNBChart() throws Exception {
        int databaseSizeBeforeUpdate = nBChartRepository.findAll().size();
        nBChart.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNBChartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nBChart))
            )
            .andExpect(status().isBadRequest());

        // Validate the NBChart in the database
        List<NBChart> nBChartList = nBChartRepository.findAll();
        assertThat(nBChartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNBChart() throws Exception {
        int databaseSizeBeforeUpdate = nBChartRepository.findAll().size();
        nBChart.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNBChartMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(nBChart)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NBChart in the database
        List<NBChart> nBChartList = nBChartRepository.findAll();
        assertThat(nBChartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNBChart() throws Exception {
        // Initialize the database
        nBChartRepository.saveAndFlush(nBChart);

        int databaseSizeBeforeDelete = nBChartRepository.findAll().size();

        // Delete the nBChart
        restNBChartMockMvc
            .perform(delete(ENTITY_API_URL_ID, nBChart.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NBChart> nBChartList = nBChartRepository.findAll();
        assertThat(nBChartList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
