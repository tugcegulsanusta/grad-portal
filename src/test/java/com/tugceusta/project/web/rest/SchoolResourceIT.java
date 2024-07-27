package com.tugceusta.project.web.rest;

import static com.tugceusta.project.domain.SchoolAsserts.*;
import static com.tugceusta.project.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tugceusta.project.IntegrationTest;
import com.tugceusta.project.domain.School;
import com.tugceusta.project.repository.SchoolRepository;
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
 * Integration tests for the {@link SchoolResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SchoolResourceIT {

    private static final String DEFAULT_UNIVERSITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_UNIVERSITY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FACULTY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FACULTY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DEPARTMENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTMENT_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/schools";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSchoolMockMvc;

    private School school;

    private School insertedSchool;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static School createEntity(EntityManager em) {
        School school = new School()
            .universityName(DEFAULT_UNIVERSITY_NAME)
            .facultyName(DEFAULT_FACULTY_NAME)
            .departmentName(DEFAULT_DEPARTMENT_NAME);
        return school;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static School createUpdatedEntity(EntityManager em) {
        School school = new School()
            .universityName(UPDATED_UNIVERSITY_NAME)
            .facultyName(UPDATED_FACULTY_NAME)
            .departmentName(UPDATED_DEPARTMENT_NAME);
        return school;
    }

    @BeforeEach
    public void initTest() {
        school = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedSchool != null) {
            schoolRepository.delete(insertedSchool);
            insertedSchool = null;
        }
    }

    @Test
    @Transactional
    void createSchool() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the School
        var returnedSchool = om.readValue(
            restSchoolMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(school)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            School.class
        );

        // Validate the School in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSchoolUpdatableFieldsEquals(returnedSchool, getPersistedSchool(returnedSchool));

        insertedSchool = returnedSchool;
    }

    @Test
    @Transactional
    void createSchoolWithExistingId() throws Exception {
        // Create the School with an existing ID
        school.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchoolMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(school)))
            .andExpect(status().isBadRequest());

        // Validate the School in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkUniversityNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        school.setUniversityName(null);

        // Create the School, which fails.

        restSchoolMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(school)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFacultyNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        school.setFacultyName(null);

        // Create the School, which fails.

        restSchoolMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(school)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDepartmentNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        school.setDepartmentName(null);

        // Create the School, which fails.

        restSchoolMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(school)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSchools() throws Exception {
        // Initialize the database
        insertedSchool = schoolRepository.saveAndFlush(school);

        // Get all the schoolList
        restSchoolMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(school.getId().intValue())))
            .andExpect(jsonPath("$.[*].universityName").value(hasItem(DEFAULT_UNIVERSITY_NAME)))
            .andExpect(jsonPath("$.[*].facultyName").value(hasItem(DEFAULT_FACULTY_NAME)))
            .andExpect(jsonPath("$.[*].departmentName").value(hasItem(DEFAULT_DEPARTMENT_NAME)));
    }

    @Test
    @Transactional
    void getSchool() throws Exception {
        // Initialize the database
        insertedSchool = schoolRepository.saveAndFlush(school);

        // Get the school
        restSchoolMockMvc
            .perform(get(ENTITY_API_URL_ID, school.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(school.getId().intValue()))
            .andExpect(jsonPath("$.universityName").value(DEFAULT_UNIVERSITY_NAME))
            .andExpect(jsonPath("$.facultyName").value(DEFAULT_FACULTY_NAME))
            .andExpect(jsonPath("$.departmentName").value(DEFAULT_DEPARTMENT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingSchool() throws Exception {
        // Get the school
        restSchoolMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSchool() throws Exception {
        // Initialize the database
        insertedSchool = schoolRepository.saveAndFlush(school);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the school
        School updatedSchool = schoolRepository.findById(school.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSchool are not directly saved in db
        em.detach(updatedSchool);
        updatedSchool.universityName(UPDATED_UNIVERSITY_NAME).facultyName(UPDATED_FACULTY_NAME).departmentName(UPDATED_DEPARTMENT_NAME);

        restSchoolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSchool.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSchool))
            )
            .andExpect(status().isOk());

        // Validate the School in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSchoolToMatchAllProperties(updatedSchool);
    }

    @Test
    @Transactional
    void putNonExistingSchool() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        school.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSchoolMockMvc
            .perform(put(ENTITY_API_URL_ID, school.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(school)))
            .andExpect(status().isBadRequest());

        // Validate the School in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSchool() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        school.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchoolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(school))
            )
            .andExpect(status().isBadRequest());

        // Validate the School in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSchool() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        school.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchoolMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(school)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the School in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSchoolWithPatch() throws Exception {
        // Initialize the database
        insertedSchool = schoolRepository.saveAndFlush(school);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the school using partial update
        School partialUpdatedSchool = new School();
        partialUpdatedSchool.setId(school.getId());

        partialUpdatedSchool.universityName(UPDATED_UNIVERSITY_NAME);

        restSchoolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSchool.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSchool))
            )
            .andExpect(status().isOk());

        // Validate the School in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSchoolUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedSchool, school), getPersistedSchool(school));
    }

    @Test
    @Transactional
    void fullUpdateSchoolWithPatch() throws Exception {
        // Initialize the database
        insertedSchool = schoolRepository.saveAndFlush(school);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the school using partial update
        School partialUpdatedSchool = new School();
        partialUpdatedSchool.setId(school.getId());

        partialUpdatedSchool
            .universityName(UPDATED_UNIVERSITY_NAME)
            .facultyName(UPDATED_FACULTY_NAME)
            .departmentName(UPDATED_DEPARTMENT_NAME);

        restSchoolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSchool.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSchool))
            )
            .andExpect(status().isOk());

        // Validate the School in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSchoolUpdatableFieldsEquals(partialUpdatedSchool, getPersistedSchool(partialUpdatedSchool));
    }

    @Test
    @Transactional
    void patchNonExistingSchool() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        school.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSchoolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, school.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(school))
            )
            .andExpect(status().isBadRequest());

        // Validate the School in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSchool() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        school.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchoolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(school))
            )
            .andExpect(status().isBadRequest());

        // Validate the School in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSchool() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        school.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchoolMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(school)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the School in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSchool() throws Exception {
        // Initialize the database
        insertedSchool = schoolRepository.saveAndFlush(school);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the school
        restSchoolMockMvc
            .perform(delete(ENTITY_API_URL_ID, school.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return schoolRepository.count();
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

    protected School getPersistedSchool(School school) {
        return schoolRepository.findById(school.getId()).orElseThrow();
    }

    protected void assertPersistedSchoolToMatchAllProperties(School expectedSchool) {
        assertSchoolAllPropertiesEquals(expectedSchool, getPersistedSchool(expectedSchool));
    }

    protected void assertPersistedSchoolToMatchUpdatableProperties(School expectedSchool) {
        assertSchoolAllUpdatablePropertiesEquals(expectedSchool, getPersistedSchool(expectedSchool));
    }
}
