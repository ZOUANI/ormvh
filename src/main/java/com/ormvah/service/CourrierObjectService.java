package com.ormvah.service;

import com.ormvah.domain.CourrierObject;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CourrierObject}.
 */
public interface CourrierObjectService {

    /**
     * Save a courrierObject.
     *
     * @param courrierObject the entity to save.
     * @return the persisted entity.
     */
    CourrierObject save(CourrierObject courrierObject);

    /**
     * Get all the courrierObjects.
     *
     * @return the list of entities.
     */
    List<CourrierObject> findAll();


    /**
     * Get the "id" courrierObject.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CourrierObject> findOne(Long id);

    /**
     * Delete the "id" courrierObject.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
