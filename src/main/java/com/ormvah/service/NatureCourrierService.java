package com.ormvah.service;

import com.ormvah.domain.NatureCourrier;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link NatureCourrier}.
 */
public interface NatureCourrierService {

    /**
     * Save a natureCourrier.
     *
     * @param natureCourrier the entity to save.
     * @return the persisted entity.
     */
    NatureCourrier save(NatureCourrier natureCourrier);

    /**
     * Get all the natureCourriers.
     *
     * @return the list of entities.
     */
    List<NatureCourrier> findAll();


    /**
     * Get the "id" natureCourrier.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NatureCourrier> findOne(Long id);

    /**
     * Delete the "id" natureCourrier.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
