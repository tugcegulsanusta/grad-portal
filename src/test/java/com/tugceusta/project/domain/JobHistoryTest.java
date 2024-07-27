package com.tugceusta.project.domain;

import static com.tugceusta.project.domain.GradTestSamples.*;
import static com.tugceusta.project.domain.JobHistoryTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tugceusta.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JobHistoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobHistory.class);
        JobHistory jobHistory1 = getJobHistorySample1();
        JobHistory jobHistory2 = new JobHistory();
        assertThat(jobHistory1).isNotEqualTo(jobHistory2);

        jobHistory2.setId(jobHistory1.getId());
        assertThat(jobHistory1).isEqualTo(jobHistory2);

        jobHistory2 = getJobHistorySample2();
        assertThat(jobHistory1).isNotEqualTo(jobHistory2);
    }

    @Test
    void gradIdTest() {
        JobHistory jobHistory = getJobHistoryRandomSampleGenerator();
        Grad gradBack = getGradRandomSampleGenerator();

        jobHistory.setGradId(gradBack);
        assertThat(jobHistory.getGradId()).isEqualTo(gradBack);

        jobHistory.gradId(null);
        assertThat(jobHistory.getGradId()).isNull();
    }
}
