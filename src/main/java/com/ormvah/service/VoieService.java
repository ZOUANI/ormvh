package com.ormvah.service;

import com.ormvah.domain.Voie;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Voie}.
 */
public interface VoieService {

    /**
     * Save a voie.
     *
     * @param voie the entity to save.
     * @return the persisted entity.
     */
    Voie save(Voie voie);

    /**
     * Get all the voies.
     *
     * @return the list of entities.
     */
    List<Voie> findAll();


    /**
     * Get the "id" voie.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Voie> findOne(Long id);

    /**
     * Delete the "id" voie.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
