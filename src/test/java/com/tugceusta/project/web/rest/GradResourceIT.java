package com.tugceusta.project.web.rest;

import static com.tugceusta.project.domain.GradAsserts.*;
import static com.tugceusta.project.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tugceusta.project.IntegrationTest;
import com.tugceusta.project.domain.Grad;
import com.tugceusta.project.repository.GradRepository;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GradResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GradResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/grads";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GradRepository gradRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGradMockMvc;

    private Grad grad;

    private Grad insertedGrad;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Grad createEntity(EntityManager em) {
        Grad grad = new Grad()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER);
        return grad;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Grad createUpdatedEntity(EntityManager em) {
        Grad grad = new Grad()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER);
        return grad;
    }

    @BeforeEach
    public void initTest() {
        grad = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedGrad != null) {
            gradRepository.delete(insertedGrad);
            insertedGrad = null;
        }
    }

    @Test
    @Transactional
    void createGrad() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Grad
        var returnedGrad = om.readValue(
            restGradMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(grad)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Grad.class
        );

        // Validate the Grad in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertGradUpdatableFieldsEquals(returnedGrad, getPersistedGrad(returnedGrad));

        insertedGrad = returnedGrad;
    }

    @Test
    @Transactional
    void createGradWithExistingId() throws Exception {
        // Create the Grad with an existing ID
        grad.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGradMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(grad)))
            .andExpect(status().isBadRequest());

        // Validate the Grad in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGrads() throws Exception {
        // Initialize the database
        insertedGrad = gradRepository.saveAndFlush(grad);

        // Get all the gradList
        restGradMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grad.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)));
    }

    @Test
    @Transactional
    void getGrad() throws Exception {
        // Initialize the database
        insertedGrad = gradRepository.saveAndFlush(grad);

        // Get the grad
        restGradMockMvc
            .perform(get(ENTITY_API_URL_ID, grad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(grad.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER));
    }

    @Test
    @Transactional
    void getNonExistingGrad() throws Exception {
        // Get the grad
        restGradMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGrad() throws Exception {
        // Initialize the database
        insertedGrad = gradRepository.saveAndFlush(grad);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the grad
        Grad updatedGrad = gradRepository.findById(grad.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedGrad are not directly saved in db
        em.detach(updatedGrad);
        updatedGrad.firstName(UPDATED_FIRST_NAME).lastName(UPDATED_LAST_NAME).email(UPDATED_EMAIL).phoneNumber(UPDATED_PHONE_NUMBER);

        restGradMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGrad.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedGrad))
            )
            .andExpect(status().isOk());

        // Validate the Grad in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGradToMatchAllProperties(updatedGrad);
    }

    @Test
    @Transactional
    void putNonExistingGrad() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grad.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGradMockMvc
            .perform(put(ENTITY_API_URL_ID, grad.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(grad)))
            .andExpect(status().isBadRequest());

        // Validate the Grad in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGrad() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grad.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGradMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(grad))
            )
            .andExpect(status().isBadRequest());

        // Validate the Grad in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGrad() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grad.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGradMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(grad)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Grad in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGradWithPatch() throws Exception {
        // Initialize the database
        insertedGrad = gradRepository.saveAndFlush(grad);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the grad using partial update
        Grad partialUpdatedGrad = new Grad();
        partialUpdatedGrad.setId(grad.getId());

        partialUpdatedGrad.email(UPDATED_EMAIL).phoneNumber(UPDATED_PHONE_NUMBER);

        restGradMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGrad.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGrad))
            )
            .andExpect(status().isOk());

        // Validate the Grad in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGradUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedGrad, grad), getPersistedGrad(grad));
    }

    @Test
    @Transactional
    void fullUpdateGradWithPatch() throws Exception {
        // Initialize the database
        insertedGrad = gradRepository.saveAndFlush(grad);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the grad using partial update
        Grad partialUpdatedGrad = new Grad();
        partialUpdatedGrad.setId(grad.getId());

        partialUpdatedGrad.firstName(UPDATED_FIRST_NAME).lastName(UPDATED_LAST_NAME).email(UPDATED_EMAIL).phoneNumber(UPDATED_PHONE_NUMBER);

        restGradMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGrad.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGrad))
            )
            .andExpect(status().isOk());

        // Validate the Grad in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGradUpdatableFieldsEquals(partialUpdatedGrad, getPersistedGrad(partialUpdatedGrad));
    }

    @Test
    @Transactional
    void patchNonExistingGrad() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grad.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGradMockMvc
            .perform(patch(ENTITY_API_URL_ID, grad.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(grad)))
            .andExpect(status().isBadRequest());

        // Validate the Grad in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGrad() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grad.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGradMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(grad))
            )
            .andExpect(status().isBadRequest());

        // Validate the Grad in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGrad() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grad.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGradMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(grad)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Grad in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGrad() throws Exception {
        // Initialize the database
        insertedGrad = gradRepository.saveAndFlush(grad);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the grad
        restGradMockMvc
            .perform(delete(ENTITY_API_URL_ID, grad.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return gradRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Grad getPersistedGrad(Grad grad) {
        return gradRepository.findById(grad.getId()).orElseThrow();
    }

    protected void assertPersistedGradToMatchAllProperties(Grad expectedGrad) {
        assertGradAllPropertiesEquals(expectedGrad, getPersistedGrad(expectedGrad));
    }

    protected void assertPersistedGradToMatchUpdatableProperties(Grad expectedGrad) {
        assertGradAllUpdatablePropertiesEquals(expectedGrad, getPersistedGrad(expectedGrad));
    }
}
