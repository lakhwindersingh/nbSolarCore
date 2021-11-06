package com.neutronbinary.infectolabs.microservice.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.neutronbinary.infectolabs.microservice.IntegrationTest;
import com.neutronbinary.infectolabs.microservice.domain.NBMapComponents;
import com.neutronbinary.infectolabs.microservice.repository.NBMapComponentsRepository;
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
 * Integration tests for the {@link NBMapComponentsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NBMapComponentsResourceIT {

    private static final String DEFAULT_NB_IDFK = "AAAAAAAAAA";
    private static final String UPDATED_NB_IDFK = "BBBBBBBBBB";

    private static final String DEFAULT_NB_COMPONENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_NB_COMPONENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NB_COMPONENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_NB_COMPONENT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_NB_COMPONENT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_NB_COMPONENT_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_NB_DEFAULT = "AAAAAAAAAA";
    private static final String UPDATED_NB_DEFAULT = "BBBBBBBBBB";

    private static final String DEFAULT_NB_LAST_UPDATED = "AAAAAAAAAA";
    private static final String UPDATED_NB_LAST_UPDATED = "BBBBBBBBBB";

    private static final String DEFAULT_NB_LAST_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_NB_LAST_UPDATED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/nb-map-components";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NBMapComponentsRepository nBMapComponentsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNBMapComponentsMockMvc;

    private NBMapComponents nBMapComponents;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NBMapComponents createEntity(EntityManager em) {
        NBMapComponents nBMapComponents = new NBMapComponents()
            .nbIDFK(DEFAULT_NB_IDFK)
            .nbComponentID(DEFAULT_NB_COMPONENT_ID)
            .nbComponentType(DEFAULT_NB_COMPONENT_TYPE)
            .nbComponentValue(DEFAULT_NB_COMPONENT_VALUE)
            .nbDefault(DEFAULT_NB_DEFAULT)
            .nbLastUpdated(DEFAULT_NB_LAST_UPDATED)
            .nbLastUpdatedBy(DEFAULT_NB_LAST_UPDATED_BY);
        return nBMapComponents;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NBMapComponents createUpdatedEntity(EntityManager em) {
        NBMapComponents nBMapComponents = new NBMapComponents()
            .nbIDFK(UPDATED_NB_IDFK)
            .nbComponentID(UPDATED_NB_COMPONENT_ID)
            .nbComponentType(UPDATED_NB_COMPONENT_TYPE)
            .nbComponentValue(UPDATED_NB_COMPONENT_VALUE)
            .nbDefault(UPDATED_NB_DEFAULT)
            .nbLastUpdated(UPDATED_NB_LAST_UPDATED)
            .nbLastUpdatedBy(UPDATED_NB_LAST_UPDATED_BY);
        return nBMapComponents;
    }

    @BeforeEach
    public void initTest() {
        nBMapComponents = createEntity(em);
    }

    @Test
    @Transactional
    void createNBMapComponents() throws Exception {
        int databaseSizeBeforeCreate = nBMapComponentsRepository.findAll().size();
        // Create the NBMapComponents
        restNBMapComponentsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nBMapComponents))
            )
            .andExpect(status().isCreated());

        // Validate the NBMapComponents in the database
        List<NBMapComponents> nBMapComponentsList = nBMapComponentsRepository.findAll();
        assertThat(nBMapComponentsList).hasSize(databaseSizeBeforeCreate + 1);
        NBMapComponents testNBMapComponents = nBMapComponentsList.get(nBMapComponentsList.size() - 1);
        assertThat(testNBMapComponents.getNbIDFK()).isEqualTo(DEFAULT_NB_IDFK);
        assertThat(testNBMapComponents.getNbComponentID()).isEqualTo(DEFAULT_NB_COMPONENT_ID);
        assertThat(testNBMapComponents.getNbComponentType()).isEqualTo(DEFAULT_NB_COMPONENT_TYPE);
        assertThat(testNBMapComponents.getNbComponentValue()).isEqualTo(DEFAULT_NB_COMPONENT_VALUE);
        assertThat(testNBMapComponents.getNbDefault()).isEqualTo(DEFAULT_NB_DEFAULT);
        assertThat(testNBMapComponents.getNbLastUpdated()).isEqualTo(DEFAULT_NB_LAST_UPDATED);
        assertThat(testNBMapComponents.getNbLastUpdatedBy()).isEqualTo(DEFAULT_NB_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    void createNBMapComponentsWithExistingId() throws Exception {
        // Create the NBMapComponents with an existing ID
        nBMapComponents.setId(1L);

        int databaseSizeBeforeCreate = nBMapComponentsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNBMapComponentsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nBMapComponents))
            )
            .andExpect(status().isBadRequest());

        // Validate the NBMapComponents in the database
        List<NBMapComponents> nBMapComponentsList = nBMapComponentsRepository.findAll();
        assertThat(nBMapComponentsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNBMapComponents() throws Exception {
        // Initialize the database
        nBMapComponentsRepository.saveAndFlush(nBMapComponents);

        // Get all the nBMapComponentsList
        restNBMapComponentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nBMapComponents.getId().intValue())))
            .andExpect(jsonPath("$.[*].nbIDFK").value(hasItem(DEFAULT_NB_IDFK)))
            .andExpect(jsonPath("$.[*].nbComponentID").value(hasItem(DEFAULT_NB_COMPONENT_ID)))
            .andExpect(jsonPath("$.[*].nbComponentType").value(hasItem(DEFAULT_NB_COMPONENT_TYPE)))
            .andExpect(jsonPath("$.[*].nbComponentValue").value(hasItem(DEFAULT_NB_COMPONENT_VALUE)))
            .andExpect(jsonPath("$.[*].nbDefault").value(hasItem(DEFAULT_NB_DEFAULT)))
            .andExpect(jsonPath("$.[*].nbLastUpdated").value(hasItem(DEFAULT_NB_LAST_UPDATED)))
            .andExpect(jsonPath("$.[*].nbLastUpdatedBy").value(hasItem(DEFAULT_NB_LAST_UPDATED_BY)));
    }

    @Test
    @Transactional
    void getNBMapComponents() throws Exception {
        // Initialize the database
        nBMapComponentsRepository.saveAndFlush(nBMapComponents);

        // Get the nBMapComponents
        restNBMapComponentsMockMvc
            .perform(get(ENTITY_API_URL_ID, nBMapComponents.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nBMapComponents.getId().intValue()))
            .andExpect(jsonPath("$.nbIDFK").value(DEFAULT_NB_IDFK))
            .andExpect(jsonPath("$.nbComponentID").value(DEFAULT_NB_COMPONENT_ID))
            .andExpect(jsonPath("$.nbComponentType").value(DEFAULT_NB_COMPONENT_TYPE))
            .andExpect(jsonPath("$.nbComponentValue").value(DEFAULT_NB_COMPONENT_VALUE))
            .andExpect(jsonPath("$.nbDefault").value(DEFAULT_NB_DEFAULT))
            .andExpect(jsonPath("$.nbLastUpdated").value(DEFAULT_NB_LAST_UPDATED))
            .andExpect(jsonPath("$.nbLastUpdatedBy").value(DEFAULT_NB_LAST_UPDATED_BY));
    }

    @Test
    @Transactional
    void getNonExistingNBMapComponents() throws Exception {
        // Get the nBMapComponents
        restNBMapComponentsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNBMapComponents() throws Exception {
        // Initialize the database
        nBMapComponentsRepository.saveAndFlush(nBMapComponents);

        int databaseSizeBeforeUpdate = nBMapComponentsRepository.findAll().size();

        // Update the nBMapComponents
        NBMapComponents updatedNBMapComponents = nBMapComponentsRepository.findById(nBMapComponents.getId()).get();
        // Disconnect from session so that the updates on updatedNBMapComponents are not directly saved in db
        em.detach(updatedNBMapComponents);
        updatedNBMapComponents
            .nbIDFK(UPDATED_NB_IDFK)
            .nbComponentID(UPDATED_NB_COMPONENT_ID)
            .nbComponentType(UPDATED_NB_COMPONENT_TYPE)
            .nbComponentValue(UPDATED_NB_COMPONENT_VALUE)
            .nbDefault(UPDATED_NB_DEFAULT)
            .nbLastUpdated(UPDATED_NB_LAST_UPDATED)
            .nbLastUpdatedBy(UPDATED_NB_LAST_UPDATED_BY);

        restNBMapComponentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNBMapComponents.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNBMapComponents))
            )
            .andExpect(status().isOk());

        // Validate the NBMapComponents in the database
        List<NBMapComponents> nBMapComponentsList = nBMapComponentsRepository.findAll();
        assertThat(nBMapComponentsList).hasSize(databaseSizeBeforeUpdate);
        NBMapComponents testNBMapComponents = nBMapComponentsList.get(nBMapComponentsList.size() - 1);
        assertThat(testNBMapComponents.getNbIDFK()).isEqualTo(UPDATED_NB_IDFK);
        assertThat(testNBMapComponents.getNbComponentID()).isEqualTo(UPDATED_NB_COMPONENT_ID);
        assertThat(testNBMapComponents.getNbComponentType()).isEqualTo(UPDATED_NB_COMPONENT_TYPE);
        assertThat(testNBMapComponents.getNbComponentValue()).isEqualTo(UPDATED_NB_COMPONENT_VALUE);
        assertThat(testNBMapComponents.getNbDefault()).isEqualTo(UPDATED_NB_DEFAULT);
        assertThat(testNBMapComponents.getNbLastUpdated()).isEqualTo(UPDATED_NB_LAST_UPDATED);
        assertThat(testNBMapComponents.getNbLastUpdatedBy()).isEqualTo(UPDATED_NB_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    void putNonExistingNBMapComponents() throws Exception {
        int databaseSizeBeforeUpdate = nBMapComponentsRepository.findAll().size();
        nBMapComponents.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNBMapComponentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nBMapComponents.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nBMapComponents))
            )
            .andExpect(status().isBadRequest());

        // Validate the NBMapComponents in the database
        List<NBMapComponents> nBMapComponentsList = nBMapComponentsRepository.findAll();
        assertThat(nBMapComponentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNBMapComponents() throws Exception {
        int databaseSizeBeforeUpdate = nBMapComponentsRepository.findAll().size();
        nBMapComponents.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNBMapComponentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nBMapComponents))
            )
            .andExpect(status().isBadRequest());

        // Validate the NBMapComponents in the database
        List<NBMapComponents> nBMapComponentsList = nBMapComponentsRepository.findAll();
        assertThat(nBMapComponentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNBMapComponents() throws Exception {
        int databaseSizeBeforeUpdate = nBMapComponentsRepository.findAll().size();
        nBMapComponents.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNBMapComponentsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nBMapComponents))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NBMapComponents in the database
        List<NBMapComponents> nBMapComponentsList = nBMapComponentsRepository.findAll();
        assertThat(nBMapComponentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNBMapComponentsWithPatch() throws Exception {
        // Initialize the database
        nBMapComponentsRepository.saveAndFlush(nBMapComponents);

        int databaseSizeBeforeUpdate = nBMapComponentsRepository.findAll().size();

        // Update the nBMapComponents using partial update
        NBMapComponents partialUpdatedNBMapComponents = new NBMapComponents();
        partialUpdatedNBMapComponents.setId(nBMapComponents.getId());

        partialUpdatedNBMapComponents
            .nbComponentID(UPDATED_NB_COMPONENT_ID)
            .nbComponentType(UPDATED_NB_COMPONENT_TYPE)
            .nbLastUpdated(UPDATED_NB_LAST_UPDATED)
            .nbLastUpdatedBy(UPDATED_NB_LAST_UPDATED_BY);

        restNBMapComponentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNBMapComponents.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNBMapComponents))
            )
            .andExpect(status().isOk());

        // Validate the NBMapComponents in the database
        List<NBMapComponents> nBMapComponentsList = nBMapComponentsRepository.findAll();
        assertThat(nBMapComponentsList).hasSize(databaseSizeBeforeUpdate);
        NBMapComponents testNBMapComponents = nBMapComponentsList.get(nBMapComponentsList.size() - 1);
        assertThat(testNBMapComponents.getNbIDFK()).isEqualTo(DEFAULT_NB_IDFK);
        assertThat(testNBMapComponents.getNbComponentID()).isEqualTo(UPDATED_NB_COMPONENT_ID);
        assertThat(testNBMapComponents.getNbComponentType()).isEqualTo(UPDATED_NB_COMPONENT_TYPE);
        assertThat(testNBMapComponents.getNbComponentValue()).isEqualTo(DEFAULT_NB_COMPONENT_VALUE);
        assertThat(testNBMapComponents.getNbDefault()).isEqualTo(DEFAULT_NB_DEFAULT);
        assertThat(testNBMapComponents.getNbLastUpdated()).isEqualTo(UPDATED_NB_LAST_UPDATED);
        assertThat(testNBMapComponents.getNbLastUpdatedBy()).isEqualTo(UPDATED_NB_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    void fullUpdateNBMapComponentsWithPatch() throws Exception {
        // Initialize the database
        nBMapComponentsRepository.saveAndFlush(nBMapComponents);

        int databaseSizeBeforeUpdate = nBMapComponentsRepository.findAll().size();

        // Update the nBMapComponents using partial update
        NBMapComponents partialUpdatedNBMapComponents = new NBMapComponents();
        partialUpdatedNBMapComponents.setId(nBMapComponents.getId());

        partialUpdatedNBMapComponents
            .nbIDFK(UPDATED_NB_IDFK)
            .nbComponentID(UPDATED_NB_COMPONENT_ID)
            .nbComponentType(UPDATED_NB_COMPONENT_TYPE)
            .nbComponentValue(UPDATED_NB_COMPONENT_VALUE)
            .nbDefault(UPDATED_NB_DEFAULT)
            .nbLastUpdated(UPDATED_NB_LAST_UPDATED)
            .nbLastUpdatedBy(UPDATED_NB_LAST_UPDATED_BY);

        restNBMapComponentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNBMapComponents.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNBMapComponents))
            )
            .andExpect(status().isOk());

        // Validate the NBMapComponents in the database
        List<NBMapComponents> nBMapComponentsList = nBMapComponentsRepository.findAll();
        assertThat(nBMapComponentsList).hasSize(databaseSizeBeforeUpdate);
        NBMapComponents testNBMapComponents = nBMapComponentsList.get(nBMapComponentsList.size() - 1);
        assertThat(testNBMapComponents.getNbIDFK()).isEqualTo(UPDATED_NB_IDFK);
        assertThat(testNBMapComponents.getNbComponentID()).isEqualTo(UPDATED_NB_COMPONENT_ID);
        assertThat(testNBMapComponents.getNbComponentType()).isEqualTo(UPDATED_NB_COMPONENT_TYPE);
        assertThat(testNBMapComponents.getNbComponentValue()).isEqualTo(UPDATED_NB_COMPONENT_VALUE);
        assertThat(testNBMapComponents.getNbDefault()).isEqualTo(UPDATED_NB_DEFAULT);
        assertThat(testNBMapComponents.getNbLastUpdated()).isEqualTo(UPDATED_NB_LAST_UPDATED);
        assertThat(testNBMapComponents.getNbLastUpdatedBy()).isEqualTo(UPDATED_NB_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingNBMapComponents() throws Exception {
        int databaseSizeBeforeUpdate = nBMapComponentsRepository.findAll().size();
        nBMapComponents.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNBMapComponentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nBMapComponents.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nBMapComponents))
            )
            .andExpect(status().isBadRequest());

        // Validate the NBMapComponents in the database
        List<NBMapComponents> nBMapComponentsList = nBMapComponentsRepository.findAll();
        assertThat(nBMapComponentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNBMapComponents() throws Exception {
        int databaseSizeBeforeUpdate = nBMapComponentsRepository.findAll().size();
        nBMapComponents.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNBMapComponentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nBMapComponents))
            )
            .andExpect(status().isBadRequest());

        // Validate the NBMapComponents in the database
        List<NBMapComponents> nBMapComponentsList = nBMapComponentsRepository.findAll();
        assertThat(nBMapComponentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNBMapComponents() throws Exception {
        int databaseSizeBeforeUpdate = nBMapComponentsRepository.findAll().size();
        nBMapComponents.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNBMapComponentsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nBMapComponents))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NBMapComponents in the database
        List<NBMapComponents> nBMapComponentsList = nBMapComponentsRepository.findAll();
        assertThat(nBMapComponentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNBMapComponents() throws Exception {
        // Initialize the database
        nBMapComponentsRepository.saveAndFlush(nBMapComponents);

        int databaseSizeBeforeDelete = nBMapComponentsRepository.findAll().size();

        // Delete the nBMapComponents
        restNBMapComponentsMockMvc
            .perform(delete(ENTITY_API_URL_ID, nBMapComponents.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NBMapComponents> nBMapComponentsList = nBMapComponentsRepository.findAll();
        assertThat(nBMapComponentsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
