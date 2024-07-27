package com.tugceusta.project.web.rest;

import static com.tugceusta.project.domain.GraduationAsserts.*;
import static com.tugceusta.project.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tugceusta.project.IntegrationTest;
import com.tugceusta.project.domain.Graduation;
import com.tugceusta.project.repository.GraduationRepository;
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
 * Integration tests for the {@link GraduationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GraduationResourceIT {

    private static final Integer DEFAULT_START_YEAR = 1;
    private static final Integer UPDATED_START_YEAR = 2;

    private static final Integer DEFAULT_GRADUATION_YEAR = 1;
    private static final Integer UPDATED_GRADUATION_YEAR = 2;

    private static final Double DEFAULT_GPA = 1D;
    private static final Double UPDATED_GPA = 2D;

    private static final String ENTITY_API_URL = "/api/graduations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GraduationRepository graduationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGraduationMockMvc;

    private Graduation graduation;

    private Graduation insertedGraduation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Graduation createEntity(EntityManager em) {
        Graduation graduation = new Graduation().startYear(DEFAULT_START_YEAR).graduationYear(DEFAULT_GRADUATION_YEAR).gpa(DEFAULT_GPA);
        return graduation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Graduation createUpdatedEntity(EntityManager em) {
        Graduation graduation = new Graduation().startYear(UPDATED_START_YEAR).graduationYear(UPDATED_GRADUATION_YEAR).gpa(UPDATED_GPA);
        return graduation;
    }

    @BeforeEach
    public void initTest() {
        graduation = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedGraduation != null) {
            graduationRepository.delete(insertedGraduation);
            insertedGraduation = null;
        }
    }

    @Test
    @Transactional
    void createGraduation() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Graduation
        var returnedGraduation = om.readValue(
            restGraduationMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(graduation)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Graduation.class
        );

        // Validate the Graduation in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertGraduationUpdatableFieldsEquals(returnedGraduation, getPersistedGraduation(returnedGraduation));

        insertedGraduation = returnedGraduation;
    }

    @Test
    @Transactional
    void createGraduationWithExistingId() throws Exception {
        // Create the Graduation with an existing ID
        graduation.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGraduationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(graduation)))
            .andExpect(status().isBadRequest());

        // Validate the Graduation in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkGraduationYearIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        graduation.setGraduationYear(null);

        // Create the Graduation, which fails.

        restGraduationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(graduation)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllGraduations() throws Exception {
        // Initialize the database
        insertedGraduation = graduationRepository.saveAndFlush(graduation);

        // Get all the graduationList
        restGraduationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(graduation.getId().intValue())))
            .andExpect(jsonPath("$.[*].startYear").value(hasItem(DEFAULT_START_YEAR)))
            .andExpect(jsonPath("$.[*].graduationYear").value(hasItem(DEFAULT_GRADUATION_YEAR)))
            .andExpect(jsonPath("$.[*].gpa").value(hasItem(DEFAULT_GPA.doubleValue())));
    }

    @Test
    @Transactional
    void getGraduation() throws Exception {
        // Initialize the database
        insertedGraduation = graduationRepository.saveAndFlush(graduation);

        // Get the graduation
        restGraduationMockMvc
            .perform(get(ENTITY_API_URL_ID, graduation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(graduation.getId().intValue()))
            .andExpect(jsonPath("$.startYear").value(DEFAULT_START_YEAR))
            .andExpect(jsonPath("$.graduationYear").value(DEFAULT_GRADUATION_YEAR))
            .andExpect(jsonPath("$.gpa").value(DEFAULT_GPA.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingGraduation() throws Exception {
        // Get the graduation
        restGraduationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGraduation() throws Exception {
        // Initialize the database
        insertedGraduation = graduationRepository.saveAndFlush(graduation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the graduation
        Graduation updatedGraduation = graduationRepository.findById(graduation.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedGraduation are not directly saved in db
        em.detach(updatedGraduation);
        updatedGraduation.startYear(UPDATED_START_YEAR).graduationYear(UPDATED_GRADUATION_YEAR).gpa(UPDATED_GPA);

        restGraduationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGraduation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedGraduation))
            )
            .andExpect(status().isOk());

        // Validate the Graduation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGraduationToMatchAllProperties(updatedGraduation);
    }

    @Test
    @Transactional
    void putNonExistingGraduation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        graduation.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGraduationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, graduation.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(graduation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Graduation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGraduation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        graduation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGraduationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(graduation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Graduation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGraduation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        graduation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGraduationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(graduation)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Graduation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGraduationWithPatch() throws Exception {
        // Initialize the database
        insertedGraduation = graduationRepository.saveAndFlush(graduation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the graduation using partial update
        Graduation partialUpdatedGraduation = new Graduation();
        partialUpdatedGraduation.setId(graduation.getId());

        partialUpdatedGraduation.startYear(UPDATED_START_YEAR).graduationYear(UPDATED_GRADUATION_YEAR).gpa(UPDATED_GPA);

        restGraduationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGraduation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGraduation))
            )
            .andExpect(status().isOk());

        // Validate the Graduation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGraduationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedGraduation, graduation),
            getPersistedGraduation(graduation)
        );
    }

    @Test
    @Transactional
    void fullUpdateGraduationWithPatch() throws Exception {
        // Initialize the database
        insertedGraduation = graduationRepository.saveAndFlush(graduation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the graduation using partial update
        Graduation partialUpdatedGraduation = new Graduation();
        partialUpdatedGraduation.setId(graduation.getId());

        partialUpdatedGraduation.startYear(UPDATED_START_YEAR).graduationYear(UPDATED_GRADUATION_YEAR).gpa(UPDATED_GPA);

        restGraduationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGraduation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGraduation))
            )
            .andExpect(status().isOk());

        // Validate the Graduation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGraduationUpdatableFieldsEquals(partialUpdatedGraduation, getPersistedGraduation(partialUpdatedGraduation));
    }

    @Test
    @Transactional
    void patchNonExistingGraduation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        graduation.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGraduationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, graduation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(graduation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Graduation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGraduation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        graduation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGraduationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(graduation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Graduation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGraduation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        graduation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGraduationMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(graduation)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Graduation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGraduation() throws Exception {
        // Initialize the database
        insertedGraduation = graduationRepository.saveAndFlush(graduation);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the graduation
        restGraduationMockMvc
            .perform(delete(ENTITY_API_URL_ID, graduation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return graduationRepository.count();
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

    protected Graduation getPersistedGraduation(Graduation graduation) {
        return graduationRepository.findById(graduation.getId()).orElseThrow();
    }

    protected void assertPersistedGraduationToMatchAllProperties(Graduation expectedGraduation) {
        assertGraduationAllPropertiesEquals(expectedGraduation, getPersistedGraduation(expectedGraduation));
    }

    protected void assertPersistedGraduationToMatchUpdatableProperties(Graduation expectedGraduation) {
        assertGraduationAllUpdatablePropertiesEquals(expectedGraduation, getPersistedGraduation(expectedGraduation));
    }
}
