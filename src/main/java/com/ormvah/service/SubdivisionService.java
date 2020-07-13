package com.ormvah.service;

import com.ormvah.domain.Subdivision;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Subdivision}.
 */
public interface SubdivisionService {

    /**
     * Save a subdivision.
     *
     * @param subdivision the entity to save.
     * @return the persisted entity.
     */
    Subdivision save(Subdivision subdivision);

    /**
     * Get all the subdivisions.
     *
     * @return the list of entities.
     */
    List<Subdivision> findAll();


    /**
     * Get the "id" subdivision.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Subdivision> findOne(Long id);

    /**
     * Delete the "id" subdivision.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
