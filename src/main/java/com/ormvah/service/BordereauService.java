package com.ormvah.service;

import com.ormvah.domain.Bordereau;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Bordereau}.
 */
public interface BordereauService {

    /**
     * Save a bordereau.
     *
     * @param bordereau the entity to save.
     * @return the persisted entity.
     */
    Bordereau save(Bordereau bordereau);

    /**
     * Get all the bordereaus.
     *
     * @return the list of entities.
     */
    List<Bordereau> findAll();


    /**
     * Get the "id" bordereau.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Bordereau> findOne(Long id);

    /**
     * Delete the "id" bordereau.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
