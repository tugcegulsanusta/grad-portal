package com.tugceusta.project.service;

import com.tugceusta.project.domain.Graduation;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.tugceusta.project.domain.Graduation}.
 */
public interface GraduationService {
    /**
     * Save a graduation.
     *
     * @param graduation the entity to save.
     * @return the persisted entity.
     */
    Graduation save(Graduation graduation);

    /**
     * Updates a graduation.
     *
     * @param graduation the entity to update.
     * @return the persisted entity.
     */
    Graduation update(Graduation graduation);

    /**
     * Partially updates a graduation.
     *
     * @param graduation the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Graduation> partialUpdate(Graduation graduation);

    /**
     * Get all the graduations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Graduation> findAll(Pageable pageable);

    /**
     * Get the "id" graduation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Graduation> findOne(Long id);

    /**
     * Delete the "id" graduation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
