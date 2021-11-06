package com.neutronbinary.infectolabs.gateway.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.neutronbinary.infectolabs.gateway.IntegrationTest;
import com.neutronbinary.infectolabs.gateway.domain.NBPalette;
import com.neutronbinary.infectolabs.gateway.repository.NBPaletteRepository;
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
 * Integration tests for the {@link NBPaletteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient
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
    private WebTestClient webTestClient;

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

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(NBPalette.class).block();
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
        nBPalette = createEntity(em);
    }

    @Test
    void createNBPalette() throws Exception {
        int databaseSizeBeforeCreate = nBPaletteRepository.findAll().collectList().block().size();
        // Create the NBPalette
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(nBPalette))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the NBPalette in the database
        List<NBPalette> nBPaletteList = nBPaletteRepository.findAll().collectList().block();
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
    void createNBPaletteWithExistingId() throws Exception {
        // Create the NBPalette with an existing ID
        nBPalette.setId(1L);

        int databaseSizeBeforeCreate = nBPaletteRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(nBPalette))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the NBPalette in the database
        List<NBPalette> nBPaletteList = nBPaletteRepository.findAll().collectList().block();
        assertThat(nBPaletteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllNBPalettesAsStream() {
        // Initialize the database
        nBPaletteRepository.save(nBPalette).block();

        List<NBPalette> nBPaletteList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(NBPalette.class)
            .getResponseBody()
            .filter(nBPalette::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(nBPaletteList).isNotNull();
        assertThat(nBPaletteList).hasSize(1);
        NBPalette testNBPalette = nBPaletteList.get(0);
        assertThat(testNBPalette.getNbPaletteID()).isEqualTo(DEFAULT_NB_PALETTE_ID);
        assertThat(testNBPalette.getNbPaletteTitle()).isEqualTo(DEFAULT_NB_PALETTE_TITLE);
        assertThat(testNBPalette.getNbPaletteType()).isEqualTo(DEFAULT_NB_PALETTE_TYPE);
        assertThat(testNBPalette.getNbPaletteColors()).isEqualTo(DEFAULT_NB_PALETTE_COLORS);
        assertThat(testNBPalette.getNbLastUpdated()).isEqualTo(DEFAULT_NB_LAST_UPDATED);
        assertThat(testNBPalette.getNbLastUpdatedBy()).isEqualTo(DEFAULT_NB_LAST_UPDATED_BY);
    }

    @Test
    void getAllNBPalettes() {
        // Initialize the database
        nBPaletteRepository.save(nBPalette).block();

        // Get all the nBPaletteList
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
            .value(hasItem(nBPalette.getId().intValue()))
            .jsonPath("$.[*].nbPaletteID")
            .value(hasItem(DEFAULT_NB_PALETTE_ID))
            .jsonPath("$.[*].nbPaletteTitle")
            .value(hasItem(DEFAULT_NB_PALETTE_TITLE))
            .jsonPath("$.[*].nbPaletteType")
            .value(hasItem(DEFAULT_NB_PALETTE_TYPE))
            .jsonPath("$.[*].nbPaletteColors")
            .value(hasItem(DEFAULT_NB_PALETTE_COLORS))
            .jsonPath("$.[*].nbLastUpdated")
            .value(hasItem(DEFAULT_NB_LAST_UPDATED))
            .jsonPath("$.[*].nbLastUpdatedBy")
            .value(hasItem(DEFAULT_NB_LAST_UPDATED_BY));
    }

    @Test
    void getNBPalette() {
        // Initialize the database
        nBPaletteRepository.save(nBPalette).block();

        // Get the nBPalette
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, nBPalette.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(nBPalette.getId().intValue()))
            .jsonPath("$.nbPaletteID")
            .value(is(DEFAULT_NB_PALETTE_ID))
            .jsonPath("$.nbPaletteTitle")
            .value(is(DEFAULT_NB_PALETTE_TITLE))
            .jsonPath("$.nbPaletteType")
            .value(is(DEFAULT_NB_PALETTE_TYPE))
            .jsonPath("$.nbPaletteColors")
            .value(is(DEFAULT_NB_PALETTE_COLORS))
            .jsonPath("$.nbLastUpdated")
            .value(is(DEFAULT_NB_LAST_UPDATED))
            .jsonPath("$.nbLastUpdatedBy")
            .value(is(DEFAULT_NB_LAST_UPDATED_BY));
    }

    @Test
    void getNonExistingNBPalette() {
        // Get the nBPalette
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putNewNBPalette() throws Exception {
        // Initialize the database
        nBPaletteRepository.save(nBPalette).block();

        int databaseSizeBeforeUpdate = nBPaletteRepository.findAll().collectList().block().size();

        // Update the nBPalette
        NBPalette updatedNBPalette = nBPaletteRepository.findById(nBPalette.getId()).block();
        updatedNBPalette
            .nbPaletteID(UPDATED_NB_PALETTE_ID)
            .nbPaletteTitle(UPDATED_NB_PALETTE_TITLE)
            .nbPaletteType(UPDATED_NB_PALETTE_TYPE)
            .nbPaletteColors(UPDATED_NB_PALETTE_COLORS)
            .nbLastUpdated(UPDATED_NB_LAST_UPDATED)
            .nbLastUpdatedBy(UPDATED_NB_LAST_UPDATED_BY);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedNBPalette.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedNBPalette))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the NBPalette in the database
        List<NBPalette> nBPaletteList = nBPaletteRepository.findAll().collectList().block();
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
    void putNonExistingNBPalette() throws Exception {
        int databaseSizeBeforeUpdate = nBPaletteRepository.findAll().collectList().block().size();
        nBPalette.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, nBPalette.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(nBPalette))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the NBPalette in the database
        List<NBPalette> nBPaletteList = nBPaletteRepository.findAll().collectList().block();
        assertThat(nBPaletteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchNBPalette() throws Exception {
        int databaseSizeBeforeUpdate = nBPaletteRepository.findAll().collectList().block().size();
        nBPalette.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(nBPalette))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the NBPalette in the database
        List<NBPalette> nBPaletteList = nBPaletteRepository.findAll().collectList().block();
        assertThat(nBPaletteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamNBPalette() throws Exception {
        int databaseSizeBeforeUpdate = nBPaletteRepository.findAll().collectList().block().size();
        nBPalette.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(nBPalette))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the NBPalette in the database
        List<NBPalette> nBPaletteList = nBPaletteRepository.findAll().collectList().block();
        assertThat(nBPaletteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateNBPaletteWithPatch() throws Exception {
        // Initialize the database
        nBPaletteRepository.save(nBPalette).block();

        int databaseSizeBeforeUpdate = nBPaletteRepository.findAll().collectList().block().size();

        // Update the nBPalette using partial update
        NBPalette partialUpdatedNBPalette = new NBPalette();
        partialUpdatedNBPalette.setId(nBPalette.getId());

        partialUpdatedNBPalette
            .nbPaletteID(UPDATED_NB_PALETTE_ID)
            .nbPaletteTitle(UPDATED_NB_PALETTE_TITLE)
            .nbPaletteColors(UPDATED_NB_PALETTE_COLORS)
            .nbLastUpdatedBy(UPDATED_NB_LAST_UPDATED_BY);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedNBPalette.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedNBPalette))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the NBPalette in the database
        List<NBPalette> nBPaletteList = nBPaletteRepository.findAll().collectList().block();
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
    void fullUpdateNBPaletteWithPatch() throws Exception {
        // Initialize the database
        nBPaletteRepository.save(nBPalette).block();

        int databaseSizeBeforeUpdate = nBPaletteRepository.findAll().collectList().block().size();

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

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedNBPalette.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedNBPalette))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the NBPalette in the database
        List<NBPalette> nBPaletteList = nBPaletteRepository.findAll().collectList().block();
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
    void patchNonExistingNBPalette() throws Exception {
        int databaseSizeBeforeUpdate = nBPaletteRepository.findAll().collectList().block().size();
        nBPalette.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, nBPalette.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(nBPalette))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the NBPalette in the database
        List<NBPalette> nBPaletteList = nBPaletteRepository.findAll().collectList().block();
        assertThat(nBPaletteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchNBPalette() throws Exception {
        int databaseSizeBeforeUpdate = nBPaletteRepository.findAll().collectList().block().size();
        nBPalette.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(nBPalette))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the NBPalette in the database
        List<NBPalette> nBPaletteList = nBPaletteRepository.findAll().collectList().block();
        assertThat(nBPaletteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamNBPalette() throws Exception {
        int databaseSizeBeforeUpdate = nBPaletteRepository.findAll().collectList().block().size();
        nBPalette.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(nBPalette))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the NBPalette in the database
        List<NBPalette> nBPaletteList = nBPaletteRepository.findAll().collectList().block();
        assertThat(nBPaletteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteNBPalette() {
        // Initialize the database
        nBPaletteRepository.save(nBPalette).block();

        int databaseSizeBeforeDelete = nBPaletteRepository.findAll().collectList().block().size();

        // Delete the nBPalette
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, nBPalette.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<NBPalette> nBPaletteList = nBPaletteRepository.findAll().collectList().block();
        assertThat(nBPaletteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
