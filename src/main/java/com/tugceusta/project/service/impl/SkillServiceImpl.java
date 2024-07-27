package com.tugceusta.project.service.impl;

import com.tugceusta.project.domain.Skill;
import com.tugceusta.project.repository.SkillRepository;
import com.tugceusta.project.service.SkillService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.tugceusta.project.domain.Skill}.
 */
@Service
@Transactional
public class SkillServiceImpl implements SkillService {

    private static final Logger log = LoggerFactory.getLogger(SkillServiceImpl.class);

    private final SkillRepository skillRepository;

    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    public Skill save(Skill skill) {
        log.debug("Request to save Skill : {}", skill);
        return skillRepository.save(skill);
    }

    @Override
    public Skill update(Skill skill) {
        log.debug("Request to update Skill : {}", skill);
        return skillRepository.save(skill);
    }

    @Override
    public Optional<Skill> partialUpdate(Skill skill) {
        log.debug("Request to partially update Skill : {}", skill);

        return skillRepository
            .findById(skill.getId())
            .map(existingSkill -> {
                if (skill.getName() != null) {
                    existingSkill.setName(skill.getName());
                }
                if (skill.getDescription() != null) {
                    existingSkill.setDescription(skill.getDescription());
                }
                if (skill.getRate() != null) {
                    existingSkill.setRate(skill.getRate());
                }
                if (skill.getOrder() != null) {
                    existingSkill.setOrder(skill.getOrder());
                }

                return existingSkill;
            })
            .map(skillRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Skill> findAll(Pageable pageable) {
        log.debug("Request to get all Skills");
        return skillRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Skill> findOne(Long id) {
        log.debug("Request to get Skill : {}", id);
        return skillRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Skill : {}", id);
        skillRepository.deleteById(id);
    }
}
