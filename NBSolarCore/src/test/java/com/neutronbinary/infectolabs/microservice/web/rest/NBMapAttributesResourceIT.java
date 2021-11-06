package com.neutronbinary.infectolabs.microservice.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.neutronbinary.infectolabs.microservice.IntegrationTest;
import com.neutronbinary.infectolabs.microservice.domain.NBMapAttributes;
import com.neutronbinary.infectolabs.microservice.repository.NBMapAttributesRepository;
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
 * Integration tests for the {@link NBMapAttributesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NBMapAttributesResourceIT {

    private static final String DEFAULT_NB_IDFK = "AAAAAAAAAA";
    private static final String UPDATED_NB_IDFK = "BBBBBBBBBB";

    private static final String DEFAULT_NB_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_NB_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_NB_TITLE_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_NB_TITLE_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_NB_PALETTE_IDFK = "AAAAAAAAAA";
    private static final String UPDATED_NB_PALETTE_IDFK = "BBBBBBBBBB";

    private static final String DEFAULT_NB_CHART_IDFK = "AAAAAAAAAA";
    private static final String UPDATED_NB_CHART_IDFK = "BBBBBBBBBB";

    private static final String DEFAULT_NB_CHART_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_NB_CHART_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_NB_LAST_UPDATED = "AAAAAAAAAA";
    private static final String UPDATED_NB_LAST_UPDATED = "BBBBBBBBBB";

    private static final String DEFAULT_NB_LAST_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_NB_LAST_UPDATED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/nb-map-attributes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NBMapAttributesRepository nBMapAttributesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNBMapAttributesMockMvc;

    private NBMapAttributes nBMapAttributes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NBMapAttributes createEntity(EntityManager em) {
        NBMapAttributes nBMapAttributes = new NBMapAttributes()
            .nbIDFK(DEFAULT_NB_IDFK)
            .nbTitle(DEFAULT_NB_TITLE)
            .nbTitleLocation(DEFAULT_NB_TITLE_LOCATION)
            .nbPaletteIDFK(DEFAULT_NB_PALETTE_IDFK)
            .nbChartIDFK(DEFAULT_NB_CHART_IDFK)
            .nbChartType(DEFAULT_NB_CHART_TYPE)
            .nbLastUpdated(DEFAULT_NB_LAST_UPDATED)
            .nbLastUpdatedBy(DEFAULT_NB_LAST_UPDATED_BY);
        return nBMapAttributes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NBMapAttributes createUpdatedEntity(EntityManager em) {
        NBMapAttributes nBMapAttributes = new NBMapAttributes()
            .nbIDFK(UPDATED_NB_IDFK)
            .nbTitle(UPDATED_NB_TITLE)
            .nbTitleLocation(UPDATED_NB_TITLE_LOCATION)
            .nbPaletteIDFK(UPDATED_NB_PALETTE_IDFK)
            .nbChartIDFK(UPDATED_NB_CHART_IDFK)
            .nbChartType(UPDATED_NB_CHART_TYPE)
            .nbLastUpdated(UPDATED_NB_LAST_UPDATED)
            .nbLastUpdatedBy(UPDATED_NB_LAST_UPDATED_BY);
        return nBMapAttributes;
    }

    @BeforeEach
    public void initTest() {
        nBMapAttributes = createEntity(em);
    }

    @Test
    @Transactional
    void createNBMapAttributes() throws Exception {
        int databaseSizeBeforeCreate = nBMapAttributesRepository.findAll().size();
        // Create the NBMapAttributes
        restNBMapAttributesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nBMapAttributes))
            )
            .andExpect(status().isCreated());

        // Validate the NBMapAttributes in the database
        List<NBMapAttributes> nBMapAttributesList = nBMapAttributesRepository.findAll();
        assertThat(nBMapAttributesList).hasSize(databaseSizeBeforeCreate + 1);
        NBMapAttributes testNBMapAttributes = nBMapAttributesList.get(nBMapAttributesList.size() - 1);
        assertThat(testNBMapAttributes.getNbIDFK()).isEqualTo(DEFAULT_NB_IDFK);
        assertThat(testNBMapAttributes.getNbTitle()).isEqualTo(DEFAULT_NB_TITLE);
        assertThat(testNBMapAttributes.getNbTitleLocation()).isEqualTo(DEFAULT_NB_TITLE_LOCATION);
        assertThat(testNBMapAttributes.getNbPaletteIDFK()).isEqualTo(DEFAULT_NB_PALETTE_IDFK);
        assertThat(testNBMapAttributes.getNbChartIDFK()).isEqualTo(DEFAULT_NB_CHART_IDFK);
        assertThat(testNBMapAttributes.getNbChartType()).isEqualTo(DEFAULT_NB_CHART_TYPE);
        assertThat(testNBMapAttributes.getNbLastUpdated()).isEqualTo(DEFAULT_NB_LAST_UPDATED);
        assertThat(testNBMapAttributes.getNbLastUpdatedBy()).isEqualTo(DEFAULT_NB_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    void createNBMapAttributesWithExistingId() throws Exception {
        // Create the NBMapAttributes with an existing ID
        nBMapAttributes.setId(1L);

        int databaseSizeBeforeCreate = nBMapAttributesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNBMapAttributesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nBMapAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the NBMapAttributes in the database
        List<NBMapAttributes> nBMapAttributesList = nBMapAttributesRepository.findAll();
        assertThat(nBMapAttributesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNBMapAttributes() throws Exception {
        // Initialize the database
        nBMapAttributesRepository.saveAndFlush(nBMapAttributes);

        // Get all the nBMapAttributesList
        restNBMapAttributesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nBMapAttributes.getId().intValue())))
            .andExpect(jsonPath("$.[*].nbIDFK").value(hasItem(DEFAULT_NB_IDFK)))
            .andExpect(jsonPath("$.[*].nbTitle").value(hasItem(DEFAULT_NB_TITLE)))
            .andExpect(jsonPath("$.[*].nbTitleLocation").value(hasItem(DEFAULT_NB_TITLE_LOCATION)))
            .andExpect(jsonPath("$.[*].nbPaletteIDFK").value(hasItem(DEFAULT_NB_PALETTE_IDFK)))
            .andExpect(jsonPath("$.[*].nbChartIDFK").value(hasItem(DEFAULT_NB_CHART_IDFK)))
            .andExpect(jsonPath("$.[*].nbChartType").value(hasItem(DEFAULT_NB_CHART_TYPE)))
            .andExpect(jsonPath("$.[*].nbLastUpdated").value(hasItem(DEFAULT_NB_LAST_UPDATED)))
            .andExpect(jsonPath("$.[*].nbLastUpdatedBy").value(hasItem(DEFAULT_NB_LAST_UPDATED_BY)));
    }

    @Test
    @Transactional
    void getNBMapAttributes() throws Exception {
        // Initialize the database
        nBMapAttributesRepository.saveAndFlush(nBMapAttributes);

        // Get the nBMapAttributes
        restNBMapAttributesMockMvc
            .perform(get(ENTITY_API_URL_ID, nBMapAttributes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nBMapAttributes.getId().intValue()))
            .andExpect(jsonPath("$.nbIDFK").value(DEFAULT_NB_IDFK))
            .andExpect(jsonPath("$.nbTitle").value(DEFAULT_NB_TITLE))
            .andExpect(jsonPath("$.nbTitleLocation").value(DEFAULT_NB_TITLE_LOCATION))
            .andExpect(jsonPath("$.nbPaletteIDFK").value(DEFAULT_NB_PALETTE_IDFK))
            .andExpect(jsonPath("$.nbChartIDFK").value(DEFAULT_NB_CHART_IDFK))
            .andExpect(jsonPath("$.nbChartType").value(DEFAULT_NB_CHART_TYPE))
            .andExpect(jsonPath("$.nbLastUpdated").value(DEFAULT_NB_LAST_UPDATED))
            .andExpect(jsonPath("$.nbLastUpdatedBy").value(DEFAULT_NB_LAST_UPDATED_BY));
    }

    @Test
    @Transactional
    void getNonExistingNBMapAttributes() throws Exception {
        // Get the nBMapAttributes
        restNBMapAttributesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNBMapAttributes() throws Exception {
        // Initialize the database
        nBMapAttributesRepository.saveAndFlush(nBMapAttributes);

        int databaseSizeBeforeUpdate = nBMapAttributesRepository.findAll().size();

        // Update the nBMapAttributes
        NBMapAttributes updatedNBMapAttributes = nBMapAttributesRepository.findById(nBMapAttributes.getId()).get();
        // Disconnect from session so that the updates on updatedNBMapAttributes are not directly saved in db
        em.detach(updatedNBMapAttributes);
        updatedNBMapAttributes
            .nbIDFK(UPDATED_NB_IDFK)
            .nbTitle(UPDATED_NB_TITLE)
            .nbTitleLocation(UPDATED_NB_TITLE_LOCATION)
            .nbPaletteIDFK(UPDATED_NB_PALETTE_IDFK)
            .nbChartIDFK(UPDATED_NB_CHART_IDFK)
            .nbChartType(UPDATED_NB_CHART_TYPE)
            .nbLastUpdated(UPDATED_NB_LAST_UPDATED)
            .nbLastUpdatedBy(UPDATED_NB_LAST_UPDATED_BY);

        restNBMapAttributesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNBMapAttributes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNBMapAttributes))
            )
            .andExpect(status().isOk());

        // Validate the NBMapAttributes in the database
        List<NBMapAttributes> nBMapAttributesList = nBMapAttributesRepository.findAll();
        assertThat(nBMapAttributesList).hasSize(databaseSizeBeforeUpdate);
        NBMapAttributes testNBMapAttributes = nBMapAttributesList.get(nBMapAttributesList.size() - 1);
        assertThat(testNBMapAttributes.getNbIDFK()).isEqualTo(UPDATED_NB_IDFK);
        assertThat(testNBMapAttributes.getNbTitle()).isEqualTo(UPDATED_NB_TITLE);
        assertThat(testNBMapAttributes.getNbTitleLocation()).isEqualTo(UPDATED_NB_TITLE_LOCATION);
        assertThat(testNBMapAttributes.getNbPaletteIDFK()).isEqualTo(UPDATED_NB_PALETTE_IDFK);
        assertThat(testNBMapAttributes.getNbChartIDFK()).isEqualTo(UPDATED_NB_CHART_IDFK);
        assertThat(testNBMapAttributes.getNbChartType()).isEqualTo(UPDATED_NB_CHART_TYPE);
        assertThat(testNBMapAttributes.getNbLastUpdated()).isEqualTo(UPDATED_NB_LAST_UPDATED);
        assertThat(testNBMapAttributes.getNbLastUpdatedBy()).isEqualTo(UPDATED_NB_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    void putNonExistingNBMapAttributes() throws Exception {
        int databaseSizeBeforeUpdate = nBMapAttributesRepository.findAll().size();
        nBMapAttributes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNBMapAttributesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nBMapAttributes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nBMapAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the NBMapAttributes in the database
        List<NBMapAttributes> nBMapAttributesList = nBMapAttributesRepository.findAll();
        assertThat(nBMapAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNBMapAttributes() throws Exception {
        int databaseSizeBeforeUpdate = nBMapAttributesRepository.findAll().size();
        nBMapAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNBMapAttributesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nBMapAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the NBMapAttributes in the database
        List<NBMapAttributes> nBMapAttributesList = nBMapAttributesRepository.findAll();
        assertThat(nBMapAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNBMapAttributes() throws Exception {
        int databaseSizeBeforeUpdate = nBMapAttributesRepository.findAll().size();
        nBMapAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNBMapAttributesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nBMapAttributes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NBMapAttributes in the database
        List<NBMapAttributes> nBMapAttributesList = nBMapAttributesRepository.findAll();
        assertThat(nBMapAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNBMapAttributesWithPatch() throws Exception {
        // Initialize the database
        nBMapAttributesRepository.saveAndFlush(nBMapAttributes);

        int databaseSizeBeforeUpdate = nBMapAttributesRepository.findAll().size();

        // Update the nBMapAttributes using partial update
        NBMapAttributes partialUpdatedNBMapAttributes = new NBMapAttributes();
        partialUpdatedNBMapAttributes.setId(nBMapAttributes.getId());

        partialUpdatedNBMapAttributes.nbLastUpdated(UPDATED_NB_LAST_UPDATED).nbLastUpdatedBy(UPDATED_NB_LAST_UPDATED_BY);

        restNBMapAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNBMapAttributes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNBMapAttributes))
            )
            .andExpect(status().isOk());

        // Validate the NBMapAttributes in the database
        List<NBMapAttributes> nBMapAttributesList = nBMapAttributesRepository.findAll();
        assertThat(nBMapAttributesList).hasSize(databaseSizeBeforeUpdate);
        NBMapAttributes testNBMapAttributes = nBMapAttributesList.get(nBMapAttributesList.size() - 1);
        assertThat(testNBMapAttributes.getNbIDFK()).isEqualTo(DEFAULT_NB_IDFK);
        assertThat(testNBMapAttributes.getNbTitle()).isEqualTo(DEFAULT_NB_TITLE);
        assertThat(testNBMapAttributes.getNbTitleLocation()).isEqualTo(DEFAULT_NB_TITLE_LOCATION);
        assertThat(testNBMapAttributes.getNbPaletteIDFK()).isEqualTo(DEFAULT_NB_PALETTE_IDFK);
        assertThat(testNBMapAttributes.getNbChartIDFK()).isEqualTo(DEFAULT_NB_CHART_IDFK);
        assertThat(testNBMapAttributes.getNbChartType()).isEqualTo(DEFAULT_NB_CHART_TYPE);
        assertThat(testNBMapAttributes.getNbLastUpdated()).isEqualTo(UPDATED_NB_LAST_UPDATED);
        assertThat(testNBMapAttributes.getNbLastUpdatedBy()).isEqualTo(UPDATED_NB_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    void fullUpdateNBMapAttributesWithPatch() throws Exception {
        // Initialize the database
        nBMapAttributesRepository.saveAndFlush(nBMapAttributes);

        int databaseSizeBeforeUpdate = nBMapAttributesRepository.findAll().size();

        // Update the nBMapAttributes using partial update
        NBMapAttributes partialUpdatedNBMapAttributes = new NBMapAttributes();
        partialUpdatedNBMapAttributes.setId(nBMapAttributes.getId());

        partialUpdatedNBMapAttributes
            .nbIDFK(UPDATED_NB_IDFK)
            .nbTitle(UPDATED_NB_TITLE)
            .nbTitleLocation(UPDATED_NB_TITLE_LOCATION)
            .nbPaletteIDFK(UPDATED_NB_PALETTE_IDFK)
            .nbChartIDFK(UPDATED_NB_CHART_IDFK)
            .nbChartType(UPDATED_NB_CHART_TYPE)
            .nbLastUpdated(UPDATED_NB_LAST_UPDATED)
            .nbLastUpdatedBy(UPDATED_NB_LAST_UPDATED_BY);

        restNBMapAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNBMapAttributes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNBMapAttributes))
            )
            .andExpect(status().isOk());

        // Validate the NBMapAttributes in the database
        List<NBMapAttributes> nBMapAttributesList = nBMapAttributesRepository.findAll();
        assertThat(nBMapAttributesList).hasSize(databaseSizeBeforeUpdate);
        NBMapAttributes testNBMapAttributes = nBMapAttributesList.get(nBMapAttributesList.size() - 1);
        assertThat(testNBMapAttributes.getNbIDFK()).isEqualTo(UPDATED_NB_IDFK);
        assertThat(testNBMapAttributes.getNbTitle()).isEqualTo(UPDATED_NB_TITLE);
        assertThat(testNBMapAttributes.getNbTitleLocation()).isEqualTo(UPDATED_NB_TITLE_LOCATION);
        assertThat(testNBMapAttributes.getNbPaletteIDFK()).isEqualTo(UPDATED_NB_PALETTE_IDFK);
        assertThat(testNBMapAttributes.getNbChartIDFK()).isEqualTo(UPDATED_NB_CHART_IDFK);
        assertThat(testNBMapAttributes.getNbChartType()).isEqualTo(UPDATED_NB_CHART_TYPE);
        assertThat(testNBMapAttributes.getNbLastUpdated()).isEqualTo(UPDATED_NB_LAST_UPDATED);
        assertThat(testNBMapAttributes.getNbLastUpdatedBy()).isEqualTo(UPDATED_NB_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingNBMapAttributes() throws Exception {
        int databaseSizeBeforeUpdate = nBMapAttributesRepository.findAll().size();
        nBMapAttributes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNBMapAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nBMapAttributes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nBMapAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the NBMapAttributes in the database
        List<NBMapAttributes> nBMapAttributesList = nBMapAttributesRepository.findAll();
        assertThat(nBMapAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNBMapAttributes() throws Exception {
        int databaseSizeBeforeUpdate = nBMapAttributesRepository.findAll().size();
        nBMapAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNBMapAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nBMapAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the NBMapAttributes in the database
        List<NBMapAttributes> nBMapAttributesList = nBMapAttributesRepository.findAll();
        assertThat(nBMapAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNBMapAttributes() throws Exception {
        int databaseSizeBeforeUpdate = nBMapAttributesRepository.findAll().size();
        nBMapAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNBMapAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nBMapAttributes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NBMapAttributes in the database
        List<NBMapAttributes> nBMapAttributesList = nBMapAttributesRepository.findAll();
        assertThat(nBMapAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNBMapAttributes() throws Exception {
        // Initialize the database
        nBMapAttributesRepository.saveAndFlush(nBMapAttributes);

        int databaseSizeBeforeDelete = nBMapAttributesRepository.findAll().size();

        // Delete the nBMapAttributes
        restNBMapAttributesMockMvc
            .perform(delete(ENTITY_API_URL_ID, nBMapAttributes.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NBMapAttributes> nBMapAttributesList = nBMapAttributesRepository.findAll();
        assertThat(nBMapAttributesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
