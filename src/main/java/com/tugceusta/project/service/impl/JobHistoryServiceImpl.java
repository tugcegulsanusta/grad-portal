package com.tugceusta.project.service.impl;

import com.tugceusta.project.domain.JobHistory;
import com.tugceusta.project.repository.JobHistoryRepository;
import com.tugceusta.project.service.JobHistoryService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.tugceusta.project.domain.JobHistory}.
 */
@Service
@Transactional
public class JobHistoryServiceImpl implements JobHistoryService {

    private static final Logger log = LoggerFactory.getLogger(JobHistoryServiceImpl.class);

    private final JobHistoryRepository jobHistoryRepository;

    public JobHistoryServiceImpl(JobHistoryRepository jobHistoryRepository) {
        this.jobHistoryRepository = jobHistoryRepository;
    }

    @Override
    public JobHistory save(JobHistory jobHistory) {
        log.debug("Request to save JobHistory : {}", jobHistory);
        return jobHistoryRepository.save(jobHistory);
    }

    @Override
    public JobHistory update(JobHistory jobHistory) {
        log.debug("Request to update JobHistory : {}", jobHistory);
        return jobHistoryRepository.save(jobHistory);
    }

    @Override
    public Optional<JobHistory> partialUpdate(JobHistory jobHistory) {
        log.debug("Request to partially update JobHistory : {}", jobHistory);

        return jobHistoryRepository
            .findById(jobHistory.getId())
            .map(existingJobHistory -> {
                if (jobHistory.getCompanyName() != null) {
                    existingJobHistory.setCompanyName(jobHistory.getCompanyName());
                }
                if (jobHistory.getJobTitle() != null) {
                    existingJobHistory.setJobTitle(jobHistory.getJobTitle());
                }
                if (jobHistory.getStartDate() != null) {
                    existingJobHistory.setStartDate(jobHistory.getStartDate());
                }
                if (jobHistory.getEndDate() != null) {
                    existingJobHistory.setEndDate(jobHistory.getEndDate());
                }
                if (jobHistory.getIsCurrent() != null) {
                    existingJobHistory.setIsCurrent(jobHistory.getIsCurrent());
                }

                return existingJobHistory;
            })
            .map(jobHistoryRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<JobHistory> findAll(Pageable pageable) {
        log.debug("Request to get all JobHistories");
        return jobHistoryRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<JobHistory> findOne(Long id) {
        log.debug("Request to get JobHistory : {}", id);
        return jobHistoryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete JobHistory : {}", id);
        jobHistoryRepository.deleteById(id);
    }
}
