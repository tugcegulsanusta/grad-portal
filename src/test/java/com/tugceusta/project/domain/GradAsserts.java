package com.tugceusta.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class GradAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertGradAllPropertiesEquals(Grad expected, Grad actual) {
        assertGradAutoGeneratedPropertiesEquals(expected, actual);
        assertGradAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertGradAllUpdatablePropertiesEquals(Grad expected, Grad actual) {
        assertGradUpdatableFieldsEquals(expected, actual);
        assertGradUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertGradAutoGeneratedPropertiesEquals(Grad expected, Grad actual) {
        assertThat(expected)
            .as("Verify Grad auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertGradUpdatableFieldsEquals(Grad expected, Grad actual) {
        assertThat(expected)
            .as("Verify Grad relevant properties")
            .satisfies(e -> assertThat(e.getFirstName()).as("check firstName").isEqualTo(actual.getFirstName()))
            .satisfies(e -> assertThat(e.getLastName()).as("check lastName").isEqualTo(actual.getLastName()))
            .satisfies(e -> assertThat(e.getEmail()).as("check email").isEqualTo(actual.getEmail()))
            .satisfies(e -> assertThat(e.getPhoneNumber()).as("check phoneNumber").isEqualTo(actual.getPhoneNumber()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertGradUpdatableRelationshipsEquals(Grad expected, Grad actual) {}
}