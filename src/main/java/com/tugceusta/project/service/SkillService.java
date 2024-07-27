package com.tugceusta.project.service;

import com.tugceusta.project.domain.Skill;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.tugceusta.project.domain.Skill}.
 */
public interface SkillService {
    /**
     * Save a skill.
     *
     * @param skill the entity to save.
     * @return the persisted entity.
     */
    Skill save(Skill skill);

    /**
     * Updates a skill.
     *
     * @param skill the entity to update.
     * @return the persisted entity.
     */
    Skill update(Skill skill);

    /**
     * Partially updates a skill.
     *
     * @param skill the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Skill> partialUpdate(Skill skill);

    /**
     * Get all the skills.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Skill> findAll(Pageable pageable);

    /**
     * Get the "id" skill.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Skill> findOne(Long id);

    /**
     * Delete the "id" skill.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
