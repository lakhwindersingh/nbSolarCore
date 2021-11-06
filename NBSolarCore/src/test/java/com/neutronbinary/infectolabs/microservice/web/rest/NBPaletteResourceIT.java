package com.neutronbinary.infectolabs.microservice.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.neutronbinary.infectolabs.microservice.IntegrationTest;
import com.neutronbinary.infectolabs.microservice.domain.NBPalette;
import com.neutronbinary.infectolabs.microservice.repository.NBPaletteRepository;
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
 * Integration tests for the {@link NBPaletteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NBPaletteResourceIT {

    private static final String DEFAULT_NB_PALETTE_ID = "AAAAAAAAAA";
    private static final String UPDATED_NB_PALETTE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NB_PALETTE_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_NB_PALETTE_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_NB_PALETTE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_NB_PALETTE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_NB_PALETTE_COLORS = "AAAAAAAAAA";
    private static final String UPDATED_NB_PALETTE_COLORS = "BBBBBBBBBB";

    private static final String DEFAULT_NB_LAST_UPDATED = "AAAAAAAAAA";
    private static final String UPDATED_NB_LAST_UPDATED = "BBBBBBBBBB";

    private static final String DEFAULT_NB_LAST_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_NB_LAST_UPDATED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/nb-palettes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NBPaletteRepository nBPaletteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNBPaletteMockMvc;

    private NBPalette nBPalette;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NBPalette createEntity(EntityManager em) {
        NBPalette nBPalette = new NBPalette()
            .nbPaletteID(DEFAULT_NB_PALETTE_ID)
            .nbPaletteTitle(DEFAULT_NB_PALETTE_TITLE)
            .nbPaletteType(DEFAULT_NB_PALETTE_TYPE)
            .nbPaletteColors(DEFAULT_NB_PALETTE_COLORS)
            .nbLastUpdated(DEFAULT_NB_LAST_UPDATED)
            .nbLastUpdatedBy(DEFAULT_NB_LAST_UPDATED_BY);
        return nBPalette;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NBPalette createUpdatedEntity(EntityManager em) {
        NBPalette nBPalette = new NBPalette()
            .nbPaletteID(UPDATED_NB_PALETTE_ID)
            .nbPaletteTitle(UPDATED_NB_PALETTE_TITLE)
            .nbPaletteType(UPDATED_NB_PALETTE_TYPE)
            .nbPaletteColors(UPDATED_NB_PALETTE_COLORS)
            .nbLastUpdated(UPDATED_NB_LAST_UPDATED)
            .nbLastUpdatedBy(UPDATED_NB_LAST_UPDATED_BY);
        return nBPalette;
    }

    @BeforeEach
    public void initTest() {
        nBPalette = createEntity(em);
    }

    @Test
    @Transactional
    void createNBPalette() throws Exception {
        int databaseSizeBeforeCreate = nBPaletteRepository.findAll().size();
        // Create the NBPalette
        restNBPaletteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nBPalette)))
            .andExpect(status().isCreated());

        // Validate the NBPalette in the database
        List<NBPalette> nBPaletteList = nBPaletteRepository.findAll();
        assertThat(nBPaletteList).hasSize(databaseSizeBeforeCreate + 1);
        NBPalette testNBPalette = nBPaletteList.get(nBPaletteList.size() - 1);
        assertThat(testNBPalette.getNbPaletteID()).isEqualTo(DEFAULT_NB_PALETTE_ID);
        assertThat(testNBPalette.getNbPaletteTitle()).isEqualTo(DEFAULT_NB_PALETTE_TITLE);
        assertThat(testNBPalette.getNbPaletteType()).isEqualTo(DEFAULT_NB_PALETTE_TYPE);
        assertThat(testNBPalette.getNbPaletteColors()).isEqualTo(DEFAULT_NB_PALETTE_COLORS);
        assertThat(testNBPalette.getNbLastUpdated()).isEqualTo(DEFAULT_NB_LAST_UPDATED);
        assertThat(testNBPalette.getNbLastUpdatedBy()).isEqualTo(DEFAULT_NB_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    void createNBPaletteWithExistingId() throws Exception {
        // Create the NBPalette with an existing ID
        nBPalette.setId(1L);

        int databaseSizeBeforeCreate = nBPaletteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNBPaletteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nBPalette)))
            .andExpect(status().isBadRequest());

        // Validate the NBPalette in the database
        List<NBPalette> nBPaletteList = nBPaletteRepository.findAll();
        assertThat(nBPaletteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNBPalettes() throws Exception {
        // Initialize the database
        nBPaletteRepository.saveAndFlush(nBPalette);

        // Get all the nBPaletteList
        restNBPaletteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nBPalette.getId().intValue())))
            .andExpect(jsonPath("$.[*].nbPaletteID").value(hasItem(DEFAULT_NB_PALETTE_ID)))
            .andExpect(jsonPath("$.[*].nbPaletteTitle").value(hasItem(DEFAULT_NB_PALETTE_TITLE)))
            .andExpect(jsonPath("$.[*].nbPaletteType").value(hasItem(DEFAULT_NB_PALETTE_TYPE)))
            .andExpect(jsonPath("$.[*].nbPaletteColors").value(hasItem(DEFAULT_NB_PALETTE_COLORS)))
            .andExpect(jsonPath("$.[*].nbLastUpdated").value(hasItem(DEFAULT_NB_LAST_UPDATED)))
            .andExpect(jsonPath("$.[*].nbLastUpdatedBy").value(hasItem(DEFAULT_NB_LAST_UPDATED_BY)));
    }

    @Test
    @Transactional
    void getNBPalette() throws Exception {
        // Initialize the database
        nBPaletteRepository.saveAndFlush(nBPalette);

        // Get the nBPalette
        restNBPaletteMockMvc
            .perform(get(ENTITY_API_URL_ID, nBPalette.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nBPalette.getId().intValue()))
            .andExpect(jsonPath("$.nbPaletteID").value(DEFAULT_NB_PALETTE_ID))
            .andExpect(jsonPath("$.nbPaletteTitle").value(DEFAULT_NB_PALETTE_TITLE))
            .andExpect(jsonPath("$.nbPaletteType").value(DEFAULT_NB_PALETTE_TYPE))
            .andExpect(jsonPath("$.nbPaletteColors").value(DEFAULT_NB_PALETTE_COLORS))
            .andExpect(jsonPath("$.nbLastUpdated").value(DEFAULT_NB_LAST_UPDATED))
            .andExpect(jsonPath("$.nbLastUpdatedBy").value(DEFAULT_NB_LAST_UPDATED_BY));
    }

    @Test
    @Transactional
    void getNonExistingNBPalette() throws Exception {
        // Get the nBPalette
        restNBPaletteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNBPalette() throws Exception {
        // Initialize the database
        nBPaletteRepository.saveAndFlush(nBPalette);

        int databaseSizeBeforeUpdate = nBPaletteRepository.findAll().size();

        // Update the nBPalette
        NBPalette updatedNBPalette = nBPaletteRepository.findById(nBPalette.getId()).get();
        // Disconnect from session so that the updates on updatedNBPalette are not directly saved in db
        em.detach(updatedNBPalette);
        updatedNBPalette
            .nbPaletteID(UPDATED_NB_PALETTE_ID)
            .nbPaletteTitle(UPDATED_NB_PALETTE_TITLE)
            .nbPaletteType(UPDATED_NB_PALETTE_TYPE)
            .nbPaletteColors(UPDATED_NB_PALETTE_COLORS)
            .nbLastUpdated(UPDATED_NB_LAST_UPDATED)
            .nbLastUpdatedBy(UPDATED_NB_LAST_UPDATED_BY);

        restNBPaletteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNBPalette.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNBPalette))
            )
            .andExpect(status().isOk());

        // Validate the NBPalette in the database
        List<NBPalette> nBPaletteList = nBPaletteRepository.findAll();
        assertThat(nBPaletteList).hasSize(databaseSizeBeforeUpdate);
        NBPalette testNBPalette = nBPaletteList.get(nBPaletteList.size() - 1);
        assertThat(testNBPalette.getNbPaletteID()).isEqualTo(UPDATED_NB_PALETTE_ID);
        assertThat(testNBPalette.getNbPaletteTitle()).isEqualTo(UPDATED_NB_PALETTE_TITLE);
        assertThat(testNBPalette.getNbPaletteType()).isEqualTo(UPDATED_NB_PALETTE_TYPE);
        assertThat(testNBPalette.getNbPaletteColors()).isEqualTo(UPDATED_NB_PALETTE_COLORS);
        assertThat(testNBPalette.getNbLastUpdated()).isEqualTo(UPDATED_NB_LAST_UPDATED);
        assertThat(testNBPalette.getNbLastUpdatedBy()).isEqualTo(UPDATED_NB_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    void putNonExistingNBPalette() throws Exception {
        int databaseSizeBeforeUpdate = nBPaletteRepository.findAll().size();
        nBPalette.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNBPaletteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nBPalette.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nBPalette))
            )
            .andExpect(status().isBadRequest());

        // Validate the NBPalette in the database
        List<NBPalette> nBPaletteList = nBPaletteRepository.findAll();
        assertThat(nBPaletteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNBPalette() throws Exception {
        int databaseSizeBeforeUpdate = nBPaletteRepository.findAll().size();
        nBPalette.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNBPaletteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nBPalette))
            )
            .andExpect(status().isBadRequest());

        // Validate the NBPalette in the database
        List<NBPalette> nBPaletteList = nBPaletteRepository.findAll();
        assertThat(nBPaletteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNBPalette() throws Exception {
        int databaseSizeBeforeUpdate = nBPaletteRepository.findAll().size();
        nBPalette.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNBPaletteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nBPalette)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NBPalette in the database
        List<NBPalette> nBPaletteList = nBPaletteRepository.findAll();
        assertThat(nBPaletteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNBPaletteWithPatch() throws Exception {
        // Initialize the database
        nBPaletteRepository.saveAndFlush(nBPalette);

        int databaseSizeBeforeUpdate = nBPaletteRepository.findAll().size();

        // Update the nBPalette using partial update
        NBPalette partialUpdatedNBPalette = new NBPalette();
        partialUpdatedNBPalette.setId(nBPalette.getId());

        partialUpdatedNBPalette
            .nbPaletteID(UPDATED_NB_PALETTE_ID)
            .nbPaletteTitle(UPDATED_NB_PALETTE_TITLE)
            .nbPaletteColors(UPDATED_NB_PALETTE_COLORS)
            .nbLastUpdatedBy(UPDATED_NB_LAST_UPDATED_BY);

        restNBPaletteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNBPalette.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNBPalette))
            )
            .andExpect(status().isOk());

        // Validate the NBPalette in the database
        List<NBPalette> nBPaletteList = nBPaletteRepository.findAll();
        assertThat(nBPaletteList).hasSize(databaseSizeBeforeUpdate);
        NBPalette testNBPalette = nBPaletteList.get(nBPaletteList.size() - 1);
        assertThat(testNBPalette.getNbPaletteID()).isEqualTo(UPDATED_NB_PALETTE_ID);
        assertThat(testNBPalette.getNbPaletteTitle()).isEqualTo(UPDATED_NB_PALETTE_TITLE);
        assertThat(testNBPalette.getNbPaletteType()).isEqualTo(DEFAULT_NB_PALETTE_TYPE);
        assertThat(testNBPalette.getNbPaletteColors()).isEqualTo(UPDATED_NB_PALETTE_COLORS);
        assertThat(testNBPalette.getNbLastUpdated()).isEqualTo(DEFAULT_NB_LAST_UPDATED);
        assertThat(testNBPalette.getNbLastUpdatedBy()).isEqualTo(UPDATED_NB_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    void fullUpdateNBPaletteWithPatch() throws Exception {
        // Initialize the database
        nBPaletteRepository.saveAndFlush(nBPalette);

        int databaseSizeBeforeUpdate = nBPaletteRepository.findAll().size();

        // Update the nBPalette using partial update
        NBPalette partialUpdatedNBPalette = new NBPalette();
        partialUpdatedNBPalette.setId(nBPalette.getId());

        partialUpdatedNBPalette
            .nbPaletteID(UPDATED_NB_PALETTE_ID)
            .nbPaletteTitle(UPDATED_NB_PALETTE_TITLE)
            .nbPaletteType(UPDATED_NB_PALETTE_TYPE)
            .nbPaletteColors(UPDATED_NB_PALETTE_COLORS)
            .nbLastUpdated(UPDATED_NB_LAST_UPDATED)
            .nbLastUpdatedBy(UPDATED_NB_LAST_UPDATED_BY);

        restNBPaletteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNBPalette.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNBPalette))
            )
            .andExpect(status().isOk());

        // Validate the NBPalette in the database
        List<NBPalette> nBPaletteList = nBPaletteRepository.findAll();
        assertThat(nBPaletteList).hasSize(databaseSizeBeforeUpdate);
        NBPalette testNBPalette = nBPaletteList.get(nBPaletteList.size() - 1);
        assertThat(testNBPalette.getNbPaletteID()).isEqualTo(UPDATED_NB_PALETTE_ID);
        assertThat(testNBPalette.getNbPaletteTitle()).isEqualTo(UPDATED_NB_PALETTE_TITLE);
        assertThat(testNBPalette.getNbPaletteType()).isEqualTo(UPDATED_NB_PALETTE_TYPE);
        assertThat(testNBPalette.getNbPaletteColors()).isEqualTo(UPDATED_NB_PALETTE_COLORS);
        assertThat(testNBPalette.getNbLastUpdated()).isEqualTo(UPDATED_NB_LAST_UPDATED);
        assertThat(testNBPalette.getNbLastUpdatedBy()).isEqualTo(UPDATED_NB_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingNBPalette() throws Exception {
        int databaseSizeBeforeUpdate = nBPaletteRepository.findAll().size();
        nBPalette.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNBPaletteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nBPalette.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nBPalette))
            )
            .andExpect(status().isBadRequest());

        // Validate the NBPalette in the database
        List<NBPalette> nBPaletteList = nBPaletteRepository.findAll();
        assertThat(nBPaletteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNBPalette() throws Exception {
        int databaseSizeBeforeUpdate = nBPaletteRepository.findAll().size();
        nBPalette.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNBPaletteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nBPalette))
            )
            .andExpect(status().isBadRequest());

        // Validate the NBPalette in the database
        List<NBPalette> nBPaletteList = nBPaletteRepository.findAll();
        assertThat(nBPaletteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNBPalette() throws Exception {
        int databaseSizeBeforeUpdate = nBPaletteRepository.findAll().size();
        nBPalette.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNBPaletteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(nBPalette))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NBPalette in the database
        List<NBPalette> nBPaletteList = nBPaletteRepository.findAll();
        assertThat(nBPaletteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNBPalette() throws Exception {
        // Initialize the database
        nBPaletteRepository.saveAndFlush(nBPalette);

        int databaseSizeBeforeDelete = nBPaletteRepository.findAll().size();

        // Delete the nBPalette
        restNBPaletteMockMvc
            .perform(delete(ENTITY_API_URL_ID, nBPalette.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NBPalette> nBPaletteList = nBPaletteRepository.findAll();
        assertThat(nBPaletteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
