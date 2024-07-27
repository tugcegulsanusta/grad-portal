package com.tugceusta.project.domain;

import static com.tugceusta.project.domain.GradTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tugceusta.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GradTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Grad.class);
        Grad grad1 = getGradSample1();
        Grad grad2 = new Grad();
        assertThat(grad1).isNotEqualTo(grad2);

        grad2.setId(grad1.getId());
        assertThat(grad1).isEqualTo(grad2);

        grad2 = getGradSample2();
        assertThat(grad1).isNotEqualTo(grad2);
    }
}
