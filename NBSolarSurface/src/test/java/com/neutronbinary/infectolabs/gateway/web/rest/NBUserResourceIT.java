package com.neutronbinary.infectolabs.gateway.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.neutronbinary.infectolabs.gateway.IntegrationTest;
import com.neutronbinary.infectolabs.gateway.domain.NBUser;
import com.neutronbinary.infectolabs.gateway.repository.NBUserRepository;
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
 * Integration tests for the {@link NBUserResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient
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
    private WebTestClient webTestClient;

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

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(NBUser.class).block();
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
        nBUser = createEntity(em);
    }

    @Test
    void createNBUser() throws Exception {
        int databaseSizeBeforeCreate = nBUserRepository.findAll().collectList().block().size();
        // Create the NBUser
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(nBUser))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the NBUser in the database
        List<NBUser> nBUserList = nBUserRepository.findAll().collectList().block();
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
    void createNBUserWithExistingId() throws Exception {
        // Create the NBUser with an existing ID
        nBUser.setId(1L);

        int databaseSizeBeforeCreate = nBUserRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(nBUser))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the NBUser in the database
        List<NBUser> nBUserList = nBUserRepository.findAll().collectList().block();
        assertThat(nBUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllNBUsersAsStream() {
        // Initialize the database
        nBUserRepository.save(nBUser).block();

        List<NBUser> nBUserList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(NBUser.class)
            .getResponseBody()
            .filter(nBUser::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(nBUserList).isNotNull();
        assertThat(nBUserList).hasSize(1);
        NBUser testNBUser = nBUserList.get(0);
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
    void getAllNBUsers() {
        // Initialize the database
        nBUserRepository.save(nBUser).block();

        // Get all the nBUserList
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
            .value(hasItem(nBUser.getId().intValue()))
            .jsonPath("$.[*].nbUserID")
            .value(hasItem(DEFAULT_NB_USER_ID))
            .jsonPath("$.[*].nbAuthType")
            .value(hasItem(DEFAULT_NB_AUTH_TYPE))
            .jsonPath("$.[*].nbPasswordHash")
            .value(hasItem(DEFAULT_NB_PASSWORD_HASH))
            .jsonPath("$.[*].nbFirstName")
            .value(hasItem(DEFAULT_NB_FIRST_NAME))
            .jsonPath("$.[*].nbLastName")
            .value(hasItem(DEFAULT_NB_LAST_NAME))
            .jsonPath("$.[*].nbAddress")
            .value(hasItem(DEFAULT_NB_ADDRESS))
            .jsonPath("$.[*].nbEmailId")
            .value(hasItem(DEFAULT_NB_EMAIL_ID))
            .jsonPath("$.[*].nbPhone")
            .value(hasItem(DEFAULT_NB_PHONE))
            .jsonPath("$.[*].nbIsActive")
            .value(hasItem(DEFAULT_NB_IS_ACTIVE))
            .jsonPath("$.[*].nbIsSuspended")
            .value(hasItem(DEFAULT_NB_IS_SUSPENDED))
            .jsonPath("$.[*].nbIsBanished")
            .value(hasItem(DEFAULT_NB_IS_BANISHED))
            .jsonPath("$.[*].nbLastUpdated")
            .value(hasItem(DEFAULT_NB_LAST_UPDATED))
            .jsonPath("$.[*].nbLastUpdatedBy")
            .value(hasItem(DEFAULT_NB_LAST_UPDATED_BY));
    }

    @Test
    void getNBUser() {
        // Initialize the database
        nBUserRepository.save(nBUser).block();

        // Get the nBUser
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, nBUser.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(nBUser.getId().intValue()))
            .jsonPath("$.nbUserID")
            .value(is(DEFAULT_NB_USER_ID))
            .jsonPath("$.nbAuthType")
            .value(is(DEFAULT_NB_AUTH_TYPE))
            .jsonPath("$.nbPasswordHash")
            .value(is(DEFAULT_NB_PASSWORD_HASH))
            .jsonPath("$.nbFirstName")
            .value(is(DEFAULT_NB_FIRST_NAME))
            .jsonPath("$.nbLastName")
            .value(is(DEFAULT_NB_LAST_NAME))
            .jsonPath("$.nbAddress")
            .value(is(DEFAULT_NB_ADDRESS))
            .jsonPath("$.nbEmailId")
            .value(is(DEFAULT_NB_EMAIL_ID))
            .jsonPath("$.nbPhone")
            .value(is(DEFAULT_NB_PHONE))
            .jsonPath("$.nbIsActive")
            .value(is(DEFAULT_NB_IS_ACTIVE))
            .jsonPath("$.nbIsSuspended")
            .value(is(DEFAULT_NB_IS_SUSPENDED))
            .jsonPath("$.nbIsBanished")
            .value(is(DEFAULT_NB_IS_BANISHED))
            .jsonPath("$.nbLastUpdated")
            .value(is(DEFAULT_NB_LAST_UPDATED))
            .jsonPath("$.nbLastUpdatedBy")
            .value(is(DEFAULT_NB_LAST_UPDATED_BY));
    }

    @Test
    void getNonExistingNBUser() {
        // Get the nBUser
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putNewNBUser() throws Exception {
        // Initialize the database
        nBUserRepository.save(nBUser).block();

        int databaseSizeBeforeUpdate = nBUserRepository.findAll().collectList().block().size();

        // Update the nBUser
        NBUser updatedNBUser = nBUserRepository.findById(nBUser.getId()).block();
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

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedNBUser.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedNBUser))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the NBUser in the database
        List<NBUser> nBUserList = nBUserRepository.findAll().collectList().block();
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
    void putNonExistingNBUser() throws Exception {
        int databaseSizeBeforeUpdate = nBUserRepository.findAll().collectList().block().size();
        nBUser.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, nBUser.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(nBUser))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the NBUser in the database
        List<NBUser> nBUserList = nBUserRepository.findAll().collectList().block();
        assertThat(nBUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchNBUser() throws Exception {
        int databaseSizeBeforeUpdate = nBUserRepository.findAll().collectList().block().size();
        nBUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(nBUser))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the NBUser in the database
        List<NBUser> nBUserList = nBUserRepository.findAll().collectList().block();
        assertThat(nBUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamNBUser() throws Exception {
        int databaseSizeBeforeUpdate = nBUserRepository.findAll().collectList().block().size();
        nBUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(nBUser))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the NBUser in the database
        List<NBUser> nBUserList = nBUserRepository.findAll().collectList().block();
        assertThat(nBUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateNBUserWithPatch() throws Exception {
        // Initialize the database
        nBUserRepository.save(nBUser).block();

        int databaseSizeBeforeUpdate = nBUserRepository.findAll().collectList().block().size();

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

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedNBUser.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedNBUser))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the NBUser in the database
        List<NBUser> nBUserList = nBUserRepository.findAll().collectList().block();
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
    void fullUpdateNBUserWithPatch() throws Exception {
        // Initialize the database
        nBUserRepository.save(nBUser).block();

        int databaseSizeBeforeUpdate = nBUserRepository.findAll().collectList().block().size();

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

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedNBUser.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedNBUser))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the NBUser in the database
        List<NBUser> nBUserList = nBUserRepository.findAll().collectList().block();
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
    void patchNonExistingNBUser() throws Exception {
        int databaseSizeBeforeUpdate = nBUserRepository.findAll().collectList().block().size();
        nBUser.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, nBUser.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(nBUser))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the NBUser in the database
        List<NBUser> nBUserList = nBUserRepository.findAll().collectList().block();
        assertThat(nBUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchNBUser() throws Exception {
        int databaseSizeBeforeUpdate = nBUserRepository.findAll().collectList().block().size();
        nBUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(nBUser))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the NBUser in the database
        List<NBUser> nBUserList = nBUserRepository.findAll().collectList().block();
        assertThat(nBUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamNBUser() throws Exception {
        int databaseSizeBeforeUpdate = nBUserRepository.findAll().collectList().block().size();
        nBUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(nBUser))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the NBUser in the database
        List<NBUser> nBUserList = nBUserRepository.findAll().collectList().block();
        assertThat(nBUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteNBUser() {
        // Initialize the database
        nBUserRepository.save(nBUser).block();

        int databaseSizeBeforeDelete = nBUserRepository.findAll().collectList().block().size();

        // Delete the nBUser
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, nBUser.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<NBUser> nBUserList = nBUserRepository.findAll().collectList().block();
        assertThat(nBUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
