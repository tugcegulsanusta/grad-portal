package com.tugceusta.project.domain;

import static com.tugceusta.project.domain.SchoolTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tugceusta.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SchoolTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(School.class);
        School school1 = getSchoolSample1();
        School school2 = new School();
        assertThat(school1).isNotEqualTo(school2);

        school2.setId(school1.getId());
        assertThat(school1).isEqualTo(school2);

        school2 = getSchoolSample2();
        assertThat(school1).isNotEqualTo(school2);
    }
}
