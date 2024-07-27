package com.tugceusta.project.service.impl;

import com.tugceusta.project.domain.Grad;
import com.tugceusta.project.repository.GradRepository;
import com.tugceusta.project.service.GradService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.tugceusta.project.domain.Grad}.
 */
@Service
@Transactional
public class GradServiceImpl implements GradService {

    private static final Logger log = LoggerFactory.getLogger(GradServiceImpl.class);

    private final GradRepository gradRepository;

    public GradServiceImpl(GradRepository gradRepository) {
        this.gradRepository = gradRepository;
    }

    @Override
    public Grad save(Grad grad) {
        log.debug("Request to save Grad : {}", grad);
        return gradRepository.save(grad);
    }

    @Override
    public Grad update(Grad grad) {
        log.debug("Request to update Grad : {}", grad);
        return gradRepository.save(grad);
    }

    @Override
    public Optional<Grad> partialUpdate(Grad grad) {
        log.debug("Request to partially update Grad : {}", grad);

        return gradRepository
            .findById(grad.getId())
            .map(existingGrad -> {
                if (grad.getFirstName() != null) {
                    existingGrad.setFirstName(grad.getFirstName());
                }
                if (grad.getLastName() != null) {
                    existingGrad.setLastName(grad.getLastName());
                }
                if (grad.getEmail() != null) {
                    existingGrad.setEmail(grad.getEmail());
                }
                if (grad.getPhoneNumber() != null) {
                    existingGrad.setPhoneNumber(grad.getPhoneNumber());
                }

                return existingGrad;
            })
            .map(gradRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Grad> findAll(Pageable pageable) {
        log.debug("Request to get all Grads");
        return gradRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Grad> findOne(Long id) {
        log.debug("Request to get Grad : {}", id);
        return gradRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Grad : {}", id);
        gradRepository.deleteById(id);
    }
}
