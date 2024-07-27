package com.tugceusta.project.service.impl;

import com.tugceusta.project.domain.Graduation;
import com.tugceusta.project.repository.GraduationRepository;
import com.tugceusta.project.service.GraduationService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.tugceusta.project.domain.Graduation}.
 */
@Service
@Transactional
public class GraduationServiceImpl implements GraduationService {

    private static final Logger log = LoggerFactory.getLogger(GraduationServiceImpl.class);

    private final GraduationRepository graduationRepository;

    public GraduationServiceImpl(GraduationRepository graduationRepository) {
        this.graduationRepository = graduationRepository;
    }

    @Override
    public Graduation save(Graduation graduation) {
        log.debug("Request to save Graduation : {}", graduation);
        return graduationRepository.save(graduation);
    }

    @Override
    public Graduation update(Graduation graduation) {
        log.debug("Request to update Graduation : {}", graduation);
        return graduationRepository.save(graduation);
    }

    @Override
    public Optional<Graduation> partialUpdate(Graduation graduation) {
        log.debug("Request to partially update Graduation : {}", graduation);

        return graduationRepository
            .findById(graduation.getId())
            .map(existingGraduation -> {
                if (graduation.getStartYear() != null) {
                    existingGraduation.setStartYear(graduation.getStartYear());
                }
                if (graduation.getGraduationYear() != null) {
                    existingGraduation.setGraduationYear(graduation.getGraduationYear());
                }
                if (graduation.getGpa() != null) {
                    existingGraduation.setGpa(graduation.getGpa());
                }

                return existingGraduation;
            })
            .map(graduationRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Graduation> findAll(Pageable pageable) {
        log.debug("Request to get all Graduations");
        return graduationRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Graduation> findOne(Long id) {
        log.debug("Request to get Graduation : {}", id);
        return graduationRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Graduation : {}", id);
        graduationRepository.deleteById(id);
    }
}
