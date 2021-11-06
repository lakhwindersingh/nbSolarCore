package com.neutronbinary.infectolabs.microservice.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.neutronbinary.infectolabs.microservice.IntegrationTest;
import com.neutronbinary.infectolabs.microservice.domain.NBMapComponentAttributes;
import com.neutronbinary.infectolabs.microservice.repository.NBMapComponentAttributesRepository;
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
 * Integration tests for the {@link NBMapComponentAttributesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NBMapComponentAttributesResourceIT {

    private static final String DEFAULT_NB_COMPONENT_IDFK = "AAAAAAAAAA";
    private static final String UPDATED_NB_COMPONENT_IDFK = "BBBBBBBBBB";

    private static final String DEFAULT_NB_COMPONENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NB_COMPONENT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NB_LAST_UPDATED = "AAAAAAAAAA";
    private static final String UPDATED_NB_LAST_UPDATED = "BBBBBBBBBB";

    private static final String DEFAULT_NB_LAST_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_NB_LAST_UPDATED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/nb-map-component-attributes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NBMapComponentAttributesRepository nBMapComponentAttributesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNBMapComponentAttributesMockMvc;

    private NBMapComponentAttributes nBMapComponentAttributes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NBMapComponentAttributes createEntity(EntityManager em) {
        NBMapComponentAttributes nBMapComponentAttributes = new NBMapComponentAttributes()
            .nbComponentIDFK(DEFAULT_NB_COMPONENT_IDFK)
            .nbComponentName(DEFAULT_NB_COMPONENT_NAME)
            .nbLastUpdated(DEFAULT_NB_LAST_UPDATED)
            .nbLastUpdatedBy(DEFAULT_NB_LAST_UPDATED_BY);
        return nBMapComponentAttributes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NBMapComponentAttributes createUpdatedEntity(EntityManager em) {
        NBMapComponentAttributes nBMapComponentAttributes = new NBMapComponentAttributes()
            .nbComponentIDFK(UPDATED_NB_COMPONENT_IDFK)
            .nbComponentName(UPDATED_NB_COMPONENT_NAME)
            .nbLastUpdated(UPDATED_NB_LAST_UPDATED)
            .nbLastUpdatedBy(UPDATED_NB_LAST_UPDATED_BY);
        return nBMapComponentAttributes;
    }

    @BeforeEach
    public void initTest() {
        nBMapComponentAttributes = createEntity(em);
    }

    @Test
    @Transactional
    void createNBMapComponentAttributes() throws Exception {
        int databaseSizeBeforeCreate = nBMapComponentAttributesRepository.findAll().size();
        // Create the NBMapComponentAttributes
        restNBMapComponentAttributesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nBMapComponentAttributes))
            )
            .andExpect(status().isCreated());

        // Validate the NBMapComponentAttributes in the database
        List<NBMapComponentAttributes> nBMapComponentAttributesList = nBMapComponentAttributesRepository.findAll();
        assertThat(nBMapComponentAttributesList).hasSize(databaseSizeBeforeCreate + 1);
        NBMapComponentAttributes testNBMapComponentAttributes = nBMapComponentAttributesList.get(nBMapComponentAttributesList.size() - 1);
        assertThat(testNBMapComponentAttributes.getNbComponentIDFK()).isEqualTo(DEFAULT_NB_COMPONENT_IDFK);
        assertThat(testNBMapComponentAttributes.getNbComponentName()).isEqualTo(DEFAULT_NB_COMPONENT_NAME);
        assertThat(testNBMapComponentAttributes.getNbLastUpdated()).isEqualTo(DEFAULT_NB_LAST_UPDATED);
        assertThat(testNBMapComponentAttributes.getNbLastUpdatedBy()).isEqualTo(DEFAULT_NB_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    void createNBMapComponentAttributesWithExistingId() throws Exception {
        // Create the NBMapComponentAttributes with an existing ID
        nBMapComponentAttributes.setId(1L);

        int databaseSizeBeforeCreate = nBMapComponentAttributesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNBMapComponentAttributesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nBMapComponentAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the NBMapComponentAttributes in the database
        List<NBMapComponentAttributes> nBMapComponentAttributesList = nBMapComponentAttributesRepository.findAll();
        assertThat(nBMapComponentAttributesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNBMapComponentAttributes() throws Exception {
        // Initialize the database
        nBMapComponentAttributesRepository.saveAndFlush(nBMapComponentAttributes);

        // Get all the nBMapComponentAttributesList
        restNBMapComponentAttributesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nBMapComponentAttributes.getId().intValue())))
            .andExpect(jsonPath("$.[*].nbComponentIDFK").value(hasItem(DEFAULT_NB_COMPONENT_IDFK)))
            .andExpect(jsonPath("$.[*].nbComponentName").value(hasItem(DEFAULT_NB_COMPONENT_NAME)))
            .andExpect(jsonPath("$.[*].nbLastUpdated").value(hasItem(DEFAULT_NB_LAST_UPDATED)))
            .andExpect(jsonPath("$.[*].nbLastUpdatedBy").value(hasItem(DEFAULT_NB_LAST_UPDATED_BY)));
    }

    @Test
    @Transactional
    void getNBMapComponentAttributes() throws Exception {
        // Initialize the database
        nBMapComponentAttributesRepository.saveAndFlush(nBMapComponentAttributes);

        // Get the nBMapComponentAttributes
        restNBMapComponentAttributesMockMvc
            .perform(get(ENTITY_API_URL_ID, nBMapComponentAttributes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nBMapComponentAttributes.getId().intValue()))
            .andExpect(jsonPath("$.nbComponentIDFK").value(DEFAULT_NB_COMPONENT_IDFK))
            .andExpect(jsonPath("$.nbComponentName").value(DEFAULT_NB_COMPONENT_NAME))
            .andExpect(jsonPath("$.nbLastUpdated").value(DEFAULT_NB_LAST_UPDATED))
            .andExpect(jsonPath("$.nbLastUpdatedBy").value(DEFAULT_NB_LAST_UPDATED_BY));
    }

    @Test
    @Transactional
    void getNonExistingNBMapComponentAttributes() throws Exception {
        // Get the nBMapComponentAttributes
        restNBMapComponentAttributesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNBMapComponentAttributes() throws Exception {
        // Initialize the database
        nBMapComponentAttributesRepository.saveAndFlush(nBMapComponentAttributes);

        int databaseSizeBeforeUpdate = nBMapComponentAttributesRepository.findAll().size();

        // Update the nBMapComponentAttributes
        NBMapComponentAttributes updatedNBMapComponentAttributes = nBMapComponentAttributesRepository
            .findById(nBMapComponentAttributes.getId())
            .get();
        // Disconnect from session so that the updates on updatedNBMapComponentAttributes are not directly saved in db
        em.detach(updatedNBMapComponentAttributes);
        updatedNBMapComponentAttributes
            .nbComponentIDFK(UPDATED_NB_COMPONENT_IDFK)
            .nbComponentName(UPDATED_NB_COMPONENT_NAME)
            .nbLastUpdated(UPDATED_NB_LAST_UPDATED)
            .nbLastUpdatedBy(UPDATED_NB_LAST_UPDATED_BY);

        restNBMapComponentAttributesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNBMapComponentAttributes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNBMapComponentAttributes))
            )
            .andExpect(status().isOk());

        // Validate the NBMapComponentAttributes in the database
        List<NBMapComponentAttributes> nBMapComponentAttributesList = nBMapComponentAttributesRepository.findAll();
        assertThat(nBMapComponentAttributesList).hasSize(databaseSizeBeforeUpdate);
        NBMapComponentAttributes testNBMapComponentAttributes = nBMapComponentAttributesList.get(nBMapComponentAttributesList.size() - 1);
        assertThat(testNBMapComponentAttributes.getNbComponentIDFK()).isEqualTo(UPDATED_NB_COMPONENT_IDFK);
        assertThat(testNBMapComponentAttributes.getNbComponentName()).isEqualTo(UPDATED_NB_COMPONENT_NAME);
        assertThat(testNBMapComponentAttributes.getNbLastUpdated()).isEqualTo(UPDATED_NB_LAST_UPDATED);
        assertThat(testNBMapComponentAttributes.getNbLastUpdatedBy()).isEqualTo(UPDATED_NB_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    void putNonExistingNBMapComponentAttributes() throws Exception {
        int databaseSizeBeforeUpdate = nBMapComponentAttributesRepository.findAll().size();
        nBMapComponentAttributes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNBMapComponentAttributesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nBMapComponentAttributes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nBMapComponentAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the NBMapComponentAttributes in the database
        List<NBMapComponentAttributes> nBMapComponentAttributesList = nBMapComponentAttributesRepository.findAll();
        assertThat(nBMapComponentAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNBMapComponentAttributes() throws Exception {
        int databaseSizeBeforeUpdate = nBMapComponentAttributesRepository.findAll().size();
        nBMapComponentAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNBMapComponentAttributesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nBMapComponentAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the NBMapComponentAttributes in the database
        List<NBMapComponentAttributes> nBMapComponentAttributesList = nBMapComponentAttributesRepository.findAll();
        assertThat(nBMapComponentAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNBMapComponentAttributes() throws Exception {
        int databaseSizeBeforeUpdate = nBMapComponentAttributesRepository.findAll().size();
        nBMapComponentAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNBMapComponentAttributesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nBMapComponentAttributes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NBMapComponentAttributes in the database
        List<NBMapComponentAttributes> nBMapComponentAttributesList = nBMapComponentAttributesRepository.findAll();
        assertThat(nBMapComponentAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNBMapComponentAttributesWithPatch() throws Exception {
        // Initialize the database
        nBMapComponentAttributesRepository.saveAndFlush(nBMapComponentAttributes);

        int databaseSizeBeforeUpdate = nBMapComponentAttributesRepository.findAll().size();

        // Update the nBMapComponentAttributes using partial update
        NBMapComponentAttributes partialUpdatedNBMapComponentAttributes = new NBMapComponentAttributes();
        partialUpdatedNBMapComponentAttributes.setId(nBMapComponentAttributes.getId());

        partialUpdatedNBMapComponentAttributes.nbComponentIDFK(UPDATED_NB_COMPONENT_IDFK).nbComponentName(UPDATED_NB_COMPONENT_NAME);

        restNBMapComponentAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNBMapComponentAttributes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNBMapComponentAttributes))
            )
            .andExpect(status().isOk());

        // Validate the NBMapComponentAttributes in the database
        List<NBMapComponentAttributes> nBMapComponentAttributesList = nBMapComponentAttributesRepository.findAll();
        assertThat(nBMapComponentAttributesList).hasSize(databaseSizeBeforeUpdate);
        NBMapComponentAttributes testNBMapComponentAttributes = nBMapComponentAttributesList.get(nBMapComponentAttributesList.size() - 1);
        assertThat(testNBMapComponentAttributes.getNbComponentIDFK()).isEqualTo(UPDATED_NB_COMPONENT_IDFK);
        assertThat(testNBMapComponentAttributes.getNbComponentName()).isEqualTo(UPDATED_NB_COMPONENT_NAME);
        assertThat(testNBMapComponentAttributes.getNbLastUpdated()).isEqualTo(DEFAULT_NB_LAST_UPDATED);
        assertThat(testNBMapComponentAttributes.getNbLastUpdatedBy()).isEqualTo(DEFAULT_NB_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    void fullUpdateNBMapComponentAttributesWithPatch() throws Exception {
        // Initialize the database
        nBMapComponentAttributesRepository.saveAndFlush(nBMapComponentAttributes);

        int databaseSizeBeforeUpdate = nBMapComponentAttributesRepository.findAll().size();

        // Update the nBMapComponentAttributes using partial update
        NBMapComponentAttributes partialUpdatedNBMapComponentAttributes = new NBMapComponentAttributes();
        partialUpdatedNBMapComponentAttributes.setId(nBMapComponentAttributes.getId());

        partialUpdatedNBMapComponentAttributes
            .nbComponentIDFK(UPDATED_NB_COMPONENT_IDFK)
            .nbComponentName(UPDATED_NB_COMPONENT_NAME)
            .nbLastUpdated(UPDATED_NB_LAST_UPDATED)
            .nbLastUpdatedBy(UPDATED_NB_LAST_UPDATED_BY);

        restNBMapComponentAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNBMapComponentAttributes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNBMapComponentAttributes))
            )
            .andExpect(status().isOk());

        // Validate the NBMapComponentAttributes in the database
        List<NBMapComponentAttributes> nBMapComponentAttributesList = nBMapComponentAttributesRepository.findAll();
        assertThat(nBMapComponentAttributesList).hasSize(databaseSizeBeforeUpdate);
        NBMapComponentAttributes testNBMapComponentAttributes = nBMapComponentAttributesList.get(nBMapComponentAttributesList.size() - 1);
        assertThat(testNBMapComponentAttributes.getNbComponentIDFK()).isEqualTo(UPDATED_NB_COMPONENT_IDFK);
        assertThat(testNBMapComponentAttributes.getNbComponentName()).isEqualTo(UPDATED_NB_COMPONENT_NAME);
        assertThat(testNBMapComponentAttributes.getNbLastUpdated()).isEqualTo(UPDATED_NB_LAST_UPDATED);
        assertThat(testNBMapComponentAttributes.getNbLastUpdatedBy()).isEqualTo(UPDATED_NB_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingNBMapComponentAttributes() throws Exception {
        int databaseSizeBeforeUpdate = nBMapComponentAttributesRepository.findAll().size();
        nBMapComponentAttributes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNBMapComponentAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nBMapComponentAttributes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nBMapComponentAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the NBMapComponentAttributes in the database
        List<NBMapComponentAttributes> nBMapComponentAttributesList = nBMapComponentAttributesRepository.findAll();
        assertThat(nBMapComponentAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNBMapComponentAttributes() throws Exception {
        int databaseSizeBeforeUpdate = nBMapComponentAttributesRepository.findAll().size();
        nBMapComponentAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNBMapComponentAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nBMapComponentAttributes))
            )
            .andExpect(status().isBadRequest());

        // Validate the NBMapComponentAttributes in the database
        List<NBMapComponentAttributes> nBMapComponentAttributesList = nBMapComponentAttributesRepository.findAll();
        assertThat(nBMapComponentAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNBMapComponentAttributes() throws Exception {
        int databaseSizeBeforeUpdate = nBMapComponentAttributesRepository.findAll().size();
        nBMapComponentAttributes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNBMapComponentAttributesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nBMapComponentAttributes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NBMapComponentAttributes in the database
        List<NBMapComponentAttributes> nBMapComponentAttributesList = nBMapComponentAttributesRepository.findAll();
        assertThat(nBMapComponentAttributesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNBMapComponentAttributes() throws Exception {
        // Initialize the database
        nBMapComponentAttributesRepository.saveAndFlush(nBMapComponentAttributes);

        int databaseSizeBeforeDelete = nBMapComponentAttributesRepository.findAll().size();

        // Delete the nBMapComponentAttributes
        restNBMapComponentAttributesMockMvc
            .perform(delete(ENTITY_API_URL_ID, nBMapComponentAttributes.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NBMapComponentAttributes> nBMapComponentAttributesList = nBMapComponentAttributesRepository.findAll();
        assertThat(nBMapComponentAttributesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
