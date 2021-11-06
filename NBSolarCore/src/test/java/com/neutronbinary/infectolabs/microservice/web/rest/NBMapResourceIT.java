package com.neutronbinary.infectolabs.microservice.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.neutronbinary.infectolabs.microservice.IntegrationTest;
import com.neutronbinary.infectolabs.microservice.domain.NBMap;
import com.neutronbinary.infectolabs.microservice.repository.NBMapRepository;
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
 * Integration tests for the {@link NBMapResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NBMapResourceIT {

    private static final String DEFAULT_NB_ID = "AAAAAAAAAA";
    private static final String UPDATED_NB_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NB_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NB_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NB_OWNER = "AAAAAAAAAA";
    private static final String UPDATED_NB_OWNER = "BBBBBBBBBB";

    private static final String DEFAULT_NB_OWNER_PRIVATE_KEY = "AAAAAAAAAA";
    private static final String UPDATED_NB_OWNER_PRIVATE_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_NB_OWNER_PUBLIC_KEY = "AAAAAAAAAA";
    private static final String UPDATED_NB_OWNER_PUBLIC_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_NB_MAP_PUBLISH_METHOD = "AAAAAAAAAA";
    private static final String UPDATED_NB_MAP_PUBLISH_METHOD = "BBBBBBBBBB";

    private static final String DEFAULT_NB_SUBSCRIPTION_DATE = "AAAAAAAAAA";
    private static final String UPDATED_NB_SUBSCRIPTION_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_NB_SUBSCRIPTION_LAST_DATE = "AAAAAAAAAA";
    private static final String UPDATED_NB_SUBSCRIPTION_LAST_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_NB_LAST_UPDATED = "AAAAAAAAAA";
    private static final String UPDATED_NB_LAST_UPDATED = "BBBBBBBBBB";

    private static final String DEFAULT_NB_LAST_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_NB_LAST_UPDATED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/nb-maps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NBMapRepository nBMapRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNBMapMockMvc;

    private NBMap nBMap;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NBMap createEntity(EntityManager em) {
        NBMap nBMap = new NBMap()
            .nbID(DEFAULT_NB_ID)
            .nbName(DEFAULT_NB_NAME)
            .nbOwner(DEFAULT_NB_OWNER)
            .nbOwnerPrivateKey(DEFAULT_NB_OWNER_PRIVATE_KEY)
            .nbOwnerPublicKey(DEFAULT_NB_OWNER_PUBLIC_KEY)
            .nbMapPublishMethod(DEFAULT_NB_MAP_PUBLISH_METHOD)
            .nbSubscriptionDate(DEFAULT_NB_SUBSCRIPTION_DATE)
            .nbSubscriptionLastDate(DEFAULT_NB_SUBSCRIPTION_LAST_DATE)
            .nbLastUpdated(DEFAULT_NB_LAST_UPDATED)
            .nbLastUpdatedBy(DEFAULT_NB_LAST_UPDATED_BY);
        return nBMap;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NBMap createUpdatedEntity(EntityManager em) {
        NBMap nBMap = new NBMap()
            .nbID(UPDATED_NB_ID)
            .nbName(UPDATED_NB_NAME)
            .nbOwner(UPDATED_NB_OWNER)
            .nbOwnerPrivateKey(UPDATED_NB_OWNER_PRIVATE_KEY)
            .nbOwnerPublicKey(UPDATED_NB_OWNER_PUBLIC_KEY)
            .nbMapPublishMethod(UPDATED_NB_MAP_PUBLISH_METHOD)
            .nbSubscriptionDate(UPDATED_NB_SUBSCRIPTION_DATE)
            .nbSubscriptionLastDate(UPDATED_NB_SUBSCRIPTION_LAST_DATE)
            .nbLastUpdated(UPDATED_NB_LAST_UPDATED)
            .nbLastUpdatedBy(UPDATED_NB_LAST_UPDATED_BY);
        return nBMap;
    }

    @BeforeEach
    public void initTest() {
        nBMap = createEntity(em);
    }

    @Test
    @Transactional
    void createNBMap() throws Exception {
        int databaseSizeBeforeCreate = nBMapRepository.findAll().size();
        // Create the NBMap
        restNBMapMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nBMap)))
            .andExpect(status().isCreated());

        // Validate the NBMap in the database
        List<NBMap> nBMapList = nBMapRepository.findAll();
        assertThat(nBMapList).hasSize(databaseSizeBeforeCreate + 1);
        NBMap testNBMap = nBMapList.get(nBMapList.size() - 1);
        assertThat(testNBMap.getNbID()).isEqualTo(DEFAULT_NB_ID);
        assertThat(testNBMap.getNbName()).isEqualTo(DEFAULT_NB_NAME);
        assertThat(testNBMap.getNbOwner()).isEqualTo(DEFAULT_NB_OWNER);
        assertThat(testNBMap.getNbOwnerPrivateKey()).isEqualTo(DEFAULT_NB_OWNER_PRIVATE_KEY);
        assertThat(testNBMap.getNbOwnerPublicKey()).isEqualTo(DEFAULT_NB_OWNER_PUBLIC_KEY);
        assertThat(testNBMap.getNbMapPublishMethod()).isEqualTo(DEFAULT_NB_MAP_PUBLISH_METHOD);
        assertThat(testNBMap.getNbSubscriptionDate()).isEqualTo(DEFAULT_NB_SUBSCRIPTION_DATE);
        assertThat(testNBMap.getNbSubscriptionLastDate()).isEqualTo(DEFAULT_NB_SUBSCRIPTION_LAST_DATE);
        assertThat(testNBMap.getNbLastUpdated()).isEqualTo(DEFAULT_NB_LAST_UPDATED);
        assertThat(testNBMap.getNbLastUpdatedBy()).isEqualTo(DEFAULT_NB_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    void createNBMapWithExistingId() throws Exception {
        // Create the NBMap with an existing ID
        nBMap.setId(1L);

        int databaseSizeBeforeCreate = nBMapRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNBMapMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nBMap)))
            .andExpect(status().isBadRequest());

        // Validate the NBMap in the database
        List<NBMap> nBMapList = nBMapRepository.findAll();
        assertThat(nBMapList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNBMaps() throws Exception {
        // Initialize the database
        nBMapRepository.saveAndFlush(nBMap);

        // Get all the nBMapList
        restNBMapMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nBMap.getId().intValue())))
            .andExpect(jsonPath("$.[*].nbID").value(hasItem(DEFAULT_NB_ID)))
            .andExpect(jsonPath("$.[*].nbName").value(hasItem(DEFAULT_NB_NAME)))
            .andExpect(jsonPath("$.[*].nbOwner").value(hasItem(DEFAULT_NB_OWNER)))
            .andExpect(jsonPath("$.[*].nbOwnerPrivateKey").value(hasItem(DEFAULT_NB_OWNER_PRIVATE_KEY)))
            .andExpect(jsonPath("$.[*].nbOwnerPublicKey").value(hasItem(DEFAULT_NB_OWNER_PUBLIC_KEY)))
            .andExpect(jsonPath("$.[*].nbMapPublishMethod").value(hasItem(DEFAULT_NB_MAP_PUBLISH_METHOD)))
            .andExpect(jsonPath("$.[*].nbSubscriptionDate").value(hasItem(DEFAULT_NB_SUBSCRIPTION_DATE)))
            .andExpect(jsonPath("$.[*].nbSubscriptionLastDate").value(hasItem(DEFAULT_NB_SUBSCRIPTION_LAST_DATE)))
            .andExpect(jsonPath("$.[*].nbLastUpdated").value(hasItem(DEFAULT_NB_LAST_UPDATED)))
            .andExpect(jsonPath("$.[*].nbLastUpdatedBy").value(hasItem(DEFAULT_NB_LAST_UPDATED_BY)));
    }

    @Test
    @Transactional
    void getNBMap() throws Exception {
        // Initialize the database
        nBMapRepository.saveAndFlush(nBMap);

        // Get the nBMap
        restNBMapMockMvc
            .perform(get(ENTITY_API_URL_ID, nBMap.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nBMap.getId().intValue()))
            .andExpect(jsonPath("$.nbID").value(DEFAULT_NB_ID))
            .andExpect(jsonPath("$.nbName").value(DEFAULT_NB_NAME))
            .andExpect(jsonPath("$.nbOwner").value(DEFAULT_NB_OWNER))
            .andExpect(jsonPath("$.nbOwnerPrivateKey").value(DEFAULT_NB_OWNER_PRIVATE_KEY))
            .andExpect(jsonPath("$.nbOwnerPublicKey").value(DEFAULT_NB_OWNER_PUBLIC_KEY))
            .andExpect(jsonPath("$.nbMapPublishMethod").value(DEFAULT_NB_MAP_PUBLISH_METHOD))
            .andExpect(jsonPath("$.nbSubscriptionDate").value(DEFAULT_NB_SUBSCRIPTION_DATE))
            .andExpect(jsonPath("$.nbSubscriptionLastDate").value(DEFAULT_NB_SUBSCRIPTION_LAST_DATE))
            .andExpect(jsonPath("$.nbLastUpdated").value(DEFAULT_NB_LAST_UPDATED))
            .andExpect(jsonPath("$.nbLastUpdatedBy").value(DEFAULT_NB_LAST_UPDATED_BY));
    }

    @Test
    @Transactional
    void getNonExistingNBMap() throws Exception {
        // Get the nBMap
        restNBMapMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNBMap() throws Exception {
        // Initialize the database
        nBMapRepository.saveAndFlush(nBMap);

        int databaseSizeBeforeUpdate = nBMapRepository.findAll().size();

        // Update the nBMap
        NBMap updatedNBMap = nBMapRepository.findById(nBMap.getId()).get();
        // Disconnect from session so that the updates on updatedNBMap are not directly saved in db
        em.detach(updatedNBMap);
        updatedNBMap
            .nbID(UPDATED_NB_ID)
            .nbName(UPDATED_NB_NAME)
            .nbOwner(UPDATED_NB_OWNER)
            .nbOwnerPrivateKey(UPDATED_NB_OWNER_PRIVATE_KEY)
            .nbOwnerPublicKey(UPDATED_NB_OWNER_PUBLIC_KEY)
            .nbMapPublishMethod(UPDATED_NB_MAP_PUBLISH_METHOD)
            .nbSubscriptionDate(UPDATED_NB_SUBSCRIPTION_DATE)
            .nbSubscriptionLastDate(UPDATED_NB_SUBSCRIPTION_LAST_DATE)
            .nbLastUpdated(UPDATED_NB_LAST_UPDATED)
            .nbLastUpdatedBy(UPDATED_NB_LAST_UPDATED_BY);

        restNBMapMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNBMap.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNBMap))
            )
            .andExpect(status().isOk());

        // Validate the NBMap in the database
        List<NBMap> nBMapList = nBMapRepository.findAll();
        assertThat(nBMapList).hasSize(databaseSizeBeforeUpdate);
        NBMap testNBMap = nBMapList.get(nBMapList.size() - 1);
        assertThat(testNBMap.getNbID()).isEqualTo(UPDATED_NB_ID);
        assertThat(testNBMap.getNbName()).isEqualTo(UPDATED_NB_NAME);
        assertThat(testNBMap.getNbOwner()).isEqualTo(UPDATED_NB_OWNER);
        assertThat(testNBMap.getNbOwnerPrivateKey()).isEqualTo(UPDATED_NB_OWNER_PRIVATE_KEY);
        assertThat(testNBMap.getNbOwnerPublicKey()).isEqualTo(UPDATED_NB_OWNER_PUBLIC_KEY);
        assertThat(testNBMap.getNbMapPublishMethod()).isEqualTo(UPDATED_NB_MAP_PUBLISH_METHOD);
        assertThat(testNBMap.getNbSubscriptionDate()).isEqualTo(UPDATED_NB_SUBSCRIPTION_DATE);
        assertThat(testNBMap.getNbSubscriptionLastDate()).isEqualTo(UPDATED_NB_SUBSCRIPTION_LAST_DATE);
        assertThat(testNBMap.getNbLastUpdated()).isEqualTo(UPDATED_NB_LAST_UPDATED);
        assertThat(testNBMap.getNbLastUpdatedBy()).isEqualTo(UPDATED_NB_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    void putNonExistingNBMap() throws Exception {
        int databaseSizeBeforeUpdate = nBMapRepository.findAll().size();
        nBMap.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNBMapMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nBMap.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nBMap))
            )
            .andExpect(status().isBadRequest());

        // Validate the NBMap in the database
        List<NBMap> nBMapList = nBMapRepository.findAll();
        assertThat(nBMapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNBMap() throws Exception {
        int databaseSizeBeforeUpdate = nBMapRepository.findAll().size();
        nBMap.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNBMapMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nBMap))
            )
            .andExpect(status().isBadRequest());

        // Validate the NBMap in the database
        List<NBMap> nBMapList = nBMapRepository.findAll();
        assertThat(nBMapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNBMap() throws Exception {
        int databaseSizeBeforeUpdate = nBMapRepository.findAll().size();
        nBMap.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNBMapMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nBMap)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NBMap in the database
        List<NBMap> nBMapList = nBMapRepository.findAll();
        assertThat(nBMapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNBMapWithPatch() throws Exception {
        // Initialize the database
        nBMapRepository.saveAndFlush(nBMap);

        int databaseSizeBeforeUpdate = nBMapRepository.findAll().size();

        // Update the nBMap using partial update
        NBMap partialUpdatedNBMap = new NBMap();
        partialUpdatedNBMap.setId(nBMap.getId());

        partialUpdatedNBMap
            .nbOwnerPrivateKey(UPDATED_NB_OWNER_PRIVATE_KEY)
            .nbOwnerPublicKey(UPDATED_NB_OWNER_PUBLIC_KEY)
            .nbMapPublishMethod(UPDATED_NB_MAP_PUBLISH_METHOD)
            .nbSubscriptionLastDate(UPDATED_NB_SUBSCRIPTION_LAST_DATE)
            .nbLastUpdatedBy(UPDATED_NB_LAST_UPDATED_BY);

        restNBMapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNBMap.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNBMap))
            )
            .andExpect(status().isOk());

        // Validate the NBMap in the database
        List<NBMap> nBMapList = nBMapRepository.findAll();
        assertThat(nBMapList).hasSize(databaseSizeBeforeUpdate);
        NBMap testNBMap = nBMapList.get(nBMapList.size() - 1);
        assertThat(testNBMap.getNbID()).isEqualTo(DEFAULT_NB_ID);
        assertThat(testNBMap.getNbName()).isEqualTo(DEFAULT_NB_NAME);
        assertThat(testNBMap.getNbOwner()).isEqualTo(DEFAULT_NB_OWNER);
        assertThat(testNBMap.getNbOwnerPrivateKey()).isEqualTo(UPDATED_NB_OWNER_PRIVATE_KEY);
        assertThat(testNBMap.getNbOwnerPublicKey()).isEqualTo(UPDATED_NB_OWNER_PUBLIC_KEY);
        assertThat(testNBMap.getNbMapPublishMethod()).isEqualTo(UPDATED_NB_MAP_PUBLISH_METHOD);
        assertThat(testNBMap.getNbSubscriptionDate()).isEqualTo(DEFAULT_NB_SUBSCRIPTION_DATE);
        assertThat(testNBMap.getNbSubscriptionLastDate()).isEqualTo(UPDATED_NB_SUBSCRIPTION_LAST_DATE);
        assertThat(testNBMap.getNbLastUpdated()).isEqualTo(DEFAULT_NB_LAST_UPDATED);
        assertThat(testNBMap.getNbLastUpdatedBy()).isEqualTo(UPDATED_NB_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    void fullUpdateNBMapWithPatch() throws Exception {
        // Initialize the database
        nBMapRepository.saveAndFlush(nBMap);

        int databaseSizeBeforeUpdate = nBMapRepository.findAll().size();

        // Update the nBMap using partial update
        NBMap partialUpdatedNBMap = new NBMap();
        partialUpdatedNBMap.setId(nBMap.getId());

        partialUpdatedNBMap
            .nbID(UPDATED_NB_ID)
            .nbName(UPDATED_NB_NAME)
            .nbOwner(UPDATED_NB_OWNER)
            .nbOwnerPrivateKey(UPDATED_NB_OWNER_PRIVATE_KEY)
            .nbOwnerPublicKey(UPDATED_NB_OWNER_PUBLIC_KEY)
            .nbMapPublishMethod(UPDATED_NB_MAP_PUBLISH_METHOD)
            .nbSubscriptionDate(UPDATED_NB_SUBSCRIPTION_DATE)
            .nbSubscriptionLastDate(UPDATED_NB_SUBSCRIPTION_LAST_DATE)
            .nbLastUpdated(UPDATED_NB_LAST_UPDATED)
            .nbLastUpdatedBy(UPDATED_NB_LAST_UPDATED_BY);

        restNBMapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNBMap.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNBMap))
            )
            .andExpect(status().isOk());

        // Validate the NBMap in the database
        List<NBMap> nBMapList = nBMapRepository.findAll();
        assertThat(nBMapList).hasSize(databaseSizeBeforeUpdate);
        NBMap testNBMap = nBMapList.get(nBMapList.size() - 1);
        assertThat(testNBMap.getNbID()).isEqualTo(UPDATED_NB_ID);
        assertThat(testNBMap.getNbName()).isEqualTo(UPDATED_NB_NAME);
        assertThat(testNBMap.getNbOwner()).isEqualTo(UPDATED_NB_OWNER);
        assertThat(testNBMap.getNbOwnerPrivateKey()).isEqualTo(UPDATED_NB_OWNER_PRIVATE_KEY);
        assertThat(testNBMap.getNbOwnerPublicKey()).isEqualTo(UPDATED_NB_OWNER_PUBLIC_KEY);
        assertThat(testNBMap.getNbMapPublishMethod()).isEqualTo(UPDATED_NB_MAP_PUBLISH_METHOD);
        assertThat(testNBMap.getNbSubscriptionDate()).isEqualTo(UPDATED_NB_SUBSCRIPTION_DATE);
        assertThat(testNBMap.getNbSubscriptionLastDate()).isEqualTo(UPDATED_NB_SUBSCRIPTION_LAST_DATE);
        assertThat(testNBMap.getNbLastUpdated()).isEqualTo(UPDATED_NB_LAST_UPDATED);
        assertThat(testNBMap.getNbLastUpdatedBy()).isEqualTo(UPDATED_NB_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingNBMap() throws Exception {
        int databaseSizeBeforeUpdate = nBMapRepository.findAll().size();
        nBMap.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNBMapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nBMap.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nBMap))
            )
            .andExpect(status().isBadRequest());

        // Validate the NBMap in the database
        List<NBMap> nBMapList = nBMapRepository.findAll();
        assertThat(nBMapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNBMap() throws Exception {
        int databaseSizeBeforeUpdate = nBMapRepository.findAll().size();
        nBMap.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNBMapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nBMap))
            )
            .andExpect(status().isBadRequest());

        // Validate the NBMap in the database
        List<NBMap> nBMapList = nBMapRepository.findAll();
        assertThat(nBMapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNBMap() throws Exception {
        int databaseSizeBeforeUpdate = nBMapRepository.findAll().size();
        nBMap.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNBMapMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(nBMap)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NBMap in the database
        List<NBMap> nBMapList = nBMapRepository.findAll();
        assertThat(nBMapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNBMap() throws Exception {
        // Initialize the database
        nBMapRepository.saveAndFlush(nBMap);

        int databaseSizeBeforeDelete = nBMapRepository.findAll().size();

        // Delete the nBMap
        restNBMapMockMvc
            .perform(delete(ENTITY_API_URL_ID, nBMap.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NBMap> nBMapList = nBMapRepository.findAll();
        assertThat(nBMapList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
