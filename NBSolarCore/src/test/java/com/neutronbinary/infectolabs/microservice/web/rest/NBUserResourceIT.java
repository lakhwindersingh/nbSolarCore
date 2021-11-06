package com.neutronbinary.infectolabs.microservice.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.neutronbinary.infectolabs.microservice.IntegrationTest;
import com.neutronbinary.infectolabs.microservice.domain.NBUser;
import com.neutronbinary.infectolabs.microservice.repository.NBUserRepository;
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
 * Integration tests for the {@link NBUserResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NBUserResourceIT {

    private static final String DEFAULT_NB_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_NB_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NB_AUTH_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_NB_AUTH_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_NB_PASSWORD_HASH = "AAAAAAAAAA";
    private static final String UPDATED_NB_PASSWORD_HASH = "BBBBBBBBBB";

    private static final String DEFAULT_NB_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NB_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NB_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NB_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NB_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_NB_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_NB_EMAIL_ID = "AAAAAAAAAA";
    private static final String UPDATED_NB_EMAIL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NB_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_NB_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_NB_IS_ACTIVE = "AAAAAAAAAA";
    private static final String UPDATED_NB_IS_ACTIVE = "BBBBBBBBBB";

    private static final String DEFAULT_NB_IS_SUSPENDED = "AAAAAAAAAA";
    private static final String UPDATED_NB_IS_SUSPENDED = "BBBBBBBBBB";

    private static final String DEFAULT_NB_IS_BANISHED = "AAAAAAAAAA";
    private static final String UPDATED_NB_IS_BANISHED = "BBBBBBBBBB";

    private static final String DEFAULT_NB_LAST_UPDATED = "AAAAAAAAAA";
    private static final String UPDATED_NB_LAST_UPDATED = "BBBBBBBBBB";

    private static final String DEFAULT_NB_LAST_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_NB_LAST_UPDATED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/nb-users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NBUserRepository nBUserRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNBUserMockMvc;

    private NBUser nBUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NBUser createEntity(EntityManager em) {
        NBUser nBUser = new NBUser()
            .nbUserID(DEFAULT_NB_USER_ID)
            .nbAuthType(DEFAULT_NB_AUTH_TYPE)
            .nbPasswordHash(DEFAULT_NB_PASSWORD_HASH)
            .nbFirstName(DEFAULT_NB_FIRST_NAME)
            .nbLastName(DEFAULT_NB_LAST_NAME)
            .nbAddress(DEFAULT_NB_ADDRESS)
            .nbEmailId(DEFAULT_NB_EMAIL_ID)
            .nbPhone(DEFAULT_NB_PHONE)
            .nbIsActive(DEFAULT_NB_IS_ACTIVE)
            .nbIsSuspended(DEFAULT_NB_IS_SUSPENDED)
            .nbIsBanished(DEFAULT_NB_IS_BANISHED)
            .nbLastUpdated(DEFAULT_NB_LAST_UPDATED)
            .nbLastUpdatedBy(DEFAULT_NB_LAST_UPDATED_BY);
        return nBUser;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NBUser createUpdatedEntity(EntityManager em) {
        NBUser nBUser = new NBUser()
            .nbUserID(UPDATED_NB_USER_ID)
            .nbAuthType(UPDATED_NB_AUTH_TYPE)
            .nbPasswordHash(UPDATED_NB_PASSWORD_HASH)
            .nbFirstName(UPDATED_NB_FIRST_NAME)
            .nbLastName(UPDATED_NB_LAST_NAME)
            .nbAddress(UPDATED_NB_ADDRESS)
            .nbEmailId(UPDATED_NB_EMAIL_ID)
            .nbPhone(UPDATED_NB_PHONE)
            .nbIsActive(UPDATED_NB_IS_ACTIVE)
            .nbIsSuspended(UPDATED_NB_IS_SUSPENDED)
            .nbIsBanished(UPDATED_NB_IS_BANISHED)
            .nbLastUpdated(UPDATED_NB_LAST_UPDATED)
            .nbLastUpdatedBy(UPDATED_NB_LAST_UPDATED_BY);
        return nBUser;
    }

    @BeforeEach
    public void initTest() {
        nBUser = createEntity(em);
    }

    @Test
    @Transactional
    void createNBUser() throws Exception {
        int databaseSizeBeforeCreate = nBUserRepository.findAll().size();
        // Create the NBUser
        restNBUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nBUser)))
            .andExpect(status().isCreated());

        // Validate the NBUser in the database
        List<NBUser> nBUserList = nBUserRepository.findAll();
        assertThat(nBUserList).hasSize(databaseSizeBeforeCreate + 1);
        NBUser testNBUser = nBUserList.get(nBUserList.size() - 1);
        assertThat(testNBUser.getNbUserID()).isEqualTo(DEFAULT_NB_USER_ID);
        assertThat(testNBUser.getNbAuthType()).isEqualTo(DEFAULT_NB_AUTH_TYPE);
        assertThat(testNBUser.getNbPasswordHash()).isEqualTo(DEFAULT_NB_PASSWORD_HASH);
        assertThat(testNBUser.getNbFirstName()).isEqualTo(DEFAULT_NB_FIRST_NAME);
        assertThat(testNBUser.getNbLastName()).isEqualTo(DEFAULT_NB_LAST_NAME);
        assertThat(testNBUser.getNbAddress()).isEqualTo(DEFAULT_NB_ADDRESS);
        assertThat(testNBUser.getNbEmailId()).isEqualTo(DEFAULT_NB_EMAIL_ID);
        assertThat(testNBUser.getNbPhone()).isEqualTo(DEFAULT_NB_PHONE);
        assertThat(testNBUser.getNbIsActive()).isEqualTo(DEFAULT_NB_IS_ACTIVE);
        assertThat(testNBUser.getNbIsSuspended()).isEqualTo(DEFAULT_NB_IS_SUSPENDED);
        assertThat(testNBUser.getNbIsBanished()).isEqualTo(DEFAULT_NB_IS_BANISHED);
        assertThat(testNBUser.getNbLastUpdated()).isEqualTo(DEFAULT_NB_LAST_UPDATED);
        assertThat(testNBUser.getNbLastUpdatedBy()).isEqualTo(DEFAULT_NB_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    void createNBUserWithExistingId() throws Exception {
        // Create the NBUser with an existing ID
        nBUser.setId(1L);

        int databaseSizeBeforeCreate = nBUserRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNBUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nBUser)))
            .andExpect(status().isBadRequest());

        // Validate the NBUser in the database
        List<NBUser> nBUserList = nBUserRepository.findAll();
        assertThat(nBUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNBUsers() throws Exception {
        // Initialize the database
        nBUserRepository.saveAndFlush(nBUser);

        // Get all the nBUserList
        restNBUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nBUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].nbUserID").value(hasItem(DEFAULT_NB_USER_ID)))
            .andExpect(jsonPath("$.[*].nbAuthType").value(hasItem(DEFAULT_NB_AUTH_TYPE)))
            .andExpect(jsonPath("$.[*].nbPasswordHash").value(hasItem(DEFAULT_NB_PASSWORD_HASH)))
            .andExpect(jsonPath("$.[*].nbFirstName").value(hasItem(DEFAULT_NB_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].nbLastName").value(hasItem(DEFAULT_NB_LAST_NAME)))
            .andExpect(jsonPath("$.[*].nbAddress").value(hasItem(DEFAULT_NB_ADDRESS)))
            .andExpect(jsonPath("$.[*].nbEmailId").value(hasItem(DEFAULT_NB_EMAIL_ID)))
            .andExpect(jsonPath("$.[*].nbPhone").value(hasItem(DEFAULT_NB_PHONE)))
            .andExpect(jsonPath("$.[*].nbIsActive").value(hasItem(DEFAULT_NB_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].nbIsSuspended").value(hasItem(DEFAULT_NB_IS_SUSPENDED)))
            .andExpect(jsonPath("$.[*].nbIsBanished").value(hasItem(DEFAULT_NB_IS_BANISHED)))
            .andExpect(jsonPath("$.[*].nbLastUpdated").value(hasItem(DEFAULT_NB_LAST_UPDATED)))
            .andExpect(jsonPath("$.[*].nbLastUpdatedBy").value(hasItem(DEFAULT_NB_LAST_UPDATED_BY)));
    }

    @Test
    @Transactional
    void getNBUser() throws Exception {
        // Initialize the database
        nBUserRepository.saveAndFlush(nBUser);

        // Get the nBUser
        restNBUserMockMvc
            .perform(get(ENTITY_API_URL_ID, nBUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nBUser.getId().intValue()))
            .andExpect(jsonPath("$.nbUserID").value(DEFAULT_NB_USER_ID))
            .andExpect(jsonPath("$.nbAuthType").value(DEFAULT_NB_AUTH_TYPE))
            .andExpect(jsonPath("$.nbPasswordHash").value(DEFAULT_NB_PASSWORD_HASH))
            .andExpect(jsonPath("$.nbFirstName").value(DEFAULT_NB_FIRST_NAME))
            .andExpect(jsonPath("$.nbLastName").value(DEFAULT_NB_LAST_NAME))
            .andExpect(jsonPath("$.nbAddress").value(DEFAULT_NB_ADDRESS))
            .andExpect(jsonPath("$.nbEmailId").value(DEFAULT_NB_EMAIL_ID))
            .andExpect(jsonPath("$.nbPhone").value(DEFAULT_NB_PHONE))
            .andExpect(jsonPath("$.nbIsActive").value(DEFAULT_NB_IS_ACTIVE))
            .andExpect(jsonPath("$.nbIsSuspended").value(DEFAULT_NB_IS_SUSPENDED))
            .andExpect(jsonPath("$.nbIsBanished").value(DEFAULT_NB_IS_BANISHED))
            .andExpect(jsonPath("$.nbLastUpdated").value(DEFAULT_NB_LAST_UPDATED))
            .andExpect(jsonPath("$.nbLastUpdatedBy").value(DEFAULT_NB_LAST_UPDATED_BY));
    }

    @Test
    @Transactional
    void getNonExistingNBUser() throws Exception {
        // Get the nBUser
        restNBUserMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNBUser() throws Exception {
        // Initialize the database
        nBUserRepository.saveAndFlush(nBUser);

        int databaseSizeBeforeUpdate = nBUserRepository.findAll().size();

        // Update the nBUser
        NBUser updatedNBUser = nBUserRepository.findById(nBUser.getId()).get();
        // Disconnect from session so that the updates on updatedNBUser are not directly saved in db
        em.detach(updatedNBUser);
        updatedNBUser
            .nbUserID(UPDATED_NB_USER_ID)
            .nbAuthType(UPDATED_NB_AUTH_TYPE)
            .nbPasswordHash(UPDATED_NB_PASSWORD_HASH)
            .nbFirstName(UPDATED_NB_FIRST_NAME)
            .nbLastName(UPDATED_NB_LAST_NAME)
            .nbAddress(UPDATED_NB_ADDRESS)
            .nbEmailId(UPDATED_NB_EMAIL_ID)
            .nbPhone(UPDATED_NB_PHONE)
            .nbIsActive(UPDATED_NB_IS_ACTIVE)
            .nbIsSuspended(UPDATED_NB_IS_SUSPENDED)
            .nbIsBanished(UPDATED_NB_IS_BANISHED)
            .nbLastUpdated(UPDATED_NB_LAST_UPDATED)
            .nbLastUpdatedBy(UPDATED_NB_LAST_UPDATED_BY);

        restNBUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNBUser.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNBUser))
            )
            .andExpect(status().isOk());

        // Validate the NBUser in the database
        List<NBUser> nBUserList = nBUserRepository.findAll();
        assertThat(nBUserList).hasSize(databaseSizeBeforeUpdate);
        NBUser testNBUser = nBUserList.get(nBUserList.size() - 1);
        assertThat(testNBUser.getNbUserID()).isEqualTo(UPDATED_NB_USER_ID);
        assertThat(testNBUser.getNbAuthType()).isEqualTo(UPDATED_NB_AUTH_TYPE);
        assertThat(testNBUser.getNbPasswordHash()).isEqualTo(UPDATED_NB_PASSWORD_HASH);
        assertThat(testNBUser.getNbFirstName()).isEqualTo(UPDATED_NB_FIRST_NAME);
        assertThat(testNBUser.getNbLastName()).isEqualTo(UPDATED_NB_LAST_NAME);
        assertThat(testNBUser.getNbAddress()).isEqualTo(UPDATED_NB_ADDRESS);
        assertThat(testNBUser.getNbEmailId()).isEqualTo(UPDATED_NB_EMAIL_ID);
        assertThat(testNBUser.getNbPhone()).isEqualTo(UPDATED_NB_PHONE);
        assertThat(testNBUser.getNbIsActive()).isEqualTo(UPDATED_NB_IS_ACTIVE);
        assertThat(testNBUser.getNbIsSuspended()).isEqualTo(UPDATED_NB_IS_SUSPENDED);
        assertThat(testNBUser.getNbIsBanished()).isEqualTo(UPDATED_NB_IS_BANISHED);
        assertThat(testNBUser.getNbLastUpdated()).isEqualTo(UPDATED_NB_LAST_UPDATED);
        assertThat(testNBUser.getNbLastUpdatedBy()).isEqualTo(UPDATED_NB_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    void putNonExistingNBUser() throws Exception {
        int databaseSizeBeforeUpdate = nBUserRepository.findAll().size();
        nBUser.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNBUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nBUser.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nBUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the NBUser in the database
        List<NBUser> nBUserList = nBUserRepository.findAll();
        assertThat(nBUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNBUser() throws Exception {
        int databaseSizeBeforeUpdate = nBUserRepository.findAll().size();
        nBUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNBUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nBUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the NBUser in the database
        List<NBUser> nBUserList = nBUserRepository.findAll();
        assertThat(nBUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNBUser() throws Exception {
        int databaseSizeBeforeUpdate = nBUserRepository.findAll().size();
        nBUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNBUserMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nBUser)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NBUser in the database
        List<NBUser> nBUserList = nBUserRepository.findAll();
        assertThat(nBUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNBUserWithPatch() throws Exception {
        // Initialize the database
        nBUserRepository.saveAndFlush(nBUser);

        int databaseSizeBeforeUpdate = nBUserRepository.findAll().size();

        // Update the nBUser using partial update
        NBUser partialUpdatedNBUser = new NBUser();
        partialUpdatedNBUser.setId(nBUser.getId());

        partialUpdatedNBUser
            .nbUserID(UPDATED_NB_USER_ID)
            .nbPasswordHash(UPDATED_NB_PASSWORD_HASH)
            .nbLastName(UPDATED_NB_LAST_NAME)
            .nbEmailId(UPDATED_NB_EMAIL_ID)
            .nbPhone(UPDATED_NB_PHONE)
            .nbIsActive(UPDATED_NB_IS_ACTIVE)
            .nbIsBanished(UPDATED_NB_IS_BANISHED)
            .nbLastUpdated(UPDATED_NB_LAST_UPDATED);

        restNBUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNBUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNBUser))
            )
            .andExpect(status().isOk());

        // Validate the NBUser in the database
        List<NBUser> nBUserList = nBUserRepository.findAll();
        assertThat(nBUserList).hasSize(databaseSizeBeforeUpdate);
        NBUser testNBUser = nBUserList.get(nBUserList.size() - 1);
        assertThat(testNBUser.getNbUserID()).isEqualTo(UPDATED_NB_USER_ID);
        assertThat(testNBUser.getNbAuthType()).isEqualTo(DEFAULT_NB_AUTH_TYPE);
        assertThat(testNBUser.getNbPasswordHash()).isEqualTo(UPDATED_NB_PASSWORD_HASH);
        assertThat(testNBUser.getNbFirstName()).isEqualTo(DEFAULT_NB_FIRST_NAME);
        assertThat(testNBUser.getNbLastName()).isEqualTo(UPDATED_NB_LAST_NAME);
        assertThat(testNBUser.getNbAddress()).isEqualTo(DEFAULT_NB_ADDRESS);
        assertThat(testNBUser.getNbEmailId()).isEqualTo(UPDATED_NB_EMAIL_ID);
        assertThat(testNBUser.getNbPhone()).isEqualTo(UPDATED_NB_PHONE);
        assertThat(testNBUser.getNbIsActive()).isEqualTo(UPDATED_NB_IS_ACTIVE);
        assertThat(testNBUser.getNbIsSuspended()).isEqualTo(DEFAULT_NB_IS_SUSPENDED);
        assertThat(testNBUser.getNbIsBanished()).isEqualTo(UPDATED_NB_IS_BANISHED);
        assertThat(testNBUser.getNbLastUpdated()).isEqualTo(UPDATED_NB_LAST_UPDATED);
        assertThat(testNBUser.getNbLastUpdatedBy()).isEqualTo(DEFAULT_NB_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    void fullUpdateNBUserWithPatch() throws Exception {
        // Initialize the database
        nBUserRepository.saveAndFlush(nBUser);

        int databaseSizeBeforeUpdate = nBUserRepository.findAll().size();

        // Update the nBUser using partial update
        NBUser partialUpdatedNBUser = new NBUser();
        partialUpdatedNBUser.setId(nBUser.getId());

        partialUpdatedNBUser
            .nbUserID(UPDATED_NB_USER_ID)
            .nbAuthType(UPDATED_NB_AUTH_TYPE)
            .nbPasswordHash(UPDATED_NB_PASSWORD_HASH)
            .nbFirstName(UPDATED_NB_FIRST_NAME)
            .nbLastName(UPDATED_NB_LAST_NAME)
            .nbAddress(UPDATED_NB_ADDRESS)
            .nbEmailId(UPDATED_NB_EMAIL_ID)
            .nbPhone(UPDATED_NB_PHONE)
            .nbIsActive(UPDATED_NB_IS_ACTIVE)
            .nbIsSuspended(UPDATED_NB_IS_SUSPENDED)
            .nbIsBanished(UPDATED_NB_IS_BANISHED)
            .nbLastUpdated(UPDATED_NB_LAST_UPDATED)
            .nbLastUpdatedBy(UPDATED_NB_LAST_UPDATED_BY);

        restNBUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNBUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNBUser))
            )
            .andExpect(status().isOk());

        // Validate the NBUser in the database
        List<NBUser> nBUserList = nBUserRepository.findAll();
        assertThat(nBUserList).hasSize(databaseSizeBeforeUpdate);
        NBUser testNBUser = nBUserList.get(nBUserList.size() - 1);
        assertThat(testNBUser.getNbUserID()).isEqualTo(UPDATED_NB_USER_ID);
        assertThat(testNBUser.getNbAuthType()).isEqualTo(UPDATED_NB_AUTH_TYPE);
        assertThat(testNBUser.getNbPasswordHash()).isEqualTo(UPDATED_NB_PASSWORD_HASH);
        assertThat(testNBUser.getNbFirstName()).isEqualTo(UPDATED_NB_FIRST_NAME);
        assertThat(testNBUser.getNbLastName()).isEqualTo(UPDATED_NB_LAST_NAME);
        assertThat(testNBUser.getNbAddress()).isEqualTo(UPDATED_NB_ADDRESS);
        assertThat(testNBUser.getNbEmailId()).isEqualTo(UPDATED_NB_EMAIL_ID);
        assertThat(testNBUser.getNbPhone()).isEqualTo(UPDATED_NB_PHONE);
        assertThat(testNBUser.getNbIsActive()).isEqualTo(UPDATED_NB_IS_ACTIVE);
        assertThat(testNBUser.getNbIsSuspended()).isEqualTo(UPDATED_NB_IS_SUSPENDED);
        assertThat(testNBUser.getNbIsBanished()).isEqualTo(UPDATED_NB_IS_BANISHED);
        assertThat(testNBUser.getNbLastUpdated()).isEqualTo(UPDATED_NB_LAST_UPDATED);
        assertThat(testNBUser.getNbLastUpdatedBy()).isEqualTo(UPDATED_NB_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingNBUser() throws Exception {
        int databaseSizeBeforeUpdate = nBUserRepository.findAll().size();
        nBUser.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNBUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nBUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nBUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the NBUser in the database
        List<NBUser> nBUserList = nBUserRepository.findAll();
        assertThat(nBUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNBUser() throws Exception {
        int databaseSizeBeforeUpdate = nBUserRepository.findAll().size();
        nBUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNBUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nBUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the NBUser in the database
        List<NBUser> nBUserList = nBUserRepository.findAll();
        assertThat(nBUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNBUser() throws Exception {
        int databaseSizeBeforeUpdate = nBUserRepository.findAll().size();
        nBUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNBUserMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(nBUser)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NBUser in the database
        List<NBUser> nBUserList = nBUserRepository.findAll();
        assertThat(nBUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNBUser() throws Exception {
        // Initialize the database
        nBUserRepository.saveAndFlush(nBUser);

        int databaseSizeBeforeDelete = nBUserRepository.findAll().size();

        // Delete the nBUser
        restNBUserMockMvc
            .perform(delete(ENTITY_API_URL_ID, nBUser.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NBUser> nBUserList = nBUserRepository.findAll();
        assertThat(nBUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
