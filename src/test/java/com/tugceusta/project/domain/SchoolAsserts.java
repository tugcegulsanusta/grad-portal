package com.tugceusta.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class SchoolAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSchoolAllPropertiesEquals(School expected, School actual) {
        assertSchoolAutoGeneratedPropertiesEquals(expected, actual);
        assertSchoolAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSchoolAllUpdatablePropertiesEquals(School expected, School actual) {
        assertSchoolUpdatableFieldsEquals(expected, actual);
        assertSchoolUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSchoolAutoGeneratedPropertiesEquals(School expected, School actual) {
        assertThat(expected)
            .as("Verify School auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSchoolUpdatableFieldsEquals(School expected, School actual) {
        assertThat(expected)
            .as("Verify School relevant properties")
            .satisfies(e -> assertThat(e.getUniversityName()).as("check universityName").isEqualTo(actual.getUniversityName()))
            .satisfies(e -> assertThat(e.getFacultyName()).as("check facultyName").isEqualTo(actual.getFacultyName()))
            .satisfies(e -> assertThat(e.getDepartmentName()).as("check departmentName").isEqualTo(actual.getDepartmentName()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSchoolUpdatableRelationshipsEquals(School expected, School actual) {}
}
