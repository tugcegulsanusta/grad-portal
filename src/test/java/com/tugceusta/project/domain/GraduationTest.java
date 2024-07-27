package com.tugceusta.project.domain;

import static com.tugceusta.project.domain.GradTestSamples.*;
import static com.tugceusta.project.domain.GraduationTestSamples.*;
import static com.tugceusta.project.domain.SchoolTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tugceusta.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GraduationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Graduation.class);
        Graduation graduation1 = getGraduationSample1();
        Graduation graduation2 = new Graduation();
        assertThat(graduation1).isNotEqualTo(graduation2);

        graduation2.setId(graduation1.getId());
        assertThat(graduation1).isEqualTo(graduation2);

        graduation2 = getGraduationSample2();
        assertThat(graduation1).isNotEqualTo(graduation2);
    }

    @Test
    void schoolIdTest() {
        Graduation graduation = getGraduationRandomSampleGenerator();
        School schoolBack = getSchoolRandomSampleGenerator();

        graduation.setSchoolId(schoolBack);
        assertThat(graduation.getSchoolId()).isEqualTo(schoolBack);

        graduation.schoolId(null);
        assertThat(graduation.getSchoolId()).isNull();
    }

    @Test
    void gradIdTest() {
        Graduation graduation = getGraduationRandomSampleGenerator();
        Grad gradBack = getGradRandomSampleGenerator();

        graduation.setGradId(gradBack);
        assertThat(graduation.getGradId()).isEqualTo(gradBack);

        graduation.gradId(null);
        assertThat(graduation.getGradId()).isNull();
    }
}
