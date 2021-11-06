package com.neutronbinary.infectolabs.gateway.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.neutronbinary.infectolabs.gateway.IntegrationTest;
import com.neutronbinary.infectolabs.gateway.domain.NBMap;
import com.neutronbinary.infectolabs.gateway.repository.NBMapRepository;
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
 * Integration tests for the {@link NBMapResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient
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
    private WebTestClient webTestClient;

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

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(NBMap.class).block();
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
        nBMap = createEntity(em);
    }

    @Test
    void createNBMap() throws Exception {
        int databaseSizeBeforeCreate = nBMapRepository.findAll().collectList().block().size();
        // Create the NBMap
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(nBMap))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the NBMap in the database
        List<NBMap> nBMapList = nBMapRepository.findAll().collectList().block();
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
    void createNBMapWithExistingId() throws Exception {
        // Create the NBMap with an existing ID
        nBMap.setId(1L);

        int databaseSizeBeforeCreate = nBMapRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(nBMap))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the NBMap in the database
        List<NBMap> nBMapList = nBMapRepository.findAll().collectList().block();
        assertThat(nBMapList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllNBMapsAsStream() {
        // Initialize the database
        nBMapRepository.save(nBMap).block();

        List<NBMap> nBMapList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(NBMap.class)
            .getResponseBody()
            .filter(nBMap::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(nBMapList).isNotNull();
        assertThat(nBMapList).hasSize(1);
        NBMap testNBMap = nBMapList.get(0);
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
    void getAllNBMaps() {
        // Initialize the database
        nBMapRepository.save(nBMap).block();

        // Get all the nBMapList
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
            .value(hasItem(nBMap.getId().intValue()))
            .jsonPath("$.[*].nbID")
            .value(hasItem(DEFAULT_NB_ID))
            .jsonPath("$.[*].nbName")
            .value(hasItem(DEFAULT_NB_NAME))
            .jsonPath("$.[*].nbOwner")
            .value(hasItem(DEFAULT_NB_OWNER))
            .jsonPath("$.[*].nbOwnerPrivateKey")
            .value(hasItem(DEFAULT_NB_OWNER_PRIVATE_KEY))
            .jsonPath("$.[*].nbOwnerPublicKey")
            .value(hasItem(DEFAULT_NB_OWNER_PUBLIC_KEY))
            .jsonPath("$.[*].nbMapPublishMethod")
            .value(hasItem(DEFAULT_NB_MAP_PUBLISH_METHOD))
            .jsonPath("$.[*].nbSubscriptionDate")
            .value(hasItem(DEFAULT_NB_SUBSCRIPTION_DATE))
            .jsonPath("$.[*].nbSubscriptionLastDate")
            .value(hasItem(DEFAULT_NB_SUBSCRIPTION_LAST_DATE))
            .jsonPath("$.[*].nbLastUpdated")
            .value(hasItem(DEFAULT_NB_LAST_UPDATED))
            .jsonPath("$.[*].nbLastUpdatedBy")
            .value(hasItem(DEFAULT_NB_LAST_UPDATED_BY));
    }

    @Test
    void getNBMap() {
        // Initialize the database
        nBMapRepository.save(nBMap).block();

        // Get the nBMap
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, nBMap.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(nBMap.getId().intValue()))
            .jsonPath("$.nbID")
            .value(is(DEFAULT_NB_ID))
            .jsonPath("$.nbName")
            .value(is(DEFAULT_NB_NAME))
            .jsonPath("$.nbOwner")
            .value(is(DEFAULT_NB_OWNER))
            .jsonPath("$.nbOwnerPrivateKey")
            .value(is(DEFAULT_NB_OWNER_PRIVATE_KEY))
            .jsonPath("$.nbOwnerPublicKey")
            .value(is(DEFAULT_NB_OWNER_PUBLIC_KEY))
            .jsonPath("$.nbMapPublishMethod")
            .value(is(DEFAULT_NB_MAP_PUBLISH_METHOD))
            .jsonPath("$.nbSubscriptionDate")
            .value(is(DEFAULT_NB_SUBSCRIPTION_DATE))
            .jsonPath("$.nbSubscriptionLastDate")
            .value(is(DEFAULT_NB_SUBSCRIPTION_LAST_DATE))
            .jsonPath("$.nbLastUpdated")
            .value(is(DEFAULT_NB_LAST_UPDATED))
            .jsonPath("$.nbLastUpdatedBy")
            .value(is(DEFAULT_NB_LAST_UPDATED_BY));
    }

    @Test
    void getNonExistingNBMap() {
        // Get the nBMap
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putNewNBMap() throws Exception {
        // Initialize the database
        nBMapRepository.save(nBMap).block();

        int databaseSizeBeforeUpdate = nBMapRepository.findAll().collectList().block().size();

        // Update the nBMap
        NBMap updatedNBMap = nBMapRepository.findById(nBMap.getId()).block();
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

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedNBMap.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedNBMap))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the NBMap in the database
        List<NBMap> nBMapList = nBMapRepository.findAll().collectList().block();
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
    void putNonExistingNBMap() throws Exception {
        int databaseSizeBeforeUpdate = nBMapRepository.findAll().collectList().block().size();
        nBMap.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, nBMap.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(nBMap))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the NBMap in the database
        List<NBMap> nBMapList = nBMapRepository.findAll().collectList().block();
        assertThat(nBMapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchNBMap() throws Exception {
        int databaseSizeBeforeUpdate = nBMapRepository.findAll().collectList().block().size();
        nBMap.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(nBMap))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the NBMap in the database
        List<NBMap> nBMapList = nBMapRepository.findAll().collectList().block();
        assertThat(nBMapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamNBMap() throws Exception {
        int databaseSizeBeforeUpdate = nBMapRepository.findAll().collectList().block().size();
        nBMap.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(nBMap))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the NBMap in the database
        List<NBMap> nBMapList = nBMapRepository.findAll().collectList().block();
        assertThat(nBMapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateNBMapWithPatch() throws Exception {
        // Initialize the database
        nBMapRepository.save(nBMap).block();

        int databaseSizeBeforeUpdate = nBMapRepository.findAll().collectList().block().size();

        // Update the nBMap using partial update
        NBMap partialUpdatedNBMap = new NBMap();
        partialUpdatedNBMap.setId(nBMap.getId());

        partialUpdatedNBMap
            .nbOwnerPrivateKey(UPDATED_NB_OWNER_PRIVATE_KEY)
            .nbOwnerPublicKey(UPDATED_NB_OWNER_PUBLIC_KEY)
            .nbMapPublishMethod(UPDATED_NB_MAP_PUBLISH_METHOD)
            .nbSubscriptionLastDate(UPDATED_NB_SUBSCRIPTION_LAST_DATE)
            .nbLastUpdatedBy(UPDATED_NB_LAST_UPDATED_BY);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedNBMap.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedNBMap))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the NBMap in the database
        List<NBMap> nBMapList = nBMapRepository.findAll().collectList().block();
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
    void fullUpdateNBMapWithPatch() throws Exception {
        // Initialize the database
        nBMapRepository.save(nBMap).block();

        int databaseSizeBeforeUpdate = nBMapRepository.findAll().collectList().block().size();

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

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedNBMap.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedNBMap))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the NBMap in the database
        List<NBMap> nBMapList = nBMapRepository.findAll().collectList().block();
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
    void patchNonExistingNBMap() throws Exception {
        int databaseSizeBeforeUpdate = nBMapRepository.findAll().collectList().block().size();
        nBMap.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, nBMap.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(nBMap))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the NBMap in the database
        List<NBMap> nBMapList = nBMapRepository.findAll().collectList().block();
        assertThat(nBMapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchNBMap() throws Exception {
        int databaseSizeBeforeUpdate = nBMapRepository.findAll().collectList().block().size();
        nBMap.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(nBMap))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the NBMap in the database
        List<NBMap> nBMapList = nBMapRepository.findAll().collectList().block();
        assertThat(nBMapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamNBMap() throws Exception {
        int databaseSizeBeforeUpdate = nBMapRepository.findAll().collectList().block().size();
        nBMap.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(nBMap))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the NBMap in the database
        List<NBMap> nBMapList = nBMapRepository.findAll().collectList().block();
        assertThat(nBMapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteNBMap() {
        // Initialize the database
        nBMapRepository.save(nBMap).block();

        int databaseSizeBeforeDelete = nBMapRepository.findAll().collectList().block().size();

        // Delete the nBMap
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, nBMap.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<NBMap> nBMapList = nBMapRepository.findAll().collectList().block();
        assertThat(nBMapList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
