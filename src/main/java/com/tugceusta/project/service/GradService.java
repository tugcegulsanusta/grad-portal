package com.tugceusta.project.service;

import com.tugceusta.project.domain.Grad;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.tugceusta.project.domain.Grad}.
 */
public interface GradService {
    /**
     * Save a grad.
     *
     * @param grad the entity to save.
     * @return the persisted entity.
     */
    Grad save(Grad grad);

    /**
     * Updates a grad.
     *
     * @param grad the entity to update.
     * @return the persisted entity.
     */
    Grad update(Grad grad);

    /**
     * Partially updates a grad.
     *
     * @param grad the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Grad> partialUpdate(Grad grad);

    /**
     * Get all the grads.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Grad> findAll(Pageable pageable);

    /**
     * Get the "id" grad.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Grad> findOne(Long id);

    /**
     * Delete the "id" grad.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
