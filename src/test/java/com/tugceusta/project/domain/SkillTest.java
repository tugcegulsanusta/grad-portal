package com.tugceusta.project.domain;

import static com.tugceusta.project.domain.GradTestSamples.*;
import static com.tugceusta.project.domain.SkillTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tugceusta.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SkillTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Skill.class);
        Skill skill1 = getSkillSample1();
        Skill skill2 = new Skill();
        assertThat(skill1).isNotEqualTo(skill2);

        skill2.setId(skill1.getId());
        assertThat(skill1).isEqualTo(skill2);

        skill2 = getSkillSample2();
        assertThat(skill1).isNotEqualTo(skill2);
    }

    @Test
    void gradIdTest() {
        Skill skill = getSkillRandomSampleGenerator();
        Grad gradBack = getGradRandomSampleGenerator();

        skill.setGradId(gradBack);
        assertThat(skill.getGradId()).isEqualTo(gradBack);

        skill.gradId(null);
        assertThat(skill.getGradId()).isNull();
    }
}
